package ru.smartel.strike.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.security.filter.FirebaseTokenFilter;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .addFilterBefore(new FirebaseTokenFilter(userRepository, objectMapper), AnonymousAuthenticationFilter.class)
                .logout().disable()
                .requestCache().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
