package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.smartel.strike.configuration.properties.TelegramProperties;
import ru.smartel.strike.dto.publication.PublishDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class TelegramService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);
    public static final String SEND_MESSAGE_URL = "https://api.telegram.org/bot%s:%s/sendMessage";
    public static final String TELEGRAM_CHANNEL = "telegramPubChannel";

    private final RestTemplate restTemplate;
    private final TelegramProperties properties;

    public TelegramService(RestTemplate restTemplate, TelegramProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Send post content and links to telegram channel
     */
    @ServiceActivator(inputChannel = TELEGRAM_CHANNEL)
    public void sendToChannel(PublishDTO data) {
        if (!properties.isSpecified()) {
            return;
        }
        logger.info("Sending post to telegram");

        String text = data.getText();
        if (!data.getVideoUrls().isEmpty()) {
            text = text + data.getVideoUrls().stream()
                    .map(url -> "<i><a href='" + url + "'>видео</a></i>")
                    .collect(Collectors.joining(", ", "\n\n", ""));
        }
        if (nonNull(data.getSourceUrl())) {
            if (data.getVideoUrls().isEmpty()) {
                text = text + "\n\n<i><a href='" + data.getSourceUrl() + "'>источник</a></i>";
                text = text + "\n\n<i><a href='" + data.getSitePageUrl() + "'>на сайте</a></i>";
            } else {
                text = text + "; <i><a href='" + data.getSourceUrl() + "'>источник</a></i>";
                text = text + "; <i><a href='" + data.getSitePageUrl() + "'>на сайте</a></i>";
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
