package ru.smartel.strike.dto.request.news;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.post.PostRequestDTO;


/**
 * dto for creating/updating news requests
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsRequestDTO extends PostRequestDTO {
}
