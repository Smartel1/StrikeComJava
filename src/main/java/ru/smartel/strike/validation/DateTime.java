package ru.smartel.strike.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=DateTimeValidator.class)

public @interface DateTime {
    String message() default "должно соответствовать формату yyyy-MM-dd hh-mm-ss";
    Class[] groups() default {};
    Class[] payload() default {};
}
