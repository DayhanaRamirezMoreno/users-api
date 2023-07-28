package com.pragma.users.api.domain.spi;

import com.pragma.users.api.domain.model.UserModel;

public interface IUserPersistencePort {
    void save(UserModel userModel);

}
