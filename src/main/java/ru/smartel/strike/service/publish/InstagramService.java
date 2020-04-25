package ru.smartel.strike.service.publish;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.publication.PublishDTO;

@Service
public class InstagramService {
    public static final String INSTAGRAM_CHANNEL = "instagramPubChannel";

    @ServiceActivator(inputChannel = INSTAGRAM_CHANNEL)
    public void sendToChannel(PublishDTO data) {

    }
}
