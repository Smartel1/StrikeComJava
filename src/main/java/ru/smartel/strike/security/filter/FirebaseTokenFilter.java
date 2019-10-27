package ru.smartel.strike.security.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.security.token.UserAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class FirebaseTokenFilter implements Filter {

    private UserRepository userRepository;

    public FirebaseTokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = ((HttpServletRequest)request).getHeader("Authorization");

        if (bearer != null) {
            if (!bearer.startsWith("Bearer ")) {
                throw new BadCredentialsException("Некорректный заголовок Authorization (должен начинаться с \"bearer\")");
            }
            authenticate(bearer.substring(7));
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String bearer) throws AuthenticationException {
        User user  = userRepository.findFirstByUuid("yBjIs0BELQSsWvyPAHcrGZa2hJi2");
        SecurityContextHolder.getContext().setAuthentication(
                new UserAuthenticationToken(user, getUserAuthorities(user), null, true)
        );
//        FirebaseToken token;
//
//        try {
//            token = FirebaseAuth.getInstance().verifyIdToken(bearer);
//        } catch (FirebaseAuthException e) {
//            throw new FirebaseAuthenticationException(e.getMessage());
//        }
//
//        String uuid = (String)token.getClaims().get("sub");
//
//        User user = userRepository.findFirstByUuid(uuid);
//        if (null == user) {
//            user = new User();
//            try {
//                updateUserFields(user, uuid);
//            } catch (FirebaseAuthException e) {
//                throw new FirebaseAuthenticationException(e.getMessage());
//            }
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(
//                new UserAuthenticationToken(user, getUserAuthorities(user), token, true)
//        );
    }

    private void updateUserFields(User user, String uuid) throws FirebaseAuthException {
        UserRecord userRecord;

        userRecord = FirebaseAuth.getInstance().getUser(uuid);

        user.setUuid(userRecord.getUid());
        user.setName(userRecord.getDisplayName());
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
}
