package com.pragma.users.api.infrastructure.validation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Service
public class DateValidationImpl implements ConstraintValidator<DateValidation, LocalDate> {
    @Override
    public void initialize(DateValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        LocalDate actualDate = LocalDate.now();
        LocalDate minusYears = actualDate.minusYears(18);

        return date.isBefore(minusYears);
    }
}
