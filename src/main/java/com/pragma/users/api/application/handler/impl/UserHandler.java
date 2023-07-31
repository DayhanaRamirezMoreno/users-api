package com.pragma.users.api.application.handler.impl;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.application.handler.IUserHandler;
import com.pragma.users.api.application.mapper.IUserRequestMapper;
import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.Role;
import com.pragma.users.api.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public void save(UserRequestDto dto, Role role) {
        UserModel userModel = userRequestMapper.dtoToUserModel(dto);
        userModel.setIdRole(role.getId());
        userServicePort.save(userModel);
    }

    @Override
    public String signIn(SignInDto dto) {
        return userServicePort.signInUser(dto);
    }
}
