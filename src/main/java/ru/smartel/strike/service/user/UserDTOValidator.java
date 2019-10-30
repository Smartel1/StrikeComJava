package ru.smartel.strike.service.user;

import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface UserDTOValidator {
    void validateUpdateDTO(UserUpdateRequestDTO dto) throws DTOValidationException;
}
