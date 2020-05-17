package ru.smartel.strike.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.user.UserService;

@RestController
@RequestMapping("/api/v2/{locale}/me")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isFullyAuthenticated()")
    public DetailWrapperDTO<UserDetailDTO> get(@AuthenticationPrincipal UserPrincipal user) {
        return new DetailWrapperDTO<>(userService.get(user.getId()));
    }

    @ApiOperation("Обновление информации о пользователе (не только себя, модераторы могут менять других пользователей)")
    @PutMapping
    @PreAuthorize("isFullyAuthenticated()")
    public DetailWrapperDTO<UserDetailDTO> update(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody UserUpdateRequestDTO dto) {
        dto.setUserId(user.getId());
        return new DetailWrapperDTO<>(userService.updateOrCreate(dto));
    }

    @DeleteMapping
    @PreAuthorize("isFullyAuthenticated()")
    public void delete(@AuthenticationPrincipal UserPrincipal user) {
       userService.delete(user.getId());
    }
}
