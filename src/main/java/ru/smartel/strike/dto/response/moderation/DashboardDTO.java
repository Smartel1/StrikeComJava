package ru.smartel.strike.dto.response.moderation;

public class DashboardDTO {
    private Long complaintCommentsCount;
    private Long nonpublishedEventsCount;
    private Long nonpublishedNewsCount;

    public DashboardDTO(Long complaintCommentsCount, Long nonpublishedEventsCount, Long nonpublishedNewsCount) {
        this.complaintCommentsCount = complaintCommentsCount;
        this.nonpublishedEventsCount = nonpublishedEventsCount;
        this.nonpublishedNewsCount = nonpublishedNewsCount;
    }

    public Long getComplaintCommentsCount() {
        return complaintCommentsCount;
    }

    public void setComplaintCommentsCount(Long complaintCommentsCount) {
        this.complaintCommentsCount = complaintCommentsCount;
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
