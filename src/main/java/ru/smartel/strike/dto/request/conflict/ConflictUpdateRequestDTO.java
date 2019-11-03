package ru.smartel.strike.dto.request.conflict;


/**
 * dto for updating conflict
 */
public class ConflictUpdateRequestDTO extends ConflictCreateRequestDTO {
    private Integer conflictId;

    public Integer getConflictId() {
        return conflictId;
    }

    public void setConflictId(Integer conflictId) {
        this.conflictId = conflictId;
    }
}
