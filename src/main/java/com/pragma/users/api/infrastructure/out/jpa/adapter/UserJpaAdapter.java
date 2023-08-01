package com.pragma.users.api.infrastructure.out.jpa.adapter;

import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.exception.SignInException;
import com.pragma.users.api.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.users.api.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.api.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.api.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void save(UserModel userModel) {
        try {
            UserEntity userEntity = userEntityMapper.toEntity(userModel);
            RoleEntity roleEntity = new RoleEntity(userModel.getIdRole());
            userEntity.setRole(roleEntity);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            String message = String.format("An error happened during while saving an user: %s", userModel.getId()) + " caused by: " + exception.getMessage();
            throw new RepositoryException(message, exception);
        }
    }

    @Override
    public UserModel getUserByEmail(String email) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            userEntity = userEntity.isEmpty() ? userRepository.findByHashedEmail(email) : userEntity;
            if (userEntity.isPresent()) {
                UserModel userModel = userEntityMapper.toUserModel(userEntity.get());
                userModel.setIdRole(userEntity.get().getRole().getId());
                return userModel;
            }
            throw new SignInException("Cannot find email", null);
        } catch (Exception exception) {
            String message = "An error happened while fetching user by email caused by: " + exception.getMessage();
            throw new RepositoryException(message, exception);
        }
    }
}