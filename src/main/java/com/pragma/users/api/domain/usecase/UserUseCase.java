package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.validation.Encrypt;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IUserCognitoPersistencePort userCognitoPersistencePort;
    private final Encrypt encrypt;

    public UserUseCase(IUserPersistencePort userPersistencePort, IUserCognitoPersistencePort userCognitoPersistencePort, Encrypt encrypt) {
        this.userPersistencePort = userPersistencePort;
        this.userCognitoPersistencePort = userCognitoPersistencePort;
        this.encrypt = encrypt;
    }

    @Override
    public void save(UserModel userModel) {
        userCognitoPersistencePort.save(userModel);
        userModel.setPassword(encrypt.encryptPassword(userModel.getPassword()));
        userPersistencePort.save(userModel);
    }

    @Override
    public String signInUser(SignInDto dto) {
        return userCognitoPersistencePort.signIn(dto);
    }
}