package ru.smartel.strike.dto.request.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiParam;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostFiltersDTO {
    @ApiParam(value = "Массив тегов. В выборку попадут посты, содержащие хотя бы один из указанных тегов")
    private List<String> tags;
    @ApiParam(value = "Фильтр отсекает посты, которые произошли до этой даты")
    private Integer dateFrom;
    @ApiParam(value = "Фильтр отсекает посты, которые произошли после этой даты")
    private Integer dateTo;
    @ApiParam(value = "true - выведутся только избранные, иначе - все. Работает только для аутентифицированных пользователей")
    private Boolean favourites;
    @ApiParam(value = "Если true, то только опубликованные, false - только неопубликованные, по-умолчанию - все")
    private Boolean published;
    @ApiParam(value = "Фильтр для полнотекстового поиска. Ищет подстроку в заголовках и содержимом на всех языках")
    private String fulltext;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }
}
