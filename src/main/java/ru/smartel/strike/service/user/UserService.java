package ru.smartel.strike.service.user;

import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;

public interface UserService {
    UserDetailDTO get(long userId);
    UserDetailDTO update(UserUpdateRequestDTO dto);
}
