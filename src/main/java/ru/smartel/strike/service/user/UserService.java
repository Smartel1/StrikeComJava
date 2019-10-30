package ru.smartel.strike.service.user;

import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface UserService {
    UserDetailDTO get(int userId);
    UserDetailDTO update(int userId, UserUpdateRequestDTO dto) throws DTOValidationException;
}
