package ru.smartel.strike.service.user;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Service
public class UserDTOValidatorImpl implements UserDTOValidator {
    @Override
    public void validateUpdateDTO(UserUpdateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getUserId()) {
            addErrorMessage("userId", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
