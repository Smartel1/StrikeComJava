package ru.smartel.strike.configuration;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;


public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.addFilterBefore(TokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
