package ru.smartel.strike.service.validation;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

public class BasePostDTOValidator {

    protected Map<String, String> validateListQueryDTO(BaseListRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getPage() < 1) {
            addErrorMessage("page", new Min(1), errors);
        }

        if (dto.getPerPage() < 1) {
            addErrorMessage("per_page", new Min(1), errors);
        }

        return errors;
    }

    protected Map<String, String> validateStoreDTO(PostRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getDate()) {
            addErrorMessage("date", new Required(), errors);
        } else if (dto.getDate().isEmpty()) {
            addErrorMessage("date", new NotNull(), errors);
        }

        validateCommon(dto, errors);

        return errors;
    }

    protected Map<String, String> validateUpdateDTO(PostRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (null != dto.getDate() && dto.getDate().isEmpty()) {
            addErrorMessage("date", new NotNull(), errors);
        }

        validateCommon(dto, errors);

        return errors;
    }

    /**
     * Validate fields whose checks are similar on update and create actions
     */
    private void validateCommon(PostRequestDTO dto, Map<String, String> errors) {

        if (null != dto.getPublished() && dto.getPublished().isEmpty()) {
            addErrorMessage("published", new NotNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title", new Max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_ru", new Max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_en", new Max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_es", new Max(255), errors);
            });
        }

        if (null != dto.getSourceLink()) {
            dto.getSourceLink().ifPresent(link -> {
                if (link.length() > 255) addErrorMessage("source_link", new Max(255), errors);
            });
        }

        if (null != dto.getContent()) {
            dto.getContent().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("content", new Min(3), errors);
            });
        }

        if (null != dto.getContentRu()) {
            dto.getContentRu().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("content_ru", new Min(3), errors);
            });
        }

        if (null != dto.getContentEn()) {
            dto.getContentEn().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("content_en", new Min(3), errors);
            });
        }

        if (null != dto.getContentEs()) {
            dto.getContentEs().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("content_es", new Min(3), errors);
            });
        }

        if (null != dto.getPhotoUrls()) {
            dto.getPhotoUrls().ifPresent((photoUrls) -> {
                    int i = 0;
                    for (String photoUrl : photoUrls) {
                        if (photoUrl.length() > 500) addErrorMessage("photo_urls[" + i + "]", new Max(500), errors);
                        i++;
                    }
                }
            );
        }

        if (null != dto.getTags()) {
            dto.getTags().ifPresent((tags) -> {
                        int i = 0;
                        for (String tag : tags) {
                            if (tag.length() > 20) addErrorMessage("tags[" + i + "]", new Max(20), errors);
                            if (tag.length() < 2) addErrorMessage("tags[" + i + "]", new Min(2), errors);
                            i++;
                        }
                    }
            );
        }

        if (null != dto.getVideos()) {
            dto.getVideos().ifPresent((videos) -> {
                        int i = 0;
                        for (VideoDTO video : videos) {
                            if (null == video.getUrl()) {
                                addErrorMessage("videos[" + i + "].url", new NotNull(), errors);
                            } else if (video.getUrl().length() > 500) {
                                addErrorMessage("videos[" + i + "].url", new Max(500), errors);
                            }

                            if (video.getPreviewUrl().isPresent()) {
                                if (video.getPreviewUrl().get().length() > 500) {
                                    addErrorMessage("videos[" + i + "].preview_url", new Max(500), errors);
                                }
                            }

                            if (null == video.getVideoTypeId()) {
                                addErrorMessage("videos[" + i + "].video_type_id", new NotNull(), errors);
                            }

                            i++;
                        }
                    }
            );
        }
    }
}
