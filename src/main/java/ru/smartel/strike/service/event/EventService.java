package ru.smartel.strike.service.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventShowDetailRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.BriefConflictWithEventsDTO;
import ru.smartel.strike.dto.response.event.BriefEventDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.service.sort.EventSortDTO;
import ru.smartel.strike.dto.service.sort.network.Networks;
import ru.smartel.strike.entity.*;
import ru.smartel.strike.entity.interfaces.PostEntity;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.repository.etc.*;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.event.EventStatusRepository;
import ru.smartel.strike.repository.event.EventTypeRepository;
import ru.smartel.strike.rules.EventAfterConflictsStart;
import ru.smartel.strike.rules.EventBeforeConflictsEnd;
import ru.smartel.strike.rules.NotAParentEvent;
import ru.smartel.strike.rules.UserCanModerate;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.service.notifications.PushService;
import ru.smartel.strike.service.publish.TelegramService;
import ru.smartel.strike.service.publish.VkService;
import ru.smartel.strike.service.validation.BusinessValidationService;
import ru.smartel.strike.specification.event.ByRolesEvent;
import ru.smartel.strike.specification.event.LocalizedEvent;
import ru.smartel.strike.specification.event.PublishedEvent;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = Exception.class)
public class EventService {

    private final TagRepository tagRepository;
    private final BusinessValidationService businessValidationService;
    private final EventRepository eventRepository;
    private final ConflictRepository conflictRepository;
    private final PhotoRepository photoRepository;
    private final VideoRepository videoRepository;
    private final VideoTypeRepository videoTypeRepository;
    private final LocalityRepository localityRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventStatusRepository eventStatusRepository;
    private final UserRepository userRepository;
    private final FiltersTransformer filtersTransformer;
    private final EventDTOValidator validator;
    private final PushService pushService;
    private final TelegramService telegramService;
    private final VkService vkService;

    public EventService(
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
            FiltersTransformer filtersTransformer,
            EventDTOValidator validator,
            PushService pushService,
            TelegramService telegramService,
            VkService vkService) {
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
        this.pushService = pushService;
        this.telegramService = telegramService;
        this.vkService = vkService;
    }

    public Long getNonPublishedCount() {
        return eventRepository.count(new PublishedEvent(false));
    }

    @PreAuthorize("permitAll()")
    public ListWrapperDTO<EventListDTO> list(EventListRequestDTO dto) {
        validator.validateListQueryDTO(dto);

        //Transform filters and other restrictions to Specifications
        Specification<Event> specification = filtersTransformer
                .toSpecification(dto.getFilters(), null != dto.getUser() ? dto.getUser().getId() : null)
                .and(new ByRolesEvent(dto.getUser()))
                .and(new LocalizedEvent(dto.getLocale()));

        //Get count of events matching specification
        long eventsCount = eventRepository.count(specification);

        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                eventsCount,
                dto.getPage(),
                dto.getPerPage()
        );

