package ru.smartel.strike.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "publications.telegram")
public class TelegramProperties {
    public static final Logger logger = LoggerFactory.getLogger(TelegramProperties.class);
    private String chatId;
    private String botId;
    private String botToken;
    private boolean specified = false;

    @PostConstruct
    public void setSpecified() {
        specified = !chatId.isBlank() && !botId.isBlank() && !botToken.isBlank();
        if (specified) {
            logger.debug("Telegram properties: chatId: {}, botId: {}, botToken: {}", chatId, botId, botToken);
        } else {
            logger.debug("Telegram properties not specified");
        }
    }

    public boolean isSpecified() {
        return specified;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
