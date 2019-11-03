package ru.smartel.strike.service.user;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class UserDTOValidatorImpl implements UserDTOValidator {
    @Override
    public void validateUpdateDTO(UserUpdateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getUserId()) {
            addErrorMessage("user_id", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
