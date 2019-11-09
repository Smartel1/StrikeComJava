package ru.smartel.strike.service.user;

import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;

public interface UserDTOValidator {
    void validateUpdateDTO(UserUpdateRequestDTO dto);
}
