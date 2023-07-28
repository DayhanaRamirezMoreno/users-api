package com.pragma.users.api.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pragma.users.api.infrastructure.validation.DateValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @Pattern(regexp = "^[0-9]+$", message = "Document should only have numbers")
    @NotBlank(message = "Document is required")
    private String document;

    @Size(min = 10, max = 13, message = "Number cannot exceed 13 characters")
    @Pattern(regexp = "^(?:\\+?[0-9]+)$", message = "Cellphone should only contain numbers and + sign at the beginning")
    @NotBlank(message = "Cellphone is required")
    private String cellphone;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateValidation
    private LocalDate birthdate;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


}
