package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.User;

public class AuthorDTO {

    private long id;
    private String name;
    private String email;
    private String imageUrl;

    public static AuthorDTO from(User user) {
        AuthorDTO instance = new AuthorDTO();
        instance.setId(user.getId());
        instance.setName(user.getName());
        instance.setEmail(user.getEmail());
        instance.setImageUrl(user.getImageUrl());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
