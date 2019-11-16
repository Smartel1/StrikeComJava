package ru.smartel.strike.security.token;

import ru.smartel.strike.entity.User;

import java.util.List;

public class UserPrincipal {
    private long id;
    private String name;
    private List<String> roles;
    //private cuz we are using static factory method
    private UserPrincipal() {
    }

    public static UserPrincipal from(User user) {
        UserPrincipal instance = new UserPrincipal();
        instance.setId(user.getId());
        instance.setName(user.getName());
        instance.setRoles(user.getRolesAsList());
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
