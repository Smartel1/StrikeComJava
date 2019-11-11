package ru.smartel.strike.dto.request.conflict;


/**
 * dto for updating conflict
 */
public class ConflictUpdateRequestDTO extends ConflictCreateRequestDTO {
    private Long conflictId;

    public Long getConflictId() {
        return conflictId;
    }

    public void setConflictId(Long conflictId) {
        this.conflictId = conflictId;
    }
}
