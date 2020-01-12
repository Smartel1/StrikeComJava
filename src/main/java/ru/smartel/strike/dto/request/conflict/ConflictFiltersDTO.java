package ru.smartel.strike.dto.request.conflict;

import java.util.List;

public class ConflictFiltersDTO {
    private Integer dateFrom;
    private Integer dateTo;
    private String fulltext;
    private List<Long> conflictResultIds;
    private List<Long> conflictReasonIds;
    private Long ancestorsOf;
    private Long childrenOf;
    private SearchArea near;

    public Integer getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Integer dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Integer getDateTo() {
        return dateTo;
    }

    public void setDateTo(Integer dateTo) {
        this.dateTo = dateTo;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public List<Long> getConflictResultIds() {
        return conflictResultIds;
    }

    public void setConflictResultIds(List<Long> conflictResultIds) {
        this.conflictResultIds = conflictResultIds;
    }

    public List<Long> getConflictReasonIds() {
        return conflictReasonIds;
    }

    public void setConflictReasonIds(List<Long> conflictReasonIds) {
        this.conflictReasonIds = conflictReasonIds;
    }

    public Long getAncestorsOf() {
        return ancestorsOf;
    }

    public void setAncestorsOf(Long ancestorsOf) {
        this.ancestorsOf = ancestorsOf;
    }

    public Long getChildrenOf() {
        return childrenOf;
    }

    public void setChildrenOf(Long childrenOf) {
        this.childrenOf = childrenOf;
    }

    public SearchArea getNear() {
        return near;
    }

    public void setNear(SearchArea near) {
        this.near = near;
    }

    public static class SearchArea {
        private Float lat;
        private Float lng;
        private Integer radius;

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLng() {
            return lng;
        }

        public void setLng(Float lng) {
            this.lng = lng;
        }

        public Integer getRadius() {
            return radius;
        }

        public void setRadius(Integer radius) {
            this.radius = radius;
        }
    }
}
