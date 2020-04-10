package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.smartel.strike.configuration.properties.TelegramProperties;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Video;
import ru.smartel.strike.entity.interfaces.PostEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class TelegramService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);
    public static final String SEND_MESSAGE_URL = "https://api.telegram.org/bot%s:%s/sendMessage";
    private RestTemplate restTemplate;
    private TelegramProperties properties;

    public TelegramService(RestTemplate restTemplate, TelegramProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Send post content and links to telegram channel
     * @param post post to publish
     */
    @Async
    public void sendToChannel(PostEntity post) {
        if (!properties.isSpecified()) {
            return;
        }
        logger.info("Sending post {} with id {} to telegram", post.getClass().getSimpleName(), post.getId());

        String text = post.getContentRu();
        if (!post.getVideos().isEmpty()) {
            text = text + post.getVideos().stream()
                    .map(Video::getUrl)
                    .map(url -> "<i><a href='" + url + "'>видео</a></i>")
                    .collect(Collectors.joining(", ", "\n\n", ""));
        }
        if (nonNull(post.getSourceLink())) {
            if (post.getVideos().isEmpty()) {
                text = text + "\n\n<i><a href='" + post.getSourceLink() + "'>источник</a></i>";
            } else {
                text = text + "; <i><a href='" + post.getSourceLink() + "'>источник</a></i>";
            }
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("chat_id", properties.getChatId());
        payload.put("parse_mode", "HTML");
        payload.put("text", text);

        String sendUrl = String.format(SEND_MESSAGE_URL, properties.getBotId(), properties.getBotToken());
        try {
            restTemplate.postForLocation(
                    sendUrl,
                    payload);
        } catch (RestClientException ex) {
            logger.error("Sending {} to telegram channel failed: {}", sendUrl, ex.getMessage());
        }
    }
}
