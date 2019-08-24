package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.smartel.strike.interceptor.BindInterceptor;

@Configuration
public class InterceptorsConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bindInterceptor()).addPathPatterns("/**");
    }

    @Bean
    BindInterceptor bindInterceptor() {
        return new BindInterceptor();
    }
}
