package com.pragma.users.api.infrastructure.out.jpa.adapter;

import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.exceptionhandler.ExceptionResponse;
import com.pragma.users.api.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.api.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.api.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final Logger logger = LoggerFactory.getLogger(UserJpaAdapter.class);

    @Override
    public void save(UserModel userModel) {
        try {
            UserEntity userEntity = userEntityMapper.toEntity(userModel);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            String message = String.format(ExceptionResponse.REPOSITORY_EXCEPTION.getMessage(), userModel.getId(), "dddddd");
            logger.error(message);
            throw new RepositoryException(message, exception);
        }
    }
}
