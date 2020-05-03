package ru.smartel.strike.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.smartel.strike.configuration.properties.FirebaseProperties;
import ru.smartel.strike.dto.exception.ApiErrorDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.security.FirebaseAuthenticationException;
import ru.smartel.strike.security.token.UserAuthenticationToken;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.firebase.FirebaseService;
import ru.smartel.strike.service.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseTokenFilter.class);

    private final ObjectMapper objectMapper;
    private final FirebaseProperties firebaseProperties;
    private final FirebaseService firebaseService;
    private final UserService userService;

    public FirebaseTokenFilter(ObjectMapper objectMapper,
                               FirebaseProperties firebaseProperties,
                               FirebaseService firebaseService,
                               UserService userService) {
        this.objectMapper = objectMapper;
        this.firebaseProperties = firebaseProperties;
        this.firebaseService = firebaseService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (firebaseProperties.isAuthStub()) {
            authenticateAsModerator();
            chain.doFilter(request, response);
            return;
        }

        if (firebaseService.firebaseAuthNotSet()) {
            writeErrorToResponse(response, "Не сконфигурирован firebase");
            return;
        }

        String bearer = (request).getHeader("Authorization");

        try {
            if (bearer != null) {
                logger.info("User uses bearer: " + bearer);
                if (!bearer.startsWith("Bearer ")) {
                    throw new BadCredentialsException("Некорректный заголовок Authorization (должен начинаться с 'bearer')");
                }
                authenticate(bearer.substring(7));
            }
        } catch (AuthenticationException ex) {
            writeErrorToResponse(response, ex.getLocalizedMessage());
            return;
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String bearer) throws AuthenticationException {
        FirebaseToken token;

        try {
            token = firebaseService.parseAndVerifyToken(bearer);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getMessage());
        }

        String uid = (String)token.getClaims().get("sub");

        User user = userService.get(uid).orElse(null);
        if (null == user) {
            user = new User();
            updateUserFields(uid);
        }

        LocalDateTime tokenIssuedAt = LocalDateTime.ofEpochSecond(
                Long.parseLong(token.getClaims().get("iat").toString()),
                0,
                ZoneOffset.ofHours(3));

        // If token was issued before user was updated last time, then update user fields (async)
        if (user.getUpdatedAt().compareTo(tokenIssuedAt) < 0) {
            CompletableFuture.runAsync(() -> updateUserFields(uid));
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UserAuthenticationToken(UserPrincipal.from(user), getUserAuthorities(user), token, true)
        );
    }

    private void authenticateAsModerator() {
        User user = userService.get("stub").orElseThrow(
                () -> new RuntimeException("Expected default moderator to exist"));
        SecurityContextHolder.getContext().setAuthentication(
                new UserAuthenticationToken(
                        UserPrincipal.from(user), getUserAuthorities(user), null, true));
    }

    private void updateUserFields(String uid) {
        try {
            firebaseService.updateUserFields(uid);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getMessage());
        }
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
    private void writeErrorToResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        String errorMessageJson = objectMapper.writeValueAsString(new ApiErrorDTO("Проблемы при аутентификации", message));
        response.getWriter().write(errorMessageJson);
    }
}
