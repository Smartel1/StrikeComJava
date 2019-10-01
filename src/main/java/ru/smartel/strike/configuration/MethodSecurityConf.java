package ru.smartel.strike.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import ru.smartel.strike.security.authorization.CustomMethodSecurityExpressionHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConf extends GlobalMethodSecurityConfiguration {

    @Autowired
    CustomMethodSecurityExpressionHandler handler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return handler;
    }
}
