package ru.smartel.strike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.user.UserService;

@RestController
@RequestMapping("/api/v1/{locale}/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isFullyAuthenticated()")
    public UserDetailDTO get (
            @PathVariable("locale") Locale locale,
            @AuthenticationPrincipal User user
    ) {
        return userService.get(user.getId());
    }

    @PutMapping(path = "/{id}", consumes = {"application/json"})
    public UserDetailDTO update (
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int id,
            @RequestBody UserUpdateRequestDTO dto
    ) throws DTOValidationException {
        return userService.update(id, dto);
    }
}
