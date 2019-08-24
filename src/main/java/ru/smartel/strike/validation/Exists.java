package ru.smartel.strike.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ExistsValidator.class)

public @interface Exists {
    String message() default "должно быть в базе данных";
    Class[] groups() default {};
    Class[] payload() default {};
    Class entity();
    String field() default "id";
}
