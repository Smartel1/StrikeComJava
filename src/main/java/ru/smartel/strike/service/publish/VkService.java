package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.smartel.strike.configuration.properties.VkProperties;
import ru.smartel.strike.dto.publication.PublishDTO;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class VkService {
    private static final Logger logger = LoggerFactory.getLogger(VkService.class);
    public static final String SEND_MESSAGE_URL = "https://api.vk.com/method/wall.post";
    public static final String API_VERSION = "5.68";
    public static final String VK_CHANNEL = "vkPubChannel";

    private final RestTemplate restTemplate;
    private final VkProperties properties;

    public VkService(RestTemplate restTemplate, VkProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Send post content and links to vk group
     */
    @ServiceActivator(inputChannel = VK_CHANNEL)
    public void sendToChannel(PublishDTO data) {
        if (!properties.isSpecified()) {
            return;
        }
        logger.info("Sending post to vk");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("owner_id", properties.getGroupId());
        payload.add("access_token", properties.getAccessToken());
        payload.add("v", API_VERSION);
        payload.add("from_group", "1");

        String text = data.getText();
        if (!data.getVideoUrls().isEmpty()) {
            text = text + data.getVideoUrls().stream()
                    .collect(Collectors.joining("\n", "\n\n", ""));
            payload.add("attachments", data.getVideoUrls().stream().findFirst().orElse(null));
        }
        //dont expose source link if one of the videos is the same URL
        if (nonNull(data.getSourceUrl()) && !data.getVideoUrls().contains(data.getSourceUrl())) {
            if (data.getVideoUrls().isEmpty()) {
                payload.add("attachments", data.getSourceUrl());
                text = text + "\n";
            }
            text = text + "\n" + data.getSourceUrl();
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
