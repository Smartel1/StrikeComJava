package ru.smartel.strike.rules;

import ru.smartel.strike.model.User;

import java.util.List;

public class UserCanModerate extends BusinessRule {
    private User user;

    public UserCanModerate(User user) {
        this.user = user;
    }

    @Override
    public boolean passes() {
        List<String> roles = user.getRolesAsList();
        return roles.contains(User.ROLE_ADMIN) || roles.contains(User.ROLE_MODERATOR);
    }

    @Override
    public String message() {
        return "Пользователь должен обладать правами модератора";
    }
}
