package com.pragma.users.api.domain.spi;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;

public interface IUserCognitoPersistencePort {
    void save(UserModel userModel);
    String signIn(SignInDto dto);
}
