package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.smartel.strike.configuration.properties.VkProperties;
import ru.smartel.strike.entity.Video;
import ru.smartel.strike.entity.interfaces.PostEntity;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class VkService {
    private static final Logger logger = LoggerFactory.getLogger(VkService.class);
    public static final String SEND_MESSAGE_URL = "https://api.vk.com/method/wall.post";
    public static final String API_VERSION = "5.68";
    private final RestTemplate restTemplate;
    private final VkProperties properties;

    public VkService(RestTemplate restTemplate, VkProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Send post content and links to vk group
     * @param post post to publish
     */
    @Async
    public void sendToChannel(PostEntity post) {
        if (!properties.isSpecified()) {
            return;
        }
        logger.info("Sending post {} with id {} to vk", post.getClass().getSimpleName(), post.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("owner_id", properties.getGroupId());
        payload.add("access_token", properties.getAccessToken());
        payload.add("v", API_VERSION);
        payload.add("from_group", "1");

        String text = post.getContentRu();
        if (!post.getVideos().isEmpty()) {
            text = text + post.getVideos().stream()
                    .map(Video::getUrl)
                    .collect(Collectors.joining("\n", "\n\n", ""));
            payload.add("attachments", post.getVideos().stream().findFirst().map(Video::getUrl).orElse(null));
        }
        if (nonNull(post.getSourceLink())) {
            if (post.getVideos().isEmpty()) {
                payload.add("attachments", post.getSourceLink());
                text = text + "\n";
            }
            text = text + "\n" + post.getSourceLink();
        }
        payload.add("message", text);

        try {
            var response = restTemplate.postForObject(SEND_MESSAGE_URL, new HttpEntity<>(payload, headers), String.class);
            logger.info("Received response from VK: {}", response);
        } catch (RestClientException ex) {
            logger.error("Sending {} to vk group failed: {}", payload, ex.getMessage());
        }
    }
}
