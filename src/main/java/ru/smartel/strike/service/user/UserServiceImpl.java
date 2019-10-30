package ru.smartel.strike.service.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.etc.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDTOValidator validator;

    public UserServiceImpl(UserRepository userRepository, UserDTOValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public UserDetailDTO get(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        );

        return new UserDetailDTO(user);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or principal.getId() == #userId and null == #dto.roles")
    public UserDetailDTO update(int userId, UserUpdateRequestDTO dto) throws DTOValidationException {
        validator.validateUpdateDTO(dto);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        );

        Optional.ofNullable(dto.getFcm()).ifPresent(user::setFcm);
        Optional.ofNullable(dto.getRoles()).ifPresent(user::setRoles);


        return new UserDetailDTO(user);
    }
}
