package ru.smartel.strike.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import ru.smartel.strike.configuration.properties.FirebaseProperties;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.security.filter.FirebaseTokenFilter;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final FirebaseProperties properties;
    private final FirebaseAuth firebaseAuth;

    public WebSecurityConf(UserRepository userRepository, ObjectMapper objectMapper, FirebaseProperties properties, @Nullable FirebaseAuth firebaseAuth) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .addFilterBefore(new FirebaseTokenFilter(firebaseAuth, userRepository, objectMapper, properties.isAuthStub()),
                        AnonymousAuthenticationFilter.class)
                .logout().disable()
                .requestCache().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
