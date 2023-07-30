package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IUserCognitoPersistencePort userCognitoPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IUserCognitoPersistencePort userCognitoPersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.userCognitoPersistencePort = userCognitoPersistencePort;
    }

    @Override
    public void save(UserModel userModel) {
        userCognitoPersistencePort.save(userModel);
        userPersistencePort.save(userModel);
    }

    @Override
    public String SignInUser(SignInDto dto) {
        return userCognitoPersistencePort.signIn(dto);
    }
}
