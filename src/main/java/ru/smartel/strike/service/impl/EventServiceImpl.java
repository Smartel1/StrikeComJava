package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.entity.*;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.entity.reference.VideoType;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.EventRepository;
import ru.smartel.strike.repository.TagRepository;
import ru.smartel.strike.repository.UserRepository;
import ru.smartel.strike.rules.NotAParentEvent;
import ru.smartel.strike.rules.UserCanModerate;
import ru.smartel.strike.service.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @PersistenceContext
    private EntityManager entityManager;
    private TagRepository tagRepository;
    private BusinessValidationService businessValidationService;
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private EventFiltersTransformer filtersTransformer;
    private EventDTOValidator validator;

    public EventServiceImpl(
            TagRepository tagRepository,
            BusinessValidationService businessValidationService,
            EventRepository eventRepository,
            UserRepository userRepository,
            EventFiltersTransformer filtersTransformer,
            EventDTOValidator validator
    ) {
        this.tagRepository = tagRepository;
        this.businessValidationService = businessValidationService;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.filtersTransformer = filtersTransformer;
        this.validator = validator;
    }


    @Override
    public EventListWrapperDTO list(EventListRequestDTO dto, int perPage, int page, Locale locale, User user) throws DTOValidationException {
        validator.validateListQueryDTO(dto);

        //Body has precedence over query params.
        //If perPage and Page of body (dto) aren't equal to default values then use values of dto
        if (!dto.getPerPage().equals(20)) perPage = dto.getPerPage();
        if (!dto.getPage().equals(1)) page = dto.getPage();

        int eventsCount = getEventsCount(dto.getFilters(), locale, user);

        EventListWrapperDTO.Meta responseMeta = new EventListWrapperDTO.Meta(
                eventsCount,
                page,
                perPage,
                eventsCount/perPage + 1
        );

        if (eventsCount == 0) {
            return new EventListWrapperDTO(Collections.emptyList(), responseMeta);
        }

        //Get list of events' ids first. That's because pagination and fetching dont work together
        List<Integer> ids = getEventIds(dto.getFilters(), perPage, page, locale, user);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> eventQuery = cb.createQuery(Event.class);

        Root<Event> eventRoot = eventQuery.from(Event.class);
        eventRoot.fetch("photos", JoinType.LEFT);
        eventRoot.fetch("videos", JoinType.LEFT);
        eventRoot.fetch("tags", JoinType.LEFT);
        eventRoot.fetch("comments", JoinType.LEFT);
        eventRoot.fetch("conflict", JoinType.LEFT);

        eventQuery.select(eventRoot)
                .distinct(true)
                .orderBy(cb.desc(eventRoot.get("date")))
                .where(cb.in(eventRoot.get("id")).value(ids));

        List<EventListDTO> eventListDTOS = entityManager
                .createQuery(eventQuery)
                .getResultList()
                .stream()
                .map(e -> new EventListDTO(e, locale))
                .collect(Collectors.toList());

        return new EventListWrapperDTO(eventListDTOS, responseMeta);
    }

    @Override
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
    public void setFavourite(Integer eventId, Integer userId, boolean isFavourite) {
        User user = userRepository.findById(userId).get();
        Event event = entityManager.getReference(Event.class, eventId);

        List<Event> currentFavourites = user.getFavouriteEvents();

        if (isFavourite) {
            //Добавим в избранное, если ещё не в избранном
            if (!currentFavourites.contains(event)) {
                currentFavourites.add(event);
            }
        } else {
            currentFavourites.remove(event);
        }
    }

    @Override
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

        entityManager.persist(event);

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
    public void delete(Integer eventId) {
        Event event = eventRepository.findOrThrow(eventId);
        entityManager.remove(event);
    }

    /**
     * Get ids of events matching filters, locale, permissions and pagination
     */
    private List<Integer> getEventIds(EventListRequestDTO.FiltersBag filters, int perPage, int page, Locale locale, User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<Event> root = idQuery.from(Event.class);
        idQuery.select(root.get("id"))
                .orderBy(cb.desc(root.get("date")));

        applyPredicatesToQuery(idQuery, root, filters, locale, user);

        return entityManager
                .createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }

    /**
     * Get count of events matching filters, locale and permissions
     */
    private int getEventsCount(EventListRequestDTO.FiltersBag filters, Locale locale, User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Event> root = countQuery.from(Event.class);
        countQuery.select(cb.count(root));

        applyPredicatesToQuery(countQuery, root, filters, locale, user);

        return entityManager
                .createQuery(countQuery)
                .getSingleResult()
                .intValue();
    }

    /**
     * Add restrictions by filters, user permissions and locale
     */
    private void applyPredicatesToQuery(CriteriaQuery query, Root root, EventListRequestDTO.FiltersBag filters, Locale locale, User user) {
        List<Predicate> predicates = new LinkedList<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        //Usual users can see published events only
        if (null == user
                || !user.getRolesAsList().contains(User.ROLE_MODERATOR)
                && !user.getRolesAsList().contains(User.ROLE_ADMIN)) {
            predicates.add(cb.equal(root.get("published"), true));
        }

        if (null != filters) {
            predicates.addAll(
                    filtersTransformer
                            .toSpecifications(filters, user)
                            .stream()
                            .map(spec -> spec.toPredicate(root, query, cb))
                            .collect(Collectors.toList()));
        }

        //Only localized events
        if (!locale.equals(Locale.ALL)) {
            predicates.add(cb.isNotNull(root.get("title" + locale.getPascalCase())));
            predicates.add(cb.isNotNull(root.get("content" + locale.getPascalCase())));
        }

        //Union all predicates (filters, restrictions) with AND operator
        query.where(cb.and(predicates.toArray(Predicate[]::new)));
    }

    private void fillEventFields(Event event, EventRequestDTO dto, Locale locale) {
        if (null != dto.getConflictId()) setConflict(event, dto.getConflictId().orElseThrow());
        if (null != dto.getDate()) event.setDate(LocalDateTime.ofEpochSecond(dto.getDate().orElseThrow(), 0, ZoneOffset.UTC));
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
        Conflict conflict = entityManager.getReference(Conflict.class, conflictId);
        event.setConflict(conflict);
    }

    /**
     * Set locality. If received localityId equals to null, then set to null
     */
    private void setLocality(Event event, Integer localityIdOptional) {
        Locality locality = null;

        if (null != localityIdOptional) {
            locality = entityManager.getReference(Locality.class, localityIdOptional);
        }

        event.setLocality(locality);
    }

    /**
     * Set event's status. If received eventStatusId equals to null, then set to null
     */
    private void setEventStatus(Event event, Integer eventStatusIdOptional) {
        EventStatus eventStatus = null;

        if (null != eventStatusIdOptional) {
            eventStatus = entityManager.getReference(EventStatus.class, eventStatusIdOptional);
        }

        event.setStatus(eventStatus);
    }

    /**
     * Set event's type. If received eventTypeId equals null, then set to null
     */
    private void setEventType(Event event, Integer eventTypeIdOptional) {
        EventType eventType = null;

        if (null != eventTypeIdOptional) {
            eventType = entityManager.getReference(EventType.class, eventTypeIdOptional);
        }

        event.setType(eventType);
    }

    /**
     * If client has sent photos, then remove old ones and save received
     */
    private void syncPhotos(Event event, List<String> photoURLs) {
        for (Photo oldPhoto : event.getPhotos()) {
            entityManager.remove(oldPhoto);
        }

        for (String photoURL : photoURLs) {
            Photo photo = new Photo();
            photo.setUrl(photoURL);
            entityManager.persist(photo);
            event.getPhotos().add(photo);
        }
    }

    /**
     * If client has sent videos, then remove old ones and save received
     */
    private void syncVideos(Event event, List<VideoDTO> videos) {
        for (Video oldVideo : event.getVideos()) {
            entityManager.remove(oldVideo);
        }

        for (VideoDTO receivedVideo : videos) {
            Video video = new Video();
            video.setUrl(receivedVideo.getUrl());
            if (null != receivedVideo.getPreviewUrl()) {
                video.setPreviewUrl(receivedVideo.getPreviewUrl().orElse(null));
            }
            video.setVideoType(entityManager.getReference(VideoType.class, receivedVideo.getVideoTypeId()));
            entityManager.persist(video);
            event.getVideos().add(video);
        }
    }

    /**
     * If client has sent tags, then detach (not remove because tags are shared between events) old ones and save received
     */
    private void syncTags(Event event, List<String> receivedTags) {
        //Не удаляем старые теги из таблицы, они могут быть привязаны к другим событиям
        event.getTags().clear();

        //сохраняем теги
        for (String receivedTag : receivedTags) {
            Tag tag = tagRepository.findFirstByName(receivedTag);
            if (null == tag) {
                tag = new Tag();
                tag.setName(receivedTag);
                entityManager.persist(tag);
            }
            event.getTags().add(tag);
        }
    }
}
