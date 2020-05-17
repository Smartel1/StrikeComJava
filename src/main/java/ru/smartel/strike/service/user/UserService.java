package ru.smartel.strike.service.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.user.UserUpdateRequestDTO;
import ru.smartel.strike.dto.response.user.UserDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.repository.etc.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ConcurrentModificationException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    // Key is uid, value is unused boolean
    private final ConcurrentHashMap<String, Boolean> registrationMonitor = new ConcurrentHashMap<>();

    private final UserRepository userRepository;
    private final UserDTOValidator validator;

    public UserService(UserRepository userRepository, UserDTOValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public UserDetailDTO get(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        );

        return UserDetailDTO.from(user);
    }

    public Optional<User> get(String uid) {
        return userRepository.findFirstByUid(uid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or principal.getId() == #dto.userId and null == #dto.roles")
    public UserDetailDTO updateOrCreate(UserUpdateRequestDTO dto) {
        validator.validateUpdateDTO(dto);

        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        );

        Optional.ofNullable(dto.getFcm()).ifPresent(user::setFcm);
        Optional.ofNullable(dto.getRoles()).ifPresent(user::setRoles);

        return UserDetailDTO.from(user);
    }

    /**
     * Register new user
     * This method is synchronized by uid
     *
     * @param uid      uid
     * @param name     name
     * @param email    email
     * @param imageUrl image url
     * @return user
     */
    public User register(String uid, String name, String email, String imageUrl) {
        if (registrationMonitor.containsKey(uid)) {
            throw new ConcurrentModificationException("Multiple registrations of same user!");
        }
        registrationMonitor.put(uid, true);
        var user = new User();
        user.setUid(uid);
        user.setName(name);
        user.setEmail(email);
        user.setImageUrl(imageUrl);
        userRepository.saveAndFlush(user);
        registrationMonitor.remove(uid);
        return user;
    }

    /**
     * Update user fields. If user not found then do nothing
     *
     * @param uid      uid
     * @param name     name
     * @param email    email
     * @param imageUrl image url
     */
    public void update(String uid, String name, String email, String imageUrl) {
        User user = userRepository.findFirstByUid(uid).orElse(null);
        if (isNull(user)) {
            return;
        }
        user.setName(name);
        user.setEmail(email);
        user.setImageUrl(imageUrl);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or principal.getId() == #userId")
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
