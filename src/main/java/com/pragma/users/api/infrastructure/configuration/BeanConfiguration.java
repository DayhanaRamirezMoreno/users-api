package com.pragma.users.api.infrastructure.configuration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.domain.usecase.UserUseCase;
import com.pragma.users.api.infrastructure.aws.cognito.CognitoService;
import com.pragma.users.api.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.users.api.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.api.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.users.api.infrastructure.validation.Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    private final IUserCognitoPersistencePort userCognitoPersistencePort;
    private final Encrypt encrypt;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserCognitoPersistencePort userCognitoPersistencePort() {
        return new CognitoService(awsCognitoIdentityProvider);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), userCognitoPersistencePort, encrypt);
    }
}