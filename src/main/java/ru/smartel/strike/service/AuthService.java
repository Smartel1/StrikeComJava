package ru.smartel.strike.service;

import org.springframework.stereotype.Service;
import ru.smartel.strike.model.User;

@Service
public class AuthService {

    private User user;

    public void login(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
