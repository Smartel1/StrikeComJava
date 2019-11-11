package ru.smartel.strike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.user.UserService;

@RestController
@RequestMapping("/api/v2/{locale}/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isFullyAuthenticated()")
    public UserDetailDTO get(@AuthenticationPrincipal User user) {
        return userService.get(user.getId());
    }

    @PutMapping("{id}")
    public UserDetailDTO update(@PathVariable("id") long id, @RequestBody UserUpdateRequestDTO dto) {
        dto.setUserId(id);
        return userService.update(dto);
    }
}
