package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ru.smartel.strike.dto.publication.PublishDTO;
import ru.smartel.strike.dto.publication.PublishDTOWithNetworks;
import ru.smartel.strike.dto.service.sort.network.Network;

import java.util.stream.Collectors;

import static ru.smartel.strike.integration.PublicationGateway.PUBLICATION_CHANNEL;
import static ru.smartel.strike.service.publish.InstagramService.INSTAGRAM_CHANNEL;
import static ru.smartel.strike.service.publish.OkService.OK_CHANNEL;
import static ru.smartel.strike.service.publish.TelegramService.TELEGRAM_CHANNEL;
import static ru.smartel.strike.service.publish.TwitterService.TWITTER_CHANNEL;
import static ru.smartel.strike.service.publish.VkService.VK_CHANNEL;

@Configuration
public class IntegrationConf {
    public static final String NETWORK_HEADER = "networkId";

    @Bean
    public IntegrationFlow publicationFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct(PUBLICATION_CHANNEL))
                .<PublishDTOWithNetworks>filter(data -> !data.getPublishTo().isEmpty())
                .split(splitter())
                .<PublishDTO, PublishDTO>transform(data -> {
                    //if videos contain sourceUrl -> remove this url from videos
                    data.getVideoUrls().remove(data.getSourceUrl());
                    return data;
                })
                .route("headers." + NETWORK_HEADER, r -> r
                        .channelMapping(Network.TELEGRAM.getId(), TELEGRAM_CHANNEL)
                        .channelMapping(Network.VK.getId(), VK_CHANNEL)
                        .channelMapping(Network.OK.getId(), OK_CHANNEL)
                        .channelMapping(Network.TWITTER.getId(), TWITTER_CHANNEL)
                        .channelMapping(Network.INSTAGRAM.getId(), INSTAGRAM_CHANNEL))
                .get();
    }

    /**
     * Splits messages by destination Network and sets appropriate header
     */
    private AbstractMessageSplitter splitter() {
        return new AbstractMessageSplitter() {
            @Override
            protected Object splitMessage(Message<?> message) {
                PublishDTOWithNetworks dto = (PublishDTOWithNetworks) message.getPayload();
                return dto.getPublishTo().stream()
                        .map(networkId -> MessageBuilder
                                .withPayload(dto.getData())
                                .setHeaderIfAbsent(NETWORK_HEADER, networkId)
                                .build())
                        .collect(Collectors.toList());
            }
        };
    }

}
