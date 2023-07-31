package com.pragma.users.api.domain.api;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;

public interface IUserServicePort {
    void save(UserModel userModel);

    String signInUser(SignInDto dto);

    UserModel getUserByEmail(String email);
}