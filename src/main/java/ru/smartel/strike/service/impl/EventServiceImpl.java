package ru.smartel.strike.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.entity.*;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.*;
import ru.smartel.strike.rules.NotAParentEvent;
import ru.smartel.strike.rules.UserCanModerate;
import ru.smartel.strike.service.*;
import ru.smartel.strike.specification.event.ByRolesEvent;
import ru.smartel.strike.specification.event.LocalizedEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class EventServiceImpl implements EventService {

    private TagRepository tagRepository;
    private BusinessValidationService businessValidationService;
    private EventRepository eventRepository;
    private ConflictRepository conflictRepository;
    private PhotoRepository photoRepository;
    private VideoRepository videoRepository;
    private VideoTypeRepository videoTypeRepository;
    private LocalityRepository localityRepository;
    private EventTypeRepository eventTypeRepository;
    private EventStatusRepository eventStatusRepository;
    private UserRepository userRepository;
    private EventFiltersTransformer filtersTransformer;
    private EventDTOValidator validator;

    public EventServiceImpl(
            TagRepository tagRepository,
            BusinessValidationService businessValidationService,
            EventRepository eventRepository,
            ConflictRepository conflictRepository,
            PhotoRepository photoRepository,
            VideoRepository videoRepository,
            VideoTypeRepository videoTypeRepository,
            LocalityRepository localityRepository,
            EventTypeRepository eventTypeRepository,
            EventStatusRepository eventStatusRepository,
            UserRepository userRepository,
            EventFiltersTransformer filtersTransformer,
            EventDTOValidator validator
    ) {
        this.tagRepository = tagRepository;
        this.businessValidationService = businessValidationService;
        this.eventRepository = eventRepository;
        this.conflictRepository = conflictRepository;
        this.photoRepository = photoRepository;
        this.videoRepository = videoRepository;
        this.videoTypeRepository = videoTypeRepository;
        this.localityRepository = localityRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.userRepository = userRepository;
        this.filtersTransformer = filtersTransformer;
        this.validator = validator;
    }


    @Override
    @PreAuthorize("permitAll()")
    public EventListWrapperDTO list(EventListRequestDTO dto, int perPage, int page, Locale locale, User user) throws DTOValidationException {
        validator.validateListQueryDTO(dto);

        //Body has precedence over query params.
        //If perPage and Page of body (dto) aren't equal to default values then use values of dto
        if (20 != dto.getPerPage()) perPage = dto.getPerPage();
        if (1 != dto.getPage()) page = dto.getPage();

        //Transform filters and other restrictions to Specifications
        Specification<Event> specification = filtersTransformer
                .toSpecification(dto.getFilters(), user)
                .and(new ByRolesEvent(user))
                .and(new LocalizedEvent(locale));

        //Get count of events matching specification
        long eventsCount = eventRepository.count(specification);

        EventListWrapperDTO.Meta responseMeta = new EventListWrapperDTO.Meta(
                eventsCount,
                page,
                perPage,
                eventsCount / perPage + 1
        );

        if (eventsCount <= (page - 1) * perPage) {
            return new EventListWrapperDTO(Collections.emptyList(), responseMeta);
        }

        //Get count of events matching specification. Because pagination and fetching dont work together
        List<Integer> ids = eventRepository.findIdsOrderByDateDesc(specification, page, perPage);

        List<EventListDTO> eventListDTOs = eventRepository.findAllById(ids)
                .stream()
                .map(e -> new EventListDTO(e, locale))
                .sorted(Comparator.comparingLong(EventListDTO::getDate))
                .collect(Collectors.toList());;

        return new EventListWrapperDTO(eventListDTOs, responseMeta);
    }

    @Override
    @PreAuthorize("permitAll()")
    public EventDetailDTO incrementViewsAndGet(Integer eventId, Locale locale, boolean withRelatives) {
        Event event = eventRepository.findOrThrow(eventId);

        event.setViews(event.getViews() + 1);
        EventDetailDTO dto = new EventDetailDTO(event, locale);

        if (withRelatives) {
            dto.add("relatives", null);
        }

        return dto;
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    public void setFavourite(Integer eventId, Integer userId, boolean isFavourite) {
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.getOne(eventId);

        List<Event> currentFavourites = user.getFavouriteEvents();

        if (isFavourite) {
            //If not in favourites - add it
            if (!currentFavourites.contains(event)) {
                currentFavourites.add(event);
            }
        } else {
            currentFavourites.remove(event);
        }
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    public EventDetailDTO create(EventRequestDTO dto, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException {
        validator.validateStoreDTO(dto);

        User user = userRepository.findById(userId).orElseThrow();

        //Если пользователь хочет сразу опубликовать событие, он должен быть модератором
        businessValidationService.validate(
                new UserCanModerate(user).when(
                        null != dto.getPublished() && dto.getPublished().orElse(false) || null != dto.getLocalityId()
                )
        );

        Event event = new Event();
        event.setAuthor(user);
        fillEventFields(event, dto, locale);

        eventRepository.save(event);

        //Если обычный пользователь предлагает событие, посылаем пуш админам. Если модератор публикует - то всем
        if (!event.isPublished()) {
//            $this->pushService->eventCreatedByUser($event);
        } else {
//            $this->pushService->eventPublished($event, new LocalesDTO(
//                    !is_null($event->getTitleRu()),
//                    !is_null($event->getTitleEn()),
//                    !is_null($event->getTitleEs())
//            ));
        }

        return new EventDetailDTO(event, locale);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isEventAuthor(#eventId)")
    public EventDetailDTO update(Integer eventId, EventRequestDTO dto, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException {
        validator.validateUpdateDTO(dto);

        Event event = eventRepository.findOrThrow(eventId);
        User user = userRepository.findById(userId).get();

        boolean userChangesPublishStatus =
                null != dto.getPublished() && dto.getPublished().get() != event.isPublished();

        boolean userChangesConflict =
                null != dto.getConflictId() && (dto.getConflictId().get() != event.getConflict().getId());

        businessValidationService.validate(
                new UserCanModerate(user).when(
                        userChangesPublishStatus
                                || null != dto.getLocalityId()
                                || userChangesConflict
                ),
                new NotAParentEvent(event).when(userChangesConflict)
        );

        //Перед изменением смотрим, на каких языках уже есть переводы (чтобы не послать пуш второй раз)
        boolean isLocaleRuBeforeUpdateNull = null == event.getTitleRu();
        boolean isLocaleEnBeforeUpdateNull = null == event.getTitleEn();
        boolean isLocaleEsBeforeUpdateNull = null == event.getTitleEs();

        fillEventFields(event, dto, locale);

//
//        //Если событие опубликовано, то посылаем пуши в те топики по языкам, на которые переведено событие в этом обновлении.
//        //Если событие публикуется в этом действии, то посылаются пуши на все языки, на которые локализовано событие
//        if ($event -> isPublished()) {
//            $localesToPush = new LocalesDTO(
//                    ($nullLocalesBeforeUpdate -> isRu()or $userChangesPublishStatus) and
//            !is_null($event -> getTitleRu()),
//                    ($nullLocalesBeforeUpdate -> isEn() or $userChangesPublishStatus)and
//            !is_null($event -> getTitleEn()),
//                    ($nullLocalesBeforeUpdate -> isEs() or $userChangesPublishStatus)and
//            !is_null($event -> getTitleEs())
//            );
//
//            $this -> pushService -> eventPublished($event, $localesToPush);
//
//            //Если событие публикуется в этом действии, то автору посылается уведомление об этом
//            if ($userChangesPublishStatus) {
//                $this -> pushService -> sendYourPostModerated($event);
//            }
//        }
//
        return new EventDetailDTO(event, locale);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(Integer eventId) {
//        Event event = eventRepository.findOrThrow(eventId);
        eventRepository.deleteById(eventId);
    }

    private void fillEventFields(Event event, EventRequestDTO dto, Locale locale) {
        if (null != dto.getConflictId()) setConflict(event, dto.getConflictId().orElseThrow());
        if (null != dto.getDate())
            event.setDate(LocalDateTime.ofEpochSecond(dto.getDate().orElseThrow(), 0, ZoneOffset.UTC));
        if (null != dto.getSourceLink()) event.setSourceLink(dto.getSourceLink().orElse(null));
        if (null != dto.getTitleRu()) event.setTitleRu(dto.getTitleRu().orElse(null));
        if (null != dto.getTitleEn()) event.setTitleEn(dto.getTitleEn().orElse(null));
        if (null != dto.getTitleEs()) event.setTitleEs(dto.getTitleEs().orElse(null));
        if (null != dto.getContentRu()) event.setContentRu(dto.getContentRu().orElse(null));
        if (null != dto.getContentEn()) event.setContentEn(dto.getContentEn().orElse(null));
        if (null != dto.getContentEs()) event.setContentEs(dto.getContentEs().orElse(null));
        if (null != dto.getPublished()) event.setPublished(dto.getPublished().orElseThrow());
        if (null != dto.getLatitude()) event.setLatitude(dto.getLatitude().orElseThrow());
        if (null != dto.getLongitude()) event.setLongitude(dto.getLongitude().orElseThrow());
        if (null != dto.getLocalityId()) setLocality(event, dto.getLocalityId().orElse(null));
        if (null != dto.getEventStatusId()) setEventStatus(event, dto.getEventStatusId().orElse(null));
        if (null != dto.getEventTypeId()) setEventType(event, dto.getEventTypeId().orElse(null));
        if (null != dto.getTitle()) event.setTitleByLocale(locale, dto.getTitle().orElse(null));
        if (null != dto.getContent()) event.setContentByLocale(locale, dto.getContent().orElse(null));
        if (null != dto.getPhotoUrls()) syncPhotos(event, dto.getPhotoUrls());
        if (null != dto.getVideos()) syncVideos(event, dto.getVideos());
        if (null != dto.getTags()) syncTags(event, dto.getTags());
    }

    /**
     * Set event's conflict
     */
    private void setConflict(Event event, Integer conflictId) {
        Conflict conflict = conflictRepository.getOne(conflictId);
        event.setConflict(conflict);
    }

    /**
     * Set locality. If received localityId equals to null, then set to null
     */
    private void setLocality(Event event, Integer localityIdOptional) {
        Locality locality = null;

        if (null != localityIdOptional) {
            locality = localityRepository.getOne(localityIdOptional);
        }

        event.setLocality(locality);
    }

    /**
     * Set event's status. If received eventStatusId equals to null, then set to null
     */
    private void setEventStatus(Event event, Integer eventStatusIdOptional) {
        EventStatus eventStatus = null;

        if (null != eventStatusIdOptional) {
            eventStatus = eventStatusRepository.getOne(eventStatusIdOptional);
        }

        event.setStatus(eventStatus);
    }

    /**
     * Set event's type. If received eventTypeId equals null, then set to null
     */
    private void setEventType(Event event, Integer eventTypeIdOptional) {
        EventType eventType = null;

        if (null != eventTypeIdOptional) {
            eventType = eventTypeRepository.getOne(eventTypeIdOptional);
        }

        event.setType(eventType);
    }

    /**
     * If client has sent photos, then remove old ones and save received
     */
    private void syncPhotos(Event event, List<String> photoURLs) {
        photoRepository.deleteAll(event.getPhotos());
        event.getPhotos().clear();
        event.getPhotos().addAll(
                photoURLs
                        .stream()
                        .map(Photo::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * If client has sent videos, then remove old ones and save received
     */
    private void syncVideos(Event event, List<VideoDTO> videos) {
        videoRepository.deleteAll(event.getVideos());
        event.getVideos().clear();
        event.getVideos().addAll(
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
     * If client has sent tags, then detach (not remove because tags are shared between events) old ones and save received
     */
    private void syncTags(Event event, List<String> tagNames) {
        //we do not remove old tags from db. They may be shared across multiple events or news
        event.getTags().clear();
        event.getTags().addAll(
                tagNames
                        .stream()
                        .map(tagName -> {
                            Tag tag = tagRepository.findFirstByName(tagName);
                            return null != tag ? tag : new Tag(tagName);
                        })
                        .collect(Collectors.toList())
        );
    }
}
