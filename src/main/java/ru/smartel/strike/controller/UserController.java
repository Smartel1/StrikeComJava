package ru.smartel.strike.controller;

import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.exception.UnauthtenticatedException;
import ru.smartel.strike.model.User;
import ru.smartel.strike.repository.UserRepository;
import ru.smartel.strike.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/{locale}/user")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @GetMapping()
    public User whoAmI(HttpServletRequest request,
                               @PathVariable("locale") String locale, @RequestParam(name = "per_page", required = false, defaultValue = "20") Integer perPage) throws Exception {
        User user = authService.getUser();

        if (null == user) throw new UnauthtenticatedException();

        return user;
    }
}
