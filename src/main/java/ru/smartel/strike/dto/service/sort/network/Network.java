package ru.smartel.strike.dto.service.sort.network;

public enum Network {
    TELEGRAM(1L, "telegram"),
    INSTAGRAM(2L, "instagram"),
    OK(3L, "odnoklassniki"),
    VK(4L, "vkontakte"),
    TWITTER(5L, "twitter"),
    ;

    Network(Long id, String slug) {
        this.id = id;
        this.slug = slug;
    }

    private Long id;
    private String slug;

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }
}
