package com.pragma.users.api.application.handler;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.domain.model.Role;

public interface IUserHandler {
    void save(UserRequestDto dto, Role role);
    String signIn(SignInDto dto);
}
