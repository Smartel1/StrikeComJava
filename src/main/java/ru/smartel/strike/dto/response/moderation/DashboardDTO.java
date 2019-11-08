package ru.smartel.strike.dto.response.moderation;

public class DashboardDTO {
    private Long nonpublishedEventsCount;
    private Long nonpublishedNewsCount;

    public DashboardDTO(Long nonpublishedEventsCount, Long nonpublishedNewsCount) {
        this.nonpublishedEventsCount = nonpublishedEventsCount;
        this.nonpublishedNewsCount = nonpublishedNewsCount;
    }

    public Long getNonpublishedEventsCount() {
        return nonpublishedEventsCount;
    }

    public void setNonpublishedEventsCount(Long nonpublishedEventsCount) {
        this.nonpublishedEventsCount = nonpublishedEventsCount;
    }

    public Long getNonpublishedNewsCount() {
        return nonpublishedNewsCount;
    }

    public void setNonpublishedNewsCount(Long nonpublishedNewsCount) {
        this.nonpublishedNewsCount = nonpublishedNewsCount;
    }
}
