package ru.smartel.strike.dto.request.event;

import io.swagger.annotations.ApiParam;
import ru.smartel.strike.dto.request.post.PostFiltersDTO;

import java.util.List;

public class EventFiltersDTO extends PostFiltersDTO {
    private List<Long> conflictIds;
    private List<Long> eventStatusIds;
    private List<Long> eventTypeIds;
    private List<Long> countryIds;
    private List<Long> regionIds;
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

    public SearchArea getNear() {
        return near;
    }

    public void setNear(SearchArea near) {
        this.near = near;
    }

    public static class SearchArea {
        @ApiParam(value = "Широта центра поиска")
        private Float lat;
        @ApiParam(value = "Долгота центра поиска")
        private Float lng;
        @ApiParam(value = "Радиус поиска в километрах")
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
