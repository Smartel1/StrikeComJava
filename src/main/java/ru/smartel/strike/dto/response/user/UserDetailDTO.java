package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDetailDTO {

    private int id;
    private String name;
    private String uuid;
    private String email;
    private String fcm;
    private List<String> roles;
    private String imageUrl;
    private List<Integer> favouriteEvents;
    private List<Integer> favouriteNews;

    public UserDetailDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        uuid = user.getUuid();
        fcm = user.getFcm();
        roles = user.getRolesAsList();
        imageUrl = user.getImageUrl();
        favouriteEvents = user.getFavouriteEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        favouriteNews = user.getFavouriteNews().stream()
                .map(News::getId)
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Integer> getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(List<Integer> favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public List<Integer> getFavouriteNews() {
        return favouriteNews;
    }

    public void setFavouriteNews(List<Integer> favouriteNews) {
        this.favouriteNews = favouriteNews;
    }
}
