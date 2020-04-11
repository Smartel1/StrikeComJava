package ru.smartel.strike.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.dto.exception.ApiErrorDTO;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.security.FirebaseAuthenticationException;
import ru.smartel.strike.security.token.UserAuthenticationToken;
import ru.smartel.strike.security.token.UserPrincipal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FirebaseTokenFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(FirebaseTokenFilter.class);

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final boolean stub;

    public FirebaseTokenFilter(UserRepository userRepository, ObjectMapper objectMapper, boolean stub) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.stub = stub;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (stub) {
            authenticateAsModerator();
            chain.doFilter(request, response);
            return;
        }

        String bearer = ((HttpServletRequest)request).getHeader("Authorization");

        try {
            if (bearer != null) {
                logger.info("User uses bearer: " + bearer);
                if (!bearer.startsWith("Bearer ")) {
                    throw new BadCredentialsException("Некорректный заголовок Authorization (должен начинаться с 'bearer')");
                }
                authenticate(bearer.substring(7));
            }
        } catch (AuthenticationException ex) {
            //we should handle exceptions here
            handleException((HttpServletResponse) response, ex);
            return;
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String bearer) throws AuthenticationException {
        FirebaseToken token;

        try {
            token = FirebaseAuth.getInstance().verifyIdToken(bearer);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getMessage());
        }

        String uuid = (String)token.getClaims().get("sub");

        User user = userRepository.findFirstByUuid(uuid).orElse(null);
        if (null == user) {
            user = new User();
            try {
                updateUserFields(user, uuid);
            } catch (FirebaseAuthException e) {
                throw new FirebaseAuthenticationException(e.getMessage());
            }
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UserAuthenticationToken(UserPrincipal.from(user), getUserAuthorities(user), token, true)
        );
    }

    private void authenticateAsModerator() {
        User user = userRepository.findFirstByUuid("stub").orElseThrow(
                () -> new RuntimeException("Expected default moderator to exist"));
        SecurityContextHolder.getContext().setAuthentication(
                new UserAuthenticationToken(UserPrincipal.from(user), getUserAuthorities(user), null, true)
        );
    }

    private void updateUserFields(User user, String uuid) throws FirebaseAuthException {
        UserRecord userRecord;

        userRecord = FirebaseAuth.getInstance().getUser(uuid);

        user.setUuid(userRecord.getUid());
        user.setName(Optional.ofNullable(userRecord.getDisplayName()).orElse("unnamed user"));
        user.setEmail(userRecord.getEmail());
        user.setImageUrl(userRecord.getPhotoUrl());

        userRepository.save(user);
    }

    private Set<GrantedAuthority> getUserAuthorities(User user) {
        return user.getRolesAsList()
                .stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toSet());
    }

    /**
     * Write exception DTO to response
     */
    private void handleException(HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        String errorMessageJson = objectMapper.writeValueAsString(
                new ApiErrorDTO(
                        "Проблемы при аутентификации",
                        ex.getLocalizedMessage()));
        response.getWriter().write(errorMessageJson);
    }
}
