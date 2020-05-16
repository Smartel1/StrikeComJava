package ru.smartel.strike.dto.service.sort.network;

public enum Network {
    TELEGRAM(1L, "telegram"),
    OK(2L, "odnoklassniki"),
    VK(3L, "vkontakte"),
    ;

    Network(Long id, String slug) {
        this.id = id;
        this.slug = slug;
    }

    private final Long id;
    private final String slug;

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }
}
