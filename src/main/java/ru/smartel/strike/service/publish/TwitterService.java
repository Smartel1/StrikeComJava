package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.publication.PublishDTO;

@Service
public class TwitterService {
    public static final String TWITTER_CHANNEL = "twitterPubChannel";

    @ServiceActivator(inputChannel = TWITTER_CHANNEL)
    public void sendToChannel(PublishDTO data) {

    }
}
