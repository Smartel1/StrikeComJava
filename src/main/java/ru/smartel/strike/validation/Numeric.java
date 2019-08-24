package ru.smartel.strike.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=NumericValidator.class)

public @interface Numeric {
    String message() default "должно быть числовым";
    Class[] groups() default {};
    Class[] payload() default {};
}
