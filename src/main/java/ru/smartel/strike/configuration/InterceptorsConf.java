package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.smartel.strike.interceptor.LocaleInterceptor;
import ru.smartel.strike.interceptor.TokenAuthInterceptor;

@Configuration
public class InterceptorsConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(tokenAuthInterceptor()).addPathPatterns("/api/**");
    }

    @Bean
    TokenAuthInterceptor tokenAuthInterceptor() {
        return new TokenAuthInterceptor();
    }
}
