package ru.smartel.strike.dto.response.user;

import ru.smartel.strike.entity.User;

public class UserDTO {

    public UserDTO (User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }

    private int id;
    private String name;
    private String email;

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
}
