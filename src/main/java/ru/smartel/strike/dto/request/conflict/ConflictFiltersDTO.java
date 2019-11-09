package ru.smartel.strike.dto.request.conflict;

import java.util.List;

public class ConflictFiltersDTO {
    private Integer dateFrom;
    private Integer dateTo;
    private List<Integer> conflictResultIds;
    private List<Integer> conflictReasonIds;
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

    public List<Integer> getConflictResultIds() {
        return conflictResultIds;
    }

    public void setConflictResultIds(List<Integer> conflictResultIds) {
        this.conflictResultIds = conflictResultIds;
    }

    public List<Integer> getConflictReasonIds() {
        return conflictReasonIds;
    }

    public void setConflictReasonIds(List<Integer> conflictReasonIds) {
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
        private Double lat;
        private Double lng;
        private Integer radius;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
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
