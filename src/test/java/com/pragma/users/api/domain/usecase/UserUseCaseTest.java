package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.validation.Encrypt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IUserCognitoPersistencePort userCognitoPersistencePort;

    @Mock
    private Encrypt encrypt;

    @InjectMocks
    private UserUseCase userUseCase;

    @Captor
    private ArgumentCaptor<UserModel> userModelArgumentCaptor;

    @Test
    void saveUserTest() {
        UserModel userModel = new UserModel(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L,
                "email@email.com"
        );
        when(userCognitoPersistencePort.save(userModel)).thenReturn("email@email.com");
        doNothing().when(userPersistencePort).save(userModel);
        when(encrypt.encryptPassword(anyString())).thenReturn("hashedPassword");
        userUseCase.save(userModel);

        verify(userCognitoPersistencePort).save(userModel);
        verify(encrypt).encryptPassword(any());
        verify(userPersistencePort).save(userModelArgumentCaptor.capture());
        Assertions.assertEquals("hashedPassword", userModelArgumentCaptor.getValue().getPassword());
        Assertions.assertEquals("email@email.com", userModelArgumentCaptor.getValue().getHashedEmail());
    }

    @Test
    void signInUser() {
        SignInDto dto = new SignInDto("test@test.com", "123456");
        String token = "test";
        when(userCognitoPersistencePort.signIn(dto)).thenReturn(token);

        String newToken = userUseCase.signInUser(dto);

        verify(userCognitoPersistencePort).signIn(dto);
        Assertions.assertEquals("test", newToken);

    }

    @Test
    void getUserByEmail() {
        UserModel userModel = new UserModel(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L,
                "email@email.com"
        );
        when(userPersistencePort.getUserByEmail("email@email.com")).thenReturn(userModel);

        UserModel newUser = userUseCase.getUserByEmail("email@email.com");

        verify(userPersistencePort).getUserByEmail("email@email.com");
        Assertions.assertEquals(1L, newUser.getId());
        Assertions.assertEquals("anyName", newUser.getName());
        Assertions.assertEquals("anyLastName", newUser.getLastName());
        Assertions.assertEquals("123456789", newUser.getDocument());
        Assertions.assertEquals("+57123456789", newUser.getCellphone());
        Assertions.assertNotNull(newUser.getBirthdate());
        Assertions.assertEquals("email@email.com", newUser.getEmail());
        Assertions.assertEquals("123456", newUser.getPassword());
        Assertions.assertEquals(1L, newUser.getIdRole());
    }
}