package com.pragma.users.api.domain.usecase;

import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

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
        doNothing().when(userPersistencePort).save(userModel);

        userUseCase.save(userModel);

        verify(userPersistencePort).save(userModel);
    }
}
