package com.pragma.users.api.infrastructure.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidationImpl.class)
@Documented
public @interface DateValidation {
    String message() default "Owner must be >= 18";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
