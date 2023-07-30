package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.aws.cognito.CognitoService;
import com.pragma.users.api.infrastructure.out.jpa.adapter.UserJpaAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {


    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IUserCognitoPersistencePort userCognitoPersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp(){
        userUseCase = new UserUseCase(userPersistencePort, userCognitoPersistencePort);
    }

    @Test
    void saveUserTest(){
        UserModel userModel = new UserModel(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L
        );
        doNothing().when(userCognitoPersistencePort).save(userModel);
        doNothing().when(userPersistencePort).save(userModel);

        userUseCase.save(userModel);

        verify(userCognitoPersistencePort).save(userModel);
        verify(userPersistencePort).save(userModel);
    }

    @Test
    void signInUser(){
        SignInDto dto = new SignInDto("test@test.com", "123456");
        String token = "test";
        when(userCognitoPersistencePort.signIn(dto)).thenReturn(token);

        String newToken = userUseCase.SignInUser(dto);

        verify(userCognitoPersistencePort).signIn(dto);
        Assertions.assertEquals("test", newToken);

    }
}
