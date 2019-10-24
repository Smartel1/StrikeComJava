package ru.smartel.strike.dto.request.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsListRequestDTO {
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
        private Boolean favourites;
        private Boolean published;

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
    }
}
