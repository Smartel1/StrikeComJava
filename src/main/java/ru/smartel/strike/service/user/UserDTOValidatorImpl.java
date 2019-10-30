package ru.smartel.strike.service.user;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

@Service
public class UserDTOValidatorImpl implements UserDTOValidator {
    @Override
    public void validateUpdateDTO(UserUpdateRequestDTO dto) throws DTOValidationException {
        //nothing to validate atm
    }
}
