package ru.smartel.strike.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.publication.PublishDTOWithNetworks;

import static ru.smartel.strike.integration.PublicationGateway.PUBLICATION_CHANNEL;

/**
 * Gateway for publishing posts to networks such as VK, telegram, OK etc
 */
@Component
@MessagingGateway(defaultRequestChannel = PUBLICATION_CHANNEL)
public interface PublicationGateway {
    String PUBLICATION_CHANNEL = "publicationChannel";

    /**
     * Publish post to networks
     *
     * @param message publishDto and set of network ids
     */
    @Async
    void publish(PublishDTOWithNetworks message);
}
