package ru.smartel.strike.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import ru.smartel.strike.security.filter.FirebaseTokenFilter;
import ru.smartel.strike.repository.UserRepository;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .addFilterBefore(new FirebaseTokenFilter(userRepository), AnonymousAuthenticationFilter.class)
                .logout().disable()
                .requestCache().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().disable();

    }
}
