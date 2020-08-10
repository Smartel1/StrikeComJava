package ru.smartel.strike.service.conflict;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.service.event.type.EventTypeCountDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.event.EventTypeRepository;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConflictMainTypeService {
    /**
     * count of events with one type needed to set this type to conflict
     */
    public static final int EVENT_TYPE_THRESHOLD = 20;

    private final ConflictRepository conflictRepository;
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;

    public ConflictMainTypeService(ConflictRepository conflictRepository, EventRepository eventRepository, EventTypeRepository eventTypeRepository) {
        this.conflictRepository = conflictRepository;
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    /**
     * Recalculate main conflict type (if this property is automanaged)
     *
     * @param conflictId conflict id
     */
    public void refreshMainType(long conflictId) {
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new IllegalArgumentException("Conflict not found"));
        if (!conflict.isAutomanagingMainType()) {
            return;
        }
        Long newTypeId = calculateMainType(conflictId);
        if (newTypeId == null && conflict.getMainType() != null) {
            conflict.setMainType(null);
        } else if (newTypeId != null && (conflict.getMainType() == null || !newTypeId.equals(conflict.getMainType().getId()))) {
            conflict.setMainType(eventTypeRepository.getOne(newTypeId));
        }
    }

    /**
     * Calculate main type of conflict depending on it's events' types
     *
     * @param conflictId conflict id
     * @return main type id or null, if cannot calculate
     */
    private Long calculateMainType(long conflictId) {
        var presentTypes = eventRepository.getEventTypesCountByConflictId(conflictId);
        if (presentTypes.isEmpty()) {
            return null;
        }

        // if all events has the same type, then set this type to conflict
        if (presentTypes.size() == 1) {
            return presentTypes.get(0).getTypeId();
        }

        // If more than EVENT_TYPE_THRESHOLD events has the same type, then set this type to conflict.
        // But if 2 or more type are present in more than EVENT_TYPE_THRESHOLD events, then set null.
        var dominatingTypes = presentTypes.stream()
                .filter(presentType -> presentType.getEventsCount() >= EVENT_TYPE_THRESHOLD)
                .collect(toList());
        if (!dominatingTypes.isEmpty()) {
            if (dominatingTypes.size() == 1) {
                return dominatingTypes.get(0).getTypeId();
            }
            return null;
        }

        // If one of the types present with maximum events count and has maximum priority - it wins
        int maxTypePriority = presentTypes.stream()
                .map(EventTypeCountDTO::getPriority)
                .max(Integer::compareTo).orElseThrow(() -> new IllegalStateException("type priority cannot be null"));

        if (maxTypePriority == 0) {
            return null;
        }

        long maxEventsCount = presentTypes.stream()
                .map(EventTypeCountDTO::getEventsCount)
                .max(Long::compareTo).orElseThrow(() -> new IllegalStateException("events count cannot be null"));

        var winType = presentTypes.stream()
                .filter(presentType -> presentType.getEventsCount() == maxEventsCount)
                .filter(presentType -> presentType.getPriority() == maxTypePriority)
                .findFirst();

        return winType.map(EventTypeCountDTO::getTypeId).orElse(null);
    }
}
