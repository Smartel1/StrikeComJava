package ru.smartel.strike.dto.request.conflict;

import io.swagger.annotations.ApiParam;

import java.util.List;

public class ConflictFiltersDTO {
    @ApiParam(value = "Фильтр отсекает те конфликты, которые закончились строго ранее этой даты")
    private Integer dateFrom;
    @ApiParam(value = "Фильтр отсекает те конфликты, которые начались строго позже этой даты")
    private Integer dateTo;
    @ApiParam(value = "Полнотекстовый поиск. В выбору попадут конфликты, в названии которых содержится заданный текст, а также если текст содержится в заголовках или описаниях событий (на всех языках)")
    private String fulltext;
    @ApiParam(value = "Id результата конфликта (из справочника)")
    private List<Long> conflictResultIds;
    @ApiParam(value = "Id причины конфликта (из справочника)")
    private List<Long> conflictReasonIds;
    @ApiParam(value = "Id основной формы конфликта (из справочника типов событий). Допускается null")
    private List<String> mainTypeIds;
    @ApiParam(value = "Id конфликта. Фильтр выводит только те конфликты, которые являются родителями (не только прямыми) переданного конфликта")
    private Long ancestorsOf;
    @ApiParam(value = "Id конфликта. Фильтр выводит только те конфликты, которые являются прямыми потомками переданного конфликта")
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

    public List<String> getMainTypeIds() {
        return mainTypeIds;
    }

    public void setMainTypeIds(List<String> mainTypeIds) {
        this.mainTypeIds = mainTypeIds;
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
