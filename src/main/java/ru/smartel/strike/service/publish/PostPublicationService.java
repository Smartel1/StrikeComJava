package ru.smartel.strike.service.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.publication.PublishDTO;
import ru.smartel.strike.dto.publication.PublishDTOWithNetworks;
import ru.smartel.strike.dto.service.network.Network;
import ru.smartel.strike.entity.Video;
import ru.smartel.strike.entity.interfaces.PostEntity;
import ru.smartel.strike.integration.PublicationGateway;
import ru.smartel.strike.service.url.UrlService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class PostPublicationService {
    public static final Logger log = LoggerFactory.getLogger(PostPublicationService.class);

    private final PublicationGateway publicationGateway;
    private final UrlService urlService;

    public PostPublicationService(PublicationGateway publicationGateway, UrlService urlService) {
        this.publicationGateway = publicationGateway;
        this.urlService = urlService;
    }

    public void publishAndSetFlags(PostEntity post, Set<Long> publishTo) {
        if (nonNull(post.getTitleRu())) {
            log.debug("Publishing post {} to networks {}", post, publishTo);
            var sitePageUrl = urlService.getUrl(post);
            publicationGateway.publish(new PublishDTOWithNetworks(
                    new PublishDTO(post.getContentRu(), post.getSourceLink(), sitePageUrl, post.getVideos().stream().map(Video::getUrl).collect(Collectors.toList())),
                    getNetworkIdsToSendTo(post, publishTo)));
            setSentToFlags(post, publishTo);
        }
    }

    /**
     * Set boolean sentTo*  flags according to given set. If flag is already true, doesnt change
     *
     * @param post   post
     * @param sentTo set of Network ids
     */
    private void setSentToFlags(PostEntity post, Set<Long> sentTo) {
        if (sentTo.contains(Network.TELEGRAM.getId())) {
            post.setSentToTelegram(true);
        }
        if (sentTo.contains(Network.VK.getId())) {
            post.setSentToVk(true);
        }
        if (sentTo.contains(Network.OK.getId())) {
            post.setSentToOk(true);
        }
    }

    /**
     * Get set of Network ids to which post was not sent yet but wanted to sent to
     *
     * @param post          post
     * @param desiredSendTo desired set of networks to send to
     * @return set of Network ids
     */
    private Set<Long> getNetworkIdsToSendTo(PostEntity post, Set<Long> desiredSendTo) {
        Set<Long> sendTo = new HashSet<>(desiredSendTo);
        Set<Long> sentTo = new HashSet<>();
        if (post.getSentToVk()) {
            sentTo.add(Network.VK.getId());
        }
        if (post.getSentToTelegram()) {
            sentTo.add(Network.TELEGRAM.getId());
        }
        if (post.getSentToOk()) {
            sentTo.add(Network.OK.getId());
        }
        sendTo.removeAll(sentTo);
        return sendTo;
    }
}
