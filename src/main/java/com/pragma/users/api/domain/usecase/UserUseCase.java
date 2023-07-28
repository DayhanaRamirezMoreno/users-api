package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void save(UserModel userModel) {
        userPersistencePort.save(userModel);
    }
}
