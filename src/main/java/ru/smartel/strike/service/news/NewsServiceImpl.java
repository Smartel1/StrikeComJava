package ru.smartel.strike.service.news;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsShowDetailRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.entity.Video;
import ru.smartel.strike.repository.etc.PhotoRepository;
import ru.smartel.strike.repository.etc.TagRepository;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.repository.etc.VideoRepository;
import ru.smartel.strike.repository.etc.VideoTypeRepository;
import ru.smartel.strike.repository.news.NewsRepository;
import ru.smartel.strike.rules.OccurredWhenPublish;
import ru.smartel.strike.rules.UserCanModerate;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.service.notifications.PushService;
import ru.smartel.strike.service.validation.BusinessValidationService;
import ru.smartel.strike.specification.news.ByRolesNews;
import ru.smartel.strike.specification.news.LocalizedNews;
import ru.smartel.strike.specification.news.PublishedNews;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = Exception.class)
public class NewsServiceImpl implements NewsService {

    private NewsDTOValidator validator;
    private FiltersTransformer filtersTransformer;
    private NewsRepository newsRepository;
    private UserRepository userRepository;
    private BusinessValidationService businessValidationService;
    private PushService pushService;
    private PhotoRepository photoRepository;
    private VideoTypeRepository videoTypeRepository;
    private TagRepository tagRepository;
    private VideoRepository videoRepository;

    public NewsServiceImpl(NewsDTOValidator validator,
                           FiltersTransformer filtersTransformer,
                           NewsRepository newsRepository,
                           UserRepository userRepository,
                           BusinessValidationService businessValidationService,
                           PushService pushService,
                           PhotoRepository photoRepository,
                           VideoTypeRepository videoTypeRepository,
                           TagRepository tagRepository,
                           VideoRepository videoRepository) {
        this.validator = validator;
        this.filtersTransformer = filtersTransformer;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.businessValidationService = businessValidationService;
        this.pushService = pushService;
        this.photoRepository = photoRepository;
        this.videoTypeRepository = videoTypeRepository;
        this.tagRepository = tagRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public Long getNonPublishedCount() {
        return newsRepository.count(new PublishedNews(false));
    }

    @Override
    @PreAuthorize("permitAll()")
    public ListWrapperDTO<NewsListDTO> list(NewsListRequestDTO dto) {
        validator.validateListQueryDTO(dto);

        //Transform filters and other restrictions to Specifications
        Specification<News> specification = filtersTransformer
                .toSpecification(dto.getFilters(), null != dto.getUser() ? dto.getUser().getId() : null)
                .and(new ByRolesNews(dto.getUser()))
                .and(new LocalizedNews(dto.getLocale()));

        //Get count of news matching specification
        long newsCount = newsRepository.count(specification);

        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                newsCount,
                dto.getPage(),
                dto.getPerPage()
        );

        if (newsCount <= (dto.getPage() - 1) * dto.getPerPage()) {
            return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);
        }

        //Get count of news matching specification. Because pagination and fetching dont work together
        List<Long> ids = newsRepository.findIdsOrderByDateDesc(specification, dto);

        List<NewsListDTO> newsListDTOs = newsRepository.findAllById(ids).stream()
                .map(e -> NewsListDTO.of(e, dto.getLocale()))
                .sorted((e1, e2) -> Long.compare(e2.getDate(), e1.getDate()))
                .collect(Collectors.toList());

