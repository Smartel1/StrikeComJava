package ru.smartel.strike.service.validation;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.dto.service.network.Network;

import java.util.*;
import java.util.stream.Collectors;

import static ru.smartel.strike.util.ValidationUtil.*;

public class BasePostDTOValidator {

    protected Map<String, List<String>> validateListQueryDTO(BaseListRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (dto.getPage() < 1) {
            addErrorMessage("page", min(1), errors);
        }

        if (dto.getPerPage() < 1) {
            addErrorMessage("perPage", min(1), errors);
        }

        if (dto.getSort() != null) {
            if (dto.getSort().getField() == null) {
                addErrorMessage("sort.field", notNull(), errors);
            } else {
                List<String> availableSortFields = Arrays.asList("createdAt", "date", "views");
                if (!availableSortFields.contains(dto.getSort().getField())) {
                    addErrorMessage("sort.field", oneOf(availableSortFields), errors);
                }
            }
            if (dto.getSort().getOrder() == null) {
                addErrorMessage("sort.order", notNull(), errors);
            } else {
                List<String> availableSortOrders = Arrays.asList("asc", "desc");
                if (!availableSortOrders.contains(dto.getSort().getOrder())) {
                    addErrorMessage("sort.order", oneOf(availableSortOrders), errors);
                }
            }
        }

        return errors;
    }

    protected Map<String, List<String>> validateStoreDTO(PostRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getDate()) {
            addErrorMessage("date", required(), errors);
        } else if (dto.getDate().isEmpty()) {
            addErrorMessage("date", notNull(), errors);
        }

        if (null == dto.getPublishTo()) {
            addErrorMessage("publishTo", notNull(), errors);
        } else {
            Set<Long> availableNetworkIds = EnumSet.allOf(Network.class).stream().map(Network::getId).collect(Collectors.toSet());
            if (dto.getPublishTo().stream().anyMatch(networkId -> !availableNetworkIds.contains(networkId))) {
                addErrorMessage("publishTo", oneOf(availableNetworkIds), errors);
            }
        }

        validateCommon(dto, errors);

        return errors;
    }

    protected Map<String, List<String>> validateUpdateDTO(PostRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null != dto.getDate() && dto.getDate().isEmpty()) {
            addErrorMessage("date", notNull(), errors);
        }

        if (null == dto.getPublishTo()) {
            addErrorMessage("publishTo", notNull(), errors);
        } else {
            Set<Long> availableNetworkIds = EnumSet.allOf(Network.class).stream().map(Network::getId).collect(Collectors.toSet());
            if (dto.getPublishTo().stream().anyMatch(networkId -> !availableNetworkIds.contains(networkId))) {
                addErrorMessage("publishTo", oneOf(availableNetworkIds), errors);
            }
        }

        validateCommon(dto, errors);

        return errors;
    }

    /**
     * Validate fields whose checks are similar on update and create actions
     */
    private void validateCommon(PostRequestDTO dto, Map<String, List<String>> errors) {

        if (null != dto.getPublished() && dto.getPublished().isEmpty()) {
            addErrorMessage("published", notNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title", max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleRu", max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEn", max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEs", max(255), errors);
            });
        }

        if (null != dto.getTitleDe()) {
            dto.getTitleDe().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleDe", max(255), errors);
            });
        }

        if (null != dto.getSourceLink()) {
            dto.getSourceLink().ifPresent(link -> {
                if (link.length() > 255) addErrorMessage("sourceLink", max(255), errors);
            });
        }

        if (null != dto.getContent()) {
            dto.getContent().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("content", min(3), errors);
            });
        }

        if (null != dto.getContentRu()) {
            dto.getContentRu().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("contentRu", min(3), errors);
            });
        }

        if (null != dto.getContentEn()) {
            dto.getContentEn().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("contentEn", min(3), errors);
            });
        }

        if (null != dto.getContentEs()) {
            dto.getContentEs().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("contentEs", min(3), errors);
            });
        }

        if (null != dto.getContentDe()) {
            dto.getContentDe().ifPresent(content -> {
                if (content.length() < 3) addErrorMessage("contentDe", min(3), errors);
            });
        }

        if (null != dto.getPhotoUrls()) {
            dto.getPhotoUrls().ifPresent((photoUrls) -> {
                        int i = 0;
                        for (String photoUrl : photoUrls) {
                            if (photoUrl.length() > 500) addErrorMessage("photoUrls[" + i + "]", max(500), errors);
                            i++;
                        }
                    }
            );
        }

        if (null != dto.getTags()) {
            dto.getTags().ifPresent((tags) -> {
                        int i = 0;
                        for (String tag : tags) {
                            if (tag.length() > 20) addErrorMessage("tags[" + i + "]", max(20), errors);
                            if (tag.length() < 2) addErrorMessage("tags[" + i + "]", min(2), errors);
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
                                addErrorMessage("videos[" + i + "].url", notNull(), errors);
                            } else if (video.getUrl().length() > 500) {
                                addErrorMessage("videos[" + i + "].url", max(500), errors);
                            }

                            if (null != video.getPreviewUrl() && video.getPreviewUrl().isPresent()) {
                                if (video.getPreviewUrl().get().length() > 500) {
                                    addErrorMessage("videos[" + i + "].previewUrl", max(500), errors);
                                }
                            }

                            if (null == video.getVideoTypeId()) {
                                addErrorMessage("videos[" + i + "].videoTypeId", notNull(), errors);
                            }

                            i++;
                        }
                    }
            );
        }
    }
}
