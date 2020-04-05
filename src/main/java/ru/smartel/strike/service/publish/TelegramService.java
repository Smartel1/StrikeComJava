package ru.smartel.strike.service.publish;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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
        Map<String, String> payload = new HashMap<>();
        payload.put("chat_id", "@" + properties.getChatId());
        String text = post.getContentRu();
        if (!post.getPhotos().isEmpty()) {
            text = text + post.getPhotos().stream()
                    .map(Photo::getUrl)
                    .collect(Collectors.joining("\n", "\n", ""));
        }
        if (!post.getVideos().isEmpty()) {
            text = text + post.getVideos().stream()
                    .map(Video::getUrl)
                    .collect(Collectors.joining("\n", "\n", ""));
        }
        if (nonNull(post.getSourceLink())) {
            text = text + "\n" + post.getSourceLink();
        }
        payload.put("text", text);
        restTemplate.postForLocation(
                String.format(SEND_MESSAGE_URL, properties.getBotId(), properties.getBotToken()),
                payload);
    }
}
