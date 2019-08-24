package ru.smartel.strike.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.smartel.strike.exception.UnauthtenticatedException;
import ru.smartel.strike.model.User;
import ru.smartel.strike.repository.UserRepository;
import ru.smartel.strike.security.FirebaseTokenAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearer = request.getHeader("Authorization");

        if (bearer == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!bearer.startsWith("Bearer ")) {
            return;
            //throw new UnauthtenticatedException("Некорректный заголовок");
        }

        bearer = bearer.substring(7);

        FirebaseTokenAdapter firebaseTokenAdapter = new FirebaseTokenAdapter(bearer);

        SecurityContextHolder.getContext().setAuthentication(firebaseTokenAdapter);

        filterChain.doFilter(request, response);
    }
}
