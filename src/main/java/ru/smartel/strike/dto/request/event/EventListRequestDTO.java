package ru.smartel.strike.dto.request.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventListRequestDTO {
    public static final int DEFAULT_PAGE_CAPACITY = 20;
    public static final int DEFAULT_PAGE = 1;

    private FiltersBag filters;
    private Integer perPage = DEFAULT_PAGE_CAPACITY;
    private Integer page = DEFAULT_PAGE;

    public FiltersBag getFilters() {
        return filters;
    }

    public void setFilters(FiltersBag filters) {
        this.filters = filters;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public static class FiltersBag {
        private Integer tagId;
        private Integer dateFrom;
        private Integer dateTo;
        private List<Integer> conflictIds;
        private List<Integer> eventStatusIds;
        private List<Integer> eventTypeIds;
        private List<Integer> countryIds;
        private List<Integer> regionIds;
        private Boolean favourites;
        private Boolean published;
        private String containsContent;
        private SearchArea near;

        public Integer getTagId() {
            return tagId;
        }

        public void setTagId(Integer tagId) {
            this.tagId = tagId;
        }

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

        public List<Integer> getConflictIds() {
            return conflictIds;
        }

        public void setConflictIds(List<Integer> conflictIds) {
            this.conflictIds = conflictIds;
        }

        public List<Integer> getEventStatusIds() {
            return eventStatusIds;
        }

        public void setEventStatusIds(List<Integer> eventStatusIds) {
            this.eventStatusIds = eventStatusIds;
        }

        public List<Integer> getEventTypeIds() {
            return eventTypeIds;
        }

        public void setEventTypeIds(List<Integer> eventTypeIds) {
            this.eventTypeIds = eventTypeIds;
        }

        public List<Integer> getCountryIds() {
            return countryIds;
        }

        public void setCountryIds(List<Integer> countryIds) {
            this.countryIds = countryIds;
        }

        public List<Integer> getRegionIds() {
            return regionIds;
        }

        public void setRegionIds(List<Integer> regionIds) {
            this.regionIds = regionIds;
        }

        public Boolean getFavourites() {
            return favourites;
        }

        public void setFavourites(Boolean favourites) {
            this.favourites = favourites;
        }

        public Boolean getPublished() {
            return published;
        }

        public void setPublished(Boolean published) {
            this.published = published;
        }

        public String getContainsContent() {
            return containsContent;
        }

        public void setContainsContent(String containsContent) {
            this.containsContent = containsContent;
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
}
