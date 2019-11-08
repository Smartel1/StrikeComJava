package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.User;

public class UserDTO {

    private int id;
    private String name;
    private String email;
    private String imageUrl;

    public static UserDTO from(User user) {
        UserDTO instance = new UserDTO();
        instance.setId(user.getId());
        instance.setName(user.getName());
        instance.setEmail(user.getEmail());
        instance.setImageUrl(user.getImageUrl());
        return instance;
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
