package ru.smartel.strike.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return null != value && value.matches("^[-+]?[0-9]*\\.?[0-9]+$");
    }
}
