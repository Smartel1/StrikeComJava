package ru.smartel.strike.service.impl;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.BaseDTOValidator;

import java.util.HashMap;
import java.util.Map;

public class BasePostDTOValidator extends BaseDTOValidator {

    void validateListQueryDTO(BaseListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getPage(), "page", errors).min(1);
        check(dto.getPerPage(), "per_page", errors).min(1);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    void validateStoreDTO(PostRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getDate(), "date", errors).requiredOptional().notNull();
        validateCommon(dto, errors);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    void validateUpdateDTO(PostRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getDate(), "date", errors).notNull();
        validateCommon(dto, errors);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    /**
     * Validate fields whose checks are similar on update and create actions
     */
    private void validateCommon(PostRequestDTO dto, Map<String, String> errors) {
        check(dto.getPublished(), "published", errors).notNull();
        check(dto.getTitle(), "title", errors).maxLength(255);
        check(dto.getTitleRu(), "title_ru", errors).maxLength(255);
        check(dto.getTitleEn(), "title_en", errors).maxLength(255);
        check(dto.getTitleEs(), "title_es", errors).maxLength(255);
        check(dto.getSourceLink(), "source_link", errors).maxLength(255);
        check(dto.getContent(), "content", errors).minLength(3);
        check(dto.getContentRu(), "content_ru", errors).minLength(3);
        check(dto.getContentEn(), "content_en", errors).minLength(3);
        check(dto.getContentEs(), "content_es", errors).minLength(3);

        if (null != dto.getPhotoUrls()) {
            check(dto.getPhotoUrls(), "photo_urls", errors).notNull();
            dto.getPhotoUrls().ifPresent((photoUrls) -> {
                    int i = 0;
                    for (String photoUrl : photoUrls) {
                        check(photoUrl, "photo_urls[" + i + "]", errors).maxLength(500);
                        i++;
                    }
                }
            );
        }

        if (null != dto.getTags()) {
            check(dto.getTags(), "tags", errors).notNull();
            dto.getTags().ifPresent((tags) -> {
                        int i = 0;
                        for (String tag : tags) {
                            check(tag, "tags[" + i + "]", errors).minLength(2).maxLength(20);
                            i++;
                        }
                    }
            );
        }

        if (null != dto.getVideos()) {
            check(dto.getVideos(), "videos", errors).notNull();
            dto.getVideos().ifPresent((videos) -> {
                        int i = 0;
                        for (VideoDTO video : videos) {
                            check(video.getUrl(), "videos[" + i + "].url", errors).notNull().maxLength(500);
                            check(video.getPreviewUrl(), "videos[" + i + "].preview_url", errors).maxLength(500);
                            check(video.getVideoTypeId(), "videos[" + i + "].video_type_id", errors);
                            i++;
                        }
                    }
            );
        }
    }
}
