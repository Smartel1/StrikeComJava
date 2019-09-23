package ru.smartel.strike.dto.response.user;

import lombok.Data;
import ru.smartel.strike.model.User;

@Data
public class UserDTO {

    public UserDTO (User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }

    private int id;
    private String name;
    private String email;
}
