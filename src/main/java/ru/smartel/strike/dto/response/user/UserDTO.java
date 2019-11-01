package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.User;

public class UserDTO {

    private int id;
    private String name;
    private String email;
    private String imageUrl;

    public UserDTO (User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        email = user.getEmail();
        imageUrl = user.getImageUrl();
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
