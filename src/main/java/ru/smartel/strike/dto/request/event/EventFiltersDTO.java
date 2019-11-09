package ru.smartel.strike.dto.request.event;

import ru.smartel.strike.dto.request.post.PostFiltersDTO;

import java.util.List;

public class EventFiltersDTO extends PostFiltersDTO {
    private List<Long> conflictIds;
    private List<Long> eventStatusIds;
    private List<Long> eventTypeIds;
    private List<Long> countryIds;
    private List<Long> regionIds;
    private String containsContent;
    private SearchArea near;

    public List<Long> getConflictIds() {
        return conflictIds;
    }

    public void setConflictIds(List<Long> conflictIds) {
        this.conflictIds = conflictIds;
    }

    public List<Long> getEventStatusIds() {
        return eventStatusIds;
    }

    public void setEventStatusIds(List<Long> eventStatusIds) {
        this.eventStatusIds = eventStatusIds;
    }

    public List<Long> getEventTypeIds() {
        return eventTypeIds;
    }

    public void setEventTypeIds(List<Long> eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
    }

    public List<Long> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<Long> countryIds) {
        this.countryIds = countryIds;
    }

    public List<Long> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(List<Long> regionIds) {
        this.regionIds = regionIds;
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
