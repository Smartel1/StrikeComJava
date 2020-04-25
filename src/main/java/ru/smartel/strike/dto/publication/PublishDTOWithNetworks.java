package ru.smartel.strike.dto.publication;

import java.util.Set;

public class PublishDTOWithNetworks {
    private PublishDTO data;
    private Set<Long> publishTo;

    public PublishDTOWithNetworks(PublishDTO data, Set<Long> publishTo) {
        this.data = data;
        this.publishTo = publishTo;
    }

    public PublishDTO getData() {
        return data;
    }

    public Set<Long> getPublishTo() {
        return publishTo;
    }
}
