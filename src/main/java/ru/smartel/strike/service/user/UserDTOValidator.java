package ru.smartel.strike.service.user;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class UserDTOValidator {
    public void validateUpdateDTO(UserUpdateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getUserId()) {
            addErrorMessage("userId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
