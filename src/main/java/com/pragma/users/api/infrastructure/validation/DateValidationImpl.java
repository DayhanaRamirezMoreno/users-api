package com.pragma.users.api.infrastructure.validation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Service
public class DateValidationImpl implements ConstraintValidator<DateValidation, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        LocalDate actualDate = LocalDate.now();
        LocalDate minusYears = actualDate.minusYears(18);
        LocalDate parsedDate = LocalDate.parse(date);

        return parsedDate.isBefore(minusYears);
    }
}
