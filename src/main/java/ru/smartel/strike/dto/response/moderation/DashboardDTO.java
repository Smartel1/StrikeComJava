package ru.smartel.strike.dto.response.moderation;

public class DashboardDTO {
    private Long nonpublishedEventsCount;
    private Long nonpublishedNewsCount;

    public static DashboardDTO of(Long nonpublishedEventsCount, Long nonpublishedNewsCount) {
        DashboardDTO instance = new DashboardDTO();
        instance.setNonpublishedEventsCount(nonpublishedEventsCount);
        instance.setNonpublishedNewsCount(nonpublishedNewsCount);
        return instance;
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
