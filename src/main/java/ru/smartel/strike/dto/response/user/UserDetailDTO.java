package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDetailDTO {

    private long id;
    private String name;
    private String uuid;
    private String email;
    private String fcm;
    private List<String> roles;
    private String imageUrl;
    private List<Long> favouriteEvents;
    private List<Long> favouriteNews;

    public static UserDetailDTO from(User user) {
        UserDetailDTO instance = new UserDetailDTO();
        instance.setId(user.getId());
        instance.setName(user.getName());
        instance.setEmail(user.getEmail());
        instance.setUuid(user.getUuid());
        instance.setFcm(user.getFcm());
        instance.setRoles(user.getRolesAsList());
        instance.setImageUrl(user.getImageUrl());
        instance.setFavouriteEvents(user.getFavouriteEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toList()));
        instance.setFavouriteNews(user.getFavouriteNews().stream()
                .map(News::getId)
                .collect(Collectors.toList()));
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Long> getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(List<Long> favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public List<Long> getFavouriteNews() {
        return favouriteNews;
    }

    public void setFavouriteNews(List<Long> favouriteNews) {
        this.favouriteNews = favouriteNews;
    }
}
