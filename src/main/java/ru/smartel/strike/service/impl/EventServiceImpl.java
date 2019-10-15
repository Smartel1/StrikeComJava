package ru.smartel.strike.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.EventListRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.entity.*;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.entity.reference.VideoType;
import ru.smartel.strike.repository.EventRepository;
import ru.smartel.strike.repository.TagRepository;
import ru.smartel.strike.repository.UserRepository;
import ru.smartel.strike.rules.NotAParentEvent;
import ru.smartel.strike.rules.UserCanModerate;
import ru.smartel.strike.service.BusinessValidationService;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.EventFiltersTransformer;
import ru.smartel.strike.service.Locale;

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

    public EventServiceImpl(
            TagRepository tagRepository,
            BusinessValidationService businessValidationService,
            EventRepository eventRepository,
            UserRepository userRepository,
            EventFiltersTransformer filtersTransformer) {
        this.tagRepository = tagRepository;
        this.businessValidationService = businessValidationService;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.filtersTransformer = filtersTransformer;
    }


    @Override
    public EventListWrapperDTO index(EventListRequestDTO.FiltersBag filters, int perPage, int page, Locale locale, User user) {

        Long eventsCount = getEventsCount(filters, locale, user);

        if (eventsCount == 0) return new EventListWrapperDTO(Collections.emptyList(), eventsCount);

        //Get list of events' ids first. That's because pagination and fetching dont work together
        List<Integer> ids = getEventIds(filters, perPage, page, locale, user);

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

        return new EventListWrapperDTO(eventListDTOS, eventsCount);
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
    public EventDetailDTO create(JsonNode data, Integer userId, Locale locale) throws BusinessRuleValidationException {
        User user = userRepository.findById(userId).get();

        //Если пользователь хочет сразу опубликовать событие, он должен быть модератором
        businessValidationService.validate(
                new UserCanModerate(user).when(
                        data.has("published")
                                && data.get("published").asBoolean()
                                || data.has("locality_id"))
        );

        Event event = new Event();
        event.setAuthor(user);
        fillEventFields(event, data, locale);

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
    public EventDetailDTO update(Integer eventId, JsonNode data, Integer userId, Locale locale) throws BusinessRuleValidationException {
        Event event = eventRepository.findOrThrow(eventId);
        User user = userRepository.findById(userId).get();

        boolean userChangesPublishStatus =
                data.has("published") && data.get("published").asBoolean() != event.isPublished();

        boolean userChangesConflict =
                data.has("conflict_id") && (data.get("conflict_id").asInt() != event.getConflict().getId());

        businessValidationService.validate(
                new UserCanModerate(user).when(
                        userChangesPublishStatus
                                || data.has("locality_id")
                                || userChangesConflict
                ),
                new NotAParentEvent(event).when(userChangesConflict)
        );

        //Перед изменением смотрим, на каких языках уже есть переводы (чтобы не послать пуш второй раз)
        boolean isLocaleRuBeforeUpdateNull = null == event.getTitleRu();
        boolean isLocaleEnBeforeUpdateNull = null == event.getTitleEn();
        boolean isLocaleEsBeforeUpdateNull = null == event.getTitleEs();

        fillEventFields(event, data, locale);

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
    private Long getEventsCount(EventListRequestDTO.FiltersBag filters, Locale locale, User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Event> root = countQuery.from(Event.class);
        countQuery.select(cb.count(root));

        applyPredicatesToQuery(countQuery, root, filters, locale, user);

        return entityManager
                .createQuery(countQuery)
                .getSingleResult();
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

    private void fillEventFields(Event event, JsonNode data, Locale locale) {
        if (data.has("conflict_id")) attachConflict(event, data.get("conflict_id").asInt());
        if (data.has("date")) event.setDate(LocalDateTime.ofEpochSecond(data.get("date").asInt(), 0, ZoneOffset.UTC));
        if (data.has("source_link")) event.setSourceLink(data.get("source_link").asText());
        if (data.has("title_ru")) event.setTitleRu(data.get("title_ru").asText());
        if (data.has("title_en")) event.setTitleEn(data.get("title_en").asText());
        if (data.has("title_es")) event.setTitleEs(data.get("title_es").asText());
        if (data.has("content_ru")) event.setContentRu(data.get("content_ru").asText());
        if (data.has("content_en")) event.setContentEn(data.get("content_en").asText());
        if (data.has("content_es")) event.setContentEs(data.get("content_es").asText());
        if (data.has("published")) event.setPublished(data.get("published").asBoolean());
        if (data.has("latitude")) event.setLatitude((float) data.get("latitude").asDouble());
        if (data.has("longitude")) event.setLongitude((float) data.get("longitude").asDouble());
        if (data.has("locality_id")) setLocality(event, data.get("locality_id"));
        if (data.has("event_status_id")) setEventStatus(event, data.get("event_status_id"));
        if (data.has("event_type_id")) setEventType(event, data.get("event_type_id"));
        if (data.has("title")) event.setTitleByLocale(locale, data.get("title").asText());
        if (data.has("content")) event.setContentByLocale(locale, data.get("content").asText());
        if (data.has("photo_urls")) syncPhotos(event, data.get("photo_urls"));
        if (data.has("videos")) syncVideos(event, data.get("videos"));
        if (data.has("tags")) syncTags(event, data.get("tags"));
    }

    /**
     * Set event's conflict
     */
    private void attachConflict(Event event, Integer conflictId) {
        Conflict conflict = entityManager.getReference(Conflict.class, conflictId);
        event.setConflict(conflict);
    }

    /**
     * Set locality. If received localityIdNode equals null, then set to null
     */
    private void setLocality(Event event, JsonNode localityIdNode) {
        Locality locality = null;

        if (!(localityIdNode instanceof NullNode)) {
            locality = entityManager.getReference(Locality.class, localityIdNode.asInt());
        }

        event.setLocality(locality);
    }

    /**
     * Set event's status. If received eventStatusIdNode equals null, then set to null
     */
    private void setEventStatus(Event event, JsonNode eventStatusIdNode) {
        EventStatus eventStatus = null;

        if (!(eventStatusIdNode instanceof NullNode)) {
            eventStatus = entityManager.getReference(EventStatus.class, eventStatusIdNode.asInt());
        }

        event.setStatus(eventStatus);
    }

    /**
     * Set event's type. If received eventTypeIdNode equals null, then set to null
     */
    private void setEventType(Event event, JsonNode eventTypeIdNode) {
        EventType eventType = null;

        if (!(eventTypeIdNode instanceof NullNode)) {
            eventType = entityManager.getReference(EventType.class, eventTypeIdNode.asInt());
        }

        event.setType(eventType);
    }

    /**
     * If client has sent photos, then remove old ones and save received
     */
    private void syncPhotos(Event event, JsonNode photoURLsNode) {
        if (null == photoURLsNode) return;

        for (Photo oldPhoto : event.getPhotos()) {
            entityManager.remove(oldPhoto);
        }

        for (JsonNode photoURLNode : photoURLsNode) {
            Photo photo = new Photo();
            photo.setUrl(photoURLNode.asText());
            entityManager.persist(photo);
            event.getPhotos().add(photo);
        }
    }

    /**
     * If client has sent videos, then remove old ones and save received
     */
    private void syncVideos(Event event, JsonNode videosNode) {
        if (null == videosNode) return;

        for (Video oldVideo : event.getVideos()) {
            entityManager.remove(oldVideo);
        }

        for (JsonNode receivedVideo : videosNode) {
            Video video = new Video();
            video.setUrl(receivedVideo.get("url").asText());
            if (receivedVideo.has("preview_url")) {
                video.setPreviewUrl(receivedVideo.get("preview_url").asText());
            }
            video.setVideoType(entityManager.getReference(VideoType.class, receivedVideo.get("video_type_id").asInt()));
            entityManager.persist(video);
            event.getVideos().add(video);
        }
    }

    /**
     * If client has sent tags, then detach (not remove because tags are shared between events) old ones and save received
     */
    private void syncTags(Event event, JsonNode receivedTags) {
        if (null == receivedTags) return;
        //Не удаляем старые теги из таблицы, они могут быть привязаны к другим событиям
        event.getTags().clear();

        //сохраняем теги
        for (JsonNode receivedTag : receivedTags) {
            Tag tag = tagRepository.findFirstByName(receivedTag.asText());
            if (null == tag) {
                tag = new Tag();
                tag.setName(receivedTag.asText());
                entityManager.persist(tag);
            }
            event.getTags().add(tag);
        }
    }
}
