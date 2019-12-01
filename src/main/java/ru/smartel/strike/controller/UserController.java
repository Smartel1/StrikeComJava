package ru.smartel.strike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.user.UserService;

@RestController
@RequestMapping("/api/v2/{locale}/me")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isFullyAuthenticated()")
    public DetailWrapperDTO<UserDetailDTO> get(@AuthenticationPrincipal UserPrincipal user) {
        return new DetailWrapperDTO<>(userService.get(user.getId()));
    }

    @PutMapping
    @PreAuthorize("isFullyAuthenticated()")
    public DetailWrapperDTO<UserDetailDTO> update(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody UserUpdateRequestDTO dto) {
        dto.setUserId(user.getId());
        return new DetailWrapperDTO<>(userService.update(dto));
    }

    @DeleteMapping
    @PreAuthorize("isFullyAuthenticated()")
    public void delete(@AuthenticationPrincipal UserPrincipal user) {
       userService.delete(user.getId());
    }
}