        if (eventsCount <= (dto.getPage() - 1) * dto.getPerPage()) {
            return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);
        }

        EventSortDTO sortDTO = EventSortDTO.of(dto.getSort());

        //Get count of events matching specification. Because pagination and fetching dont work together
        List<Long> ids = eventRepository.findIds(specification, sortDTO, dto.getPage(), dto.getPerPage());

        List<EventListDTO> eventListDTOs = eventRepository.findAllById(ids)
                .stream()
                .sorted(sortDTO.toComparator())
                .map(e -> EventListDTO.of(e, dto.getLocale()))
                .collect(Collectors.toList());

        return new ListWrapperDTO<>(eventListDTOs, responseMeta);
    }

    @PreAuthorize("permitAll()")
    @PostAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or returnObject.published")
    public EventDetailDTO incrementViewsAndGet(EventShowDetailRequestDTO dto) {
        Event event = eventRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));

        event.setViews(event.getViews() + 1);

        EventDetailDTO result = EventDetailDTO.of(event, dto.getLocale());

        if (dto.isWithRelatives()) {
            result.add("relatives", getRelatives(event, dto.getLocale()));
        }

        return result;
    }

    @PreAuthorize("isFullyAuthenticated()")
    public void setFavourite(Long eventId, long userId, boolean isFavourite) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));
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

    @PreAuthorize("isFullyAuthenticated()")
    public EventDetailDTO create(EventCreateRequestDTO dto) {
        validator.validateStoreDTO(dto);

        User user = userRepository.findById(dto.getUser().getId()).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));

        //Only moderator can publish events
        businessValidationService.validate(
                new UserCanModerate(user).when(
                        null != dto.getPublished() && dto.getPublished().orElse(false) || null != dto.getLocalityId()
                )
        );

        Event event = new Event();
        event.setAuthor(user);
        fillEventFields(event, dto, dto.getLocale());

        businessValidationService.validate(
                new EventBeforeConflictsEnd(event.getDate(), event.getConflict().getDateTo()),
                new EventAfterConflictsStart(event.getDate(), event.getConflict().getDateFrom())
        );
        eventRepository.save(event);
        updateConflictsEventStatuses(event.getConflict().getId());

        //Send push
        if (!event.isPublished()) {
            //if event is creating non published - notify moderators
            pushService.eventCreatedByUser(event.getId(), event.getAuthor().getId(), event.getAuthor().getName());
        } else {
            //if event published - notify subscribers
            Map<Locale, String> titlesByLocales = Stream.of(Locale.values())
                    .filter(loc -> !loc.equals(Locale.ALL))
                    .filter(loc -> null != event.getTitleByLocale(loc))
                    .collect(Collectors.toMap(Function.identity(), event::getTitleByLocale));

            if (titlesByLocales.containsKey(Locale.RU)) {
                if (dto.getPublishTo().contains(Networks.TELEGRAM.getId())) {
                    telegramService.sendToChannel(event);
                    vkService.sendToChannel(event);
                }
            }

            pushService.eventPublished(
                    event.getId(),
                    event.getAuthor().getId(),
                    event.getLongitude(),
                    event.getLatitude(),
                    titlesByLocales,
                    null, //do not send push to author cuz he's moderator
                    false
            );
        }

        return EventDetailDTO.of(event, dto.getLocale());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isEventAuthor(#dto.eventId)")
    public EventDetailDTO update(EventUpdateRequestDTO dto) {
        validator.validateUpdateDTO(dto);

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));
        User user = userRepository.findById(dto.getUser().getId()).orElseThrow(
                () -> new IllegalStateException("Authorization cannot pass empty user into this method"));

        boolean changingPublicationStatus =
                null != dto.getPublished() && dto.getPublished().orElseThrow() != event.isPublished();

        boolean changingConflictJoint =
                null != dto.getConflictId() && (!dto.getConflictId().orElseThrow().equals(event.getConflict().getId()));

        businessValidationService.validate(
                new UserCanModerate(user).when(changingPublicationStatus
                        || null != dto.getLocalityId()
                        || changingConflictJoint
                ),
                new NotAParentEvent(event.getId(), eventRepository).when(changingConflictJoint)
        );

        //We need to know which titles was not translated before update (not to send push twice to locale topic)
        Set<Locale> nonLocalizedTitlesBeforeUpdate = Stream.of(Locale.values())
                .filter(loc -> null == event.getTitleByLocale(loc))
                .collect(Collectors.toSet());

        fillEventFields(event, dto, dto.getLocale());
        eventRepository.save(event);
        updateConflictsEventStatuses(event.getConflict().getId());

        businessValidationService.validate(
                new EventBeforeConflictsEnd(event.getDate(), event.getConflict().getDateTo()),
                new EventAfterConflictsStart(event.getDate(), event.getConflict().getDateFrom())
        );

        if (event.isPublished()) { //after update
            // titles which was not localized earlier and have been localized in this transaction
            Map<Locale, String> titlesLocalizedDuringThisUpdate = Stream.of(Locale.values())
                    .filter(loc -> !loc.equals(Locale.ALL))
                    .filter(nonLocalizedTitlesBeforeUpdate::contains)
                    .filter(loc -> null != event.getTitleByLocale(loc))
                    .collect(Collectors.toMap(Function.identity(), event::getTitleByLocale));

            if (titlesLocalizedDuringThisUpdate.containsKey(Locale.RU)) {
                if (dto.getPublishTo().contains(Networks.TELEGRAM.getId())) {
                    telegramService.sendToChannel(event);
                    vkService.sendToChannel(event);
                }
            }

            pushService.eventPublished(
                    dto.getEventId(),
                    null != event.getAuthor() ? event.getAuthor().getId() : null,
                    event.getLongitude(),
                    event.getLatitude(),
                    titlesLocalizedDuringThisUpdate,
                    null != event.getAuthor() ? event.getAuthor().getFcm() : null,
                    changingPublicationStatus //whether notify event's author or not
            );
        }

        return EventDetailDTO.of(event, dto.getLocale());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(Long eventId) {
        businessValidationService.validate(
                new NotAParentEvent(eventId, eventRepository)
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Событие не найдено")
        );
        long conflictId = event.getConflict().getId();
        eventRepository.delete(event);
        updateConflictsEventStatuses(conflictId);

        //If event was not published - notify its' author about rejection
        if (!event.isPublished() && null != event.getAuthor()) {
            pushService.eventDeclined(
                    event.getAuthor().getFcm(),
                    eventId,
                    event.getAuthor().getId()
            );
        }
    }

    public void updateConflictsEventStatuses(long conflictId) {
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new IllegalStateException("Cannot update events of unknown conflict"));

        List<Event> events = eventRepository.findAllByConflictId(conflictId).stream()
                .sorted(Comparator.comparing(PostEntity::getDate))
                .collect(Collectors.toList());

        if (events.isEmpty()) return;
        //first event is 'new' event (unless it's final one in the same moment)
        events.get(0).setStatus(eventStatusRepository.findFirstBySlug(EventStatus.NEW));

        //every 1..n event is 'intermediate'
        if (events.size() > 1) {
            EventStatus intermediateStatus = eventStatusRepository.findFirstBySlug(EventStatus.INTERMEDIATE);
            for (int i = 1; i < events.size(); i++) {
                events.get(i).setStatus(intermediateStatus);
            }
        }

        boolean conflictFinished = conflict.getDateTo() != null;

        if (conflictFinished) {
            //latest event of finished conflict is 'final' event
            events.get(events.size() - 1).setStatus(eventStatusRepository.findFirstBySlug(EventStatus.FINAL));
        }
    }

    private void fillEventFields(Event event, EventCreateRequestDTO dto, Locale locale) {
        //for the sake of PATCH ;)
        if (null != dto.getConflictId()) {
            setConflict(event, dto.getConflictId().orElseThrow());
        }
        if (null != dto.getDate()) {
            event.setDate(
                    LocalDateTime.ofEpochSecond(
                            dto.getDate().orElseThrow(() -> new IllegalStateException("Date cannot be null there!")),
                            0,
                            ZoneOffset.UTC));
        }
        if (null != dto.getSourceLink()) {
            event.setSourceLink(dto.getSourceLink().orElse(null));
        }
        if (null != dto.getTitleRu()) {
            event.setTitleRu(dto.getTitleRu().orElse(null));
        }
        if (null != dto.getTitleEn()) {
            event.setTitleEn(dto.getTitleEn().orElse(null));
        }
        if (null != dto.getTitleEs()) {
            event.setTitleEs(dto.getTitleEs().orElse(null));
        }
        if (null != dto.getTitleDe()) {
            event.setTitleDe(dto.getTitleDe().orElse(null));
        }
        if (null != dto.getContentRu()) {
            event.setContentRu(dto.getContentRu().orElse(null));
        }
        if (null != dto.getContentEn()) {
            event.setContentEn(dto.getContentEn().orElse(null));
        }
        if (null != dto.getContentEs()) {
            event.setContentEs(dto.getContentEs().orElse(null));
        }
        if (null != dto.getContentDe()) {
            event.setContentDe(dto.getContentDe().orElse(null));
        }
        if (null != dto.getPublished()) {
            event.setPublished(dto.getPublished().orElseThrow());
        }
        if (null != dto.getLatitude()) {
            event.setLatitude(dto.getLatitude().orElseThrow());
        }
        if (null != dto.getLongitude()) {
            event.setLongitude(dto.getLongitude().orElseThrow());
        }
        if (null != dto.getLocalityId()) {
            setLocality(event, dto.getLocalityId().orElse(null));
        }
        if (null != dto.getEventTypeId()) {
            setEventType(event, dto.getEventTypeId().orElse(null));
        }
        if (null != dto.getTitle()) {
            event.setTitleByLocale(locale, dto.getTitle().orElse(null));
        }
        if (null != dto.getContent()) {
            event.setContentByLocale(locale, dto.getContent().orElse(null));
        }
        if (null != dto.getPhotoUrls()) {
            syncPhotos(event, dto.getPhotoUrls().orElseThrow());
        }
        if (null != dto.getVideos()) {
            syncVideos(event, dto.getVideos().orElseThrow());
        }
        if (null != dto.getTags()) {
            syncTags(event, dto.getTags().orElseThrow());
        }
    }

    /**
     * Set event's conflict
     */
    private void setConflict(Event event, Long conflictId) {
        Conflict conflict = conflictRepository.getOne(conflictId);
        event.setConflict(conflict);
    }

    /**
     * Set locality. If received localityId equals to null, then set to null
     */
    private void setLocality(Event event, Long localityId) {
        Locality locality = null;

        if (null != localityId) {
            locality = localityRepository.getOne(localityId);
        }

        event.setLocality(locality);
    }

    /**
     * Set event's type. If received eventTypeId equals null, then set to null
     */
    private void setEventType(Event event, Long eventTypeId) {
        EventType eventType = null;

        if (null != eventTypeId) {
            eventType = eventTypeRepository.getOne(eventTypeId);
        }

        event.setType(eventType);
    }

    /**
     * Remove old photos and save received
     */
    private void syncPhotos(Event event, List<String> photoURLs) {
        photoRepository.deleteAll(event.getPhotos());
        event.setPhotos(photoURLs.stream()
                .map(Photo::new)
                .peek(photoRepository::save)
                .collect(Collectors.toSet()));
    }

    /**
     * Remove old videos and save received
     */
    private void syncVideos(Event event, List<VideoDTO> videos) {
        videoRepository.deleteAll(event.getVideos());
        event.setVideos(videos.stream()
                .map(videoDTO -> {
                    Video video = new Video();
                    video.setUrl(videoDTO.getUrl());
                    if (null != videoDTO.getPreviewUrl()) {
                        video.setPreviewUrl(videoDTO.getPreviewUrl().orElse(null));
                    }
                    video.setVideoType(videoTypeRepository.getOne(videoDTO.getVideoTypeId()));
                    return video;
                })
                .peek(videoRepository::save)
                .collect(Collectors.toSet())
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
                        .map(tagName -> tagRepository.findFirstByName(tagName)
                                .orElse(new Tag(tagName)))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Get related events grouped by containing conflicts.
     * Related means they belongs to same root conflict
     * @param event
     * @param locale
     * @return
     */
    private List<BriefConflictWithEventsDTO> getRelatives(Event event, Locale locale) {
        Conflict conflict = event.getConflict();
        if (conflict == null) {
            return new ArrayList<>();
        }

        //get root conflict
        Conflict rootConflict = conflictRepository.getRootConflict(conflict);

        //get all conflicts caused by root
        List<Conflict> conflictsOfRoot = conflictRepository.getDescendantsAndSelf(rootConflict);

        //Create list of conflict DTOs. Each containing its events list
        return conflictsOfRoot.stream()
                .map(conf -> {
                    BriefConflictWithEventsDTO confDTO = new BriefConflictWithEventsDTO();
                    confDTO.setId(conf.getId());
                    confDTO.setParentConflictId(conf.getParentId());
                    confDTO.setParentEventId(
                            Optional.ofNullable(conf.getParentEvent()).map(Event::getId).orElse(null));
                    confDTO.setEvents(conf.getEvents().stream()
                            .map(evn -> {
                                BriefEventDTO eventDTO = new BriefEventDTO();
                                eventDTO.setId(evn.getId());
                                eventDTO.setDate(evn.getDate().toEpochSecond(ZoneOffset.UTC));
                                eventDTO.setTitlesOf(evn, locale);
                                return eventDTO;
                            })
                            .sorted(Comparator.comparingLong(BriefEventDTO::getDate))
                            .collect(Collectors.toList())
                    );
                    return confDTO;
                })
                .collect(Collectors.toList());
    }
}
