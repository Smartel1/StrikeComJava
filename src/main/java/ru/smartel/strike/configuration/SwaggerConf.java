package ru.smartel.strike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class SwaggerConf {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                    .apis(RequestHandlerSelectors.basePackage("ru.smartel.strike.controller"))
                    .paths(PathSelectors.any())
                    .build()
                .genericModelSubstitutes(Optional.class) //substitutes Optional<String> with String
                .ignoredParameterTypes(AuthenticationPrincipal.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Thebestcom API Documentation",
                new BufferedReader(
                        new InputStreamReader(getClass().getClassLoader().getResourceAsStream("doc.html")))
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator())),
                "2.0",
                "",
                new Contact("Andrew Silutin", "", "javablackstack@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }
}
