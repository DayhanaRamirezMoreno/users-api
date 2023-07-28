package com.pragma.users.api.domain.api;

import com.pragma.users.api.domain.model.UserModel;

public interface IUserServicePort {
    void save(UserModel userModel);
}
