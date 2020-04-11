package ru.smartel.strike.dto.service.sort.network;

public enum Networks {
    TELEGRAM(1, "telegram"),
    INSTAGRAM(2, "instagram"),
    OK(3, "odnoklassniki"),
    VK(4, "vkontakte"),
    TWITTER(5, "twitter"),
    ;

    Networks(long id, String slug) {
        this.id = id;
        this.slug = slug;
    }

    private long id;
    private String slug;

    public long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }
}
