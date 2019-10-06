package ru.smartel.strike.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
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
import ru.smartel.strike.service.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @PersistenceContext
    private EntityManager entityManager;
    private TagRepository tagRepository;
    private BusinessValidationService businessValidationService;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    public EventServiceImpl(
            TagRepository tagRepository,
            BusinessValidationService businessValidationService,
            EventRepository eventRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.businessValidationService = businessValidationService;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EventDetailDTO index(Locale locale, boolean withRelatives) {
        return null; //todo implement
    }

    @Override
    public EventDetailDTO getAndIncrementViews(Integer eventId, Locale locale, boolean withRelatives) {
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
    };

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
     * Связать конфликт с событием
     */
    private void attachConflict(Event event, Integer conflictId) {
        Conflict conflict = entityManager.getReference(Conflict.class, conflictId);
        event.setConflict(conflict);
    }

    /**
     * Установить населенный пункт события. Если localityIdNode пришёл равным null, то обнуляем поле
     */
    private void setLocality(Event event, JsonNode localityIdNode) {
        Locality locality = null;

        if (!(localityIdNode instanceof NullNode)) {
            locality = entityManager.getReference(Locality.class, localityIdNode.asInt());
        }

        event.setLocality(locality);
    }

    /**
     * Установить статус события. Если eventStatusIdNode пришёл равным null, то обнуляем поле
     */
    private void setEventStatus(Event event, JsonNode eventStatusIdNode) {
        EventStatus eventStatus = null;

        if (!(eventStatusIdNode instanceof NullNode)) {
            eventStatus = entityManager.getReference(EventStatus.class, eventStatusIdNode.asInt());
        }

        event.setStatus(eventStatus);
    }

    /**
     * Установить тип события. Если eventTypeIdNode пришёл равным null, то обнуляем поле
     */
    private void setEventType(Event event, JsonNode eventTypeIdNode) {
        EventType eventType = null;

        if (!(eventTypeIdNode instanceof NullNode)) {
            eventType = entityManager.getReference(EventType.class, eventTypeIdNode.asInt());
        }

        event.setType(eventType);
    }

    /**
     * Если фото переданы в запросе, то удалить старые фото события и прикрепить новые
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
     * Если видео переданы в запросе, то удалить старые видео события и прикрепить новые
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
     * Если теги переданы в запросе, то открепить старые теги события и прикрепить новые
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
