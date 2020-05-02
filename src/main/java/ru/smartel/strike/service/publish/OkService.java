package ru.smartel.strike.service.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import ru.smartel.strike.configuration.properties.OkProperties;
import ru.smartel.strike.dto.publication.PublishDTO;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class OkService {
    private static final Logger logger = LoggerFactory.getLogger(OkService.class);
    public static final String SEND_MESSAGE_URL = "https://api.ok.ru/fb.do?application_key={appKey}" +
            "&attachment={attachment}" +
            "&format=json" +
            "&gid={gid}" +
            "&method=mediatopic.post" +
            "&type=GROUP_THEME" +
            "&sig={sig}" +
            "&access_token={accessToken}";
    public static final String OK_CHANNEL = "okPubChannel";

    private final RestTemplate restTemplate;
    private final OkProperties properties;
    private final ObjectMapper objectMapper;

    public OkService(RestTemplate restTemplate, OkProperties properties, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    /**
     * Send post content and links to OK group
     */
    @ServiceActivator(inputChannel = OK_CHANNEL)
    public void sendToGroup(PublishDTO data) {
        if (!properties.isSpecified()) {
            return;
        }
        logger.info("Sending post to OK");

        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.media.add(new TextMedia(data.getText().replace("\r", " ")));

        if (!data.getVideoUrls().isEmpty()) {
            data.getVideoUrls().forEach(url -> attachmentDto.media.add(new LinkMedia(url)));
        }
        if (nonNull(data.getSourceUrl())) {
            attachmentDto.media.add(new LinkMedia(data.getSourceUrl()));
        }

        String attachmentJson;
        try {
            attachmentJson = objectMapper.writeValueAsString(attachmentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        String signature = getSig(properties.getAppKey(), attachmentJson, properties.getGid(),
                properties.getAccessToken(), properties.getAppSecret());

        try {
            logger.info("OK response: {}", restTemplate.getForObject(
                    new UriTemplate(SEND_MESSAGE_URL).expand(
                            properties.getAppKey(), attachmentJson,
                            properties.getGid(), signature, properties.getAccessToken()),
                    String.class));
        } catch (RestClientException ex) {
            logger.error("Sending to OK group failed: {}", ex.getMessage());
        }
    }

    /**
     * Generate signature. See OK API doc for details
     */
    private String getSig(String applicationKey, String attachment, String gid, String accessToken, String appSecret) {
        return DigestUtils.md5Hex("application_key=" + applicationKey +
                "attachment=" + attachment +
                "format=json" +
                "gid=" + gid +
                "method=mediatopic.post" +
                "type=GROUP_THEME" +
                DigestUtils.md5Hex(accessToken + appSecret));
    }

    private static class AttachmentDto {
        List<Media> media = new ArrayList<>();

        public List<Media> getMedia() {
            return media;
        }

        public void setMedia(List<Media> media) {
            this.media = media;
        }
    }

    private interface Media {}

    private static class TextMedia implements Media {
        String type = "text";
        String text;

        public TextMedia(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private static class LinkMedia implements Media {
        String type = "link";
        String url;

        public LinkMedia(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