        return new ListWrapperDTO<>(newsListDTOs, responseMeta);
    }

    @Override
    @PreAuthorize("permitAll()")
    @PostAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or returnObject.published")
    public NewsDetailDTO incrementViewsAndGet(NewsShowDetailRequestDTO dto) {
        News news = newsRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена"));

        news.setViews(news.getViews() + 1);

        return NewsDetailDTO.of(news, dto.getLocale());
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    public void setFavourite(Long newsId, long userId, boolean isFavourite) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));

        News news = newsRepository.getOne(newsId);

        List<News> currentFavourites = user.getFavouriteNews();

        if (isFavourite) {
            //If not in favourites - add it
            if (!currentFavourites.contains(news)) {
                currentFavourites.add(news);
            }
        } else {
            currentFavourites.remove(news);
        }
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    public NewsDetailDTO create(NewsCreateRequestDTO dto) {
        validator.validateStoreDTO(dto);


        User user = userRepository.findById(dto.getUser().getId()).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));

        //Only moderator can publish news
        businessValidationService.validate(
                new UserCanModerate(user).when(null != dto.getPublished() && dto.getPublished().orElse(false))
        );

        News news = new News();
        news.setAuthor(user);
        fillNewsFields(news, dto, dto.getLocale());

        businessValidationService.validate(new OccurredWhenPublish(news));
        newsRepository.save(news);

        //Send push
        if (!news.isPublished()) {
            //if news is creating non published - notify moderators
            pushService.newsCreatedByUser(news.getId(), news.getAuthor().getId(), news.getAuthor().getName());
        } else {
            //if news published - notify subscribers
            Map<String, Locale> titlesByLocales = Stream.of(Locale.values())
                    .filter(loc -> !loc.equals(Locale.ALL))
                    .filter(loc -> null != news.getTitleByLocale(loc))
                    .collect(Collectors.toMap(news::getTitleByLocale, Function.identity()));

            pushService.newsPublished(
                    news.getId(),
                    news.getAuthor().getId(),
                    titlesByLocales,
                    null, //do not send push to author cuz he's moderator
                    false
            );
        }

        return NewsDetailDTO.of(news, dto.getLocale());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isNewsAuthor(#dto.newsId)")
    public NewsDetailDTO update(NewsUpdateRequestDTO dto) {
        validator.validateUpdateDTO(dto);

        News news = newsRepository.findById(dto.getNewsId())
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена"));
        User user = userRepository.findById(dto.getUser().getId()).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));

        boolean changingPublicationStatus =
                null != dto.getPublished() && dto.getPublished().orElseThrow() != news.isPublished();

        businessValidationService.validate(
                new UserCanModerate(user).when(changingPublicationStatus)
        );

        //We need to know which titles was not translated before update (not to send push twice to locale topic)
        Set<Locale> nonLocalizedTitlesBeforeUpdate = Stream.of(Locale.values())
                .filter(loc -> null == news.getTitleByLocale(loc))
                .collect(Collectors.toSet());

        fillNewsFields(news, dto, dto.getLocale());

        businessValidationService.validate(new OccurredWhenPublish(news));

        if (news.isPublished()) { //after update
            // titles which was not localized earlier and have been localized in this transaction
            Map<String, Locale> titlesLocalizedDuringThisUpdate = Stream.of(Locale.values())
                    .filter(loc -> !loc.equals(Locale.ALL))
                    .filter(nonLocalizedTitlesBeforeUpdate::contains)
                    .filter(loc -> null != news.getTitleByLocale(loc))
                    .collect(Collectors.toMap(news::getTitleByLocale, Function.identity()));

            pushService.newsPublished(
                    dto.getNewsId(),
                    null != news.getAuthor() ? news.getAuthor().getId() : null,
                    titlesLocalizedDuringThisUpdate,
                    null != news.getAuthor() ? news.getAuthor().getFcm() : null,
                    changingPublicationStatus //whether notify news's author or not
            );
        }

        return NewsDetailDTO.of(news, dto.getLocale());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(
                () -> new EntityNotFoundException("Новость не найдена"));
        newsRepository.delete(news);

        //If news was not published - notify its' author about rejection
        if (!news.isPublished() && null != news.getAuthor()) {
            pushService.newsDeclined(
                    news.getAuthor().getFcm(),
                    newsId,
                    news.getAuthor().getId()
            );
        }
    }

    private void fillNewsFields(News news, NewsCreateRequestDTO dto, Locale locale) {
        //for the sake of PATCH ;)
        if (null != dto.getDate()) {
            news.setDate(LocalDateTime.ofEpochSecond(dto.getDate().orElseThrow(), 0, ZoneOffset.UTC));
        }
        if (null != dto.getSourceLink()) {
            news.setSourceLink(dto.getSourceLink().orElse(null));
        }
        if (null != dto.getTitleRu()) {
            news.setTitleRu(dto.getTitleRu().orElse(null));
        }
        if (null != dto.getTitleEn()) {
            news.setTitleEn(dto.getTitleEn().orElse(null));
        }
        if (null != dto.getTitleEs()) {
            news.setTitleEs(dto.getTitleEs().orElse(null));
        }
        if (null != dto.getTitleDe()) {
            news.setTitleDe(dto.getTitleDe().orElse(null));
        }
        if (null != dto.getContentRu()) {
            news.setContentRu(dto.getContentRu().orElse(null));
        }
        if (null != dto.getContentEn()) {
            news.setContentEn(dto.getContentEn().orElse(null));
        }
        if (null != dto.getContentEs()) {
            news.setContentEs(dto.getContentEs().orElse(null));
        }
        if (null != dto.getContentDe()) {
            news.setContentDe(dto.getContentDe().orElse(null));
        }
        if (null != dto.getPublished()) {
            news.setPublished(dto.getPublished().orElseThrow());
        }
        if (null != dto.getTitle()) {
            news.setTitleByLocale(locale, dto.getTitle().orElse(null));
        }
        if (null != dto.getContent()) {
            news.setContentByLocale(locale, dto.getContent().orElse(null));
        }
        if (null != dto.getPhotoUrls()) {
            syncPhotos(news, dto.getPhotoUrls().orElseThrow());
        }
        if (null != dto.getVideos()) {
            syncVideos(news, dto.getVideos().orElseThrow());
        }
        if (null != dto.getTags()) {
            syncTags(news, dto.getTags().orElseThrow());
        }
    }

    /**
     * If client has sent photos, then remove old ones and save received
     */
    private void syncPhotos(News news, List<String> photoURLs) {
        photoRepository.deleteAll(news.getPhotos());
        news.getPhotos().clear();
        news.getPhotos().addAll(
                photoURLs
                        .stream()
                        .map(Photo::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * If client has sent videos, then remove old ones and save received
     */
    private void syncVideos(News news, List<VideoDTO> videos) {
        videoRepository.deleteAll(news.getVideos());
        news.getVideos().clear();
        news.getVideos().addAll(
                videos
                        .stream()
                        .map(videoDTO -> {
                            Video video = new Video();
                            video.setUrl(videoDTO.getUrl());
                            if (null != videoDTO.getPreviewUrl()) {
                                video.setPreviewUrl(videoDTO.getPreviewUrl().orElse(null));
                            }
                            video.setVideoType(videoTypeRepository.getOne(videoDTO.getVideoTypeId()));
                            return video;
                        })
                        .collect(Collectors.toList())
        );
    }

    /**
     * If client has sent tags, then detach (not remove because tags are shared between news) old ones and save received
     */
    private void syncTags(News news, List<String> tagNames) {
        //we do not remove old tags from db. They may be shared across multiple events or news
        news.getTags().clear();
        news.getTags().addAll(
                tagNames
                        .stream()
                        .map(tagName -> tagRepository.findFirstByName(tagName)
                                .orElse(new Tag(tagName)))
                        .collect(Collectors.toList())
        );
    }
}
