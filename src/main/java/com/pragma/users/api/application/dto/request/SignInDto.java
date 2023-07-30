package com.pragma.users.api.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInDto {
    private String email;
    private String password;
}
