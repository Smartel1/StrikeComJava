package ru.smartel.strike.DTO;

public class UserDTO {
    private int id;
    private String uuid;
    private String name;
    private String fcm;
    private String imageUrl;
    private String email;
    private String[] roles;
    private int[] favouriteEvents;
    private int[] favouriteNews;

    public UserDTO(int id, String uuid, String name, String fcm, String imageUrl, String email, String[] roles, int[] favouriteEvents, int[] favouriteNews) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.fcm = fcm;
        this.imageUrl = imageUrl;
        this.email = email;
        this.roles = roles;
        this.favouriteEvents = favouriteEvents;
        this.favouriteNews = favouriteNews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public int[] getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(int[] favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public int[] getFavouriteNews() {
        return favouriteNews;
    }

    public void setFavouriteNews(int[] favouriteNews) {
        this.favouriteNews = favouriteNews;
    }
}
