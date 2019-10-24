package ru.smartel.strike.dto.request.event;

import ru.smartel.strike.dto.request.post.PostFiltersDTO;

import java.util.List;

public class EventFiltersDTO extends PostFiltersDTO {
    private List<Integer> conflictIds;
    private List<Integer> eventStatusIds;
    private List<Integer> eventTypeIds;
    private List<Integer> countryIds;
    private List<Integer> regionIds;
    private String containsContent;
    private SearchArea near;

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
