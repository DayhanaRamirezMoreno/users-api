package com.pragma.users.api.infrastructure.out.jpa.adapter;

import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.api.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.api.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Test
    void saveUserTest() {
        UserEntity userEntity = new UserEntity(
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
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);

        userJpaAdapter.save(userModel);

        verify(userEntityMapper).toEntity(userModel);
        verify(userRepository).save(userEntity);
    }

    @Test
    void throwRepositoryExceptionWhenSaveFailsTest() {
        UserEntity userEntity = new UserEntity(
                123L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L
        );
        UserModel userModel = new UserModel(
                123L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L
        );
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenThrow(new NullPointerException("test"));

        RepositoryException exception = Assertions.assertThrows(RepositoryException.class, () -> {
            userJpaAdapter.save(userModel);
        });

        verify(userEntityMapper).toEntity(userModel);
        verify(userRepository).save(userEntity);
        Assertions.assertEquals("An error happened during while saving an user: 123 caused by: test", exception.getMessage());
        Assertions.assertTrue(exception.getCause() instanceof NullPointerException);
    }

    @Test
    void throwRepositoryExceptionWhenUserMapperFailsTest() {
        UserModel userModel = new UserModel(
                123L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                1L
        );
        when(userEntityMapper.toEntity(userModel)).thenThrow(new NullPointerException("test"));

        RepositoryException exception = Assertions.assertThrows(RepositoryException.class, () -> {
            userJpaAdapter.save(userModel);
        });

        verify(userEntityMapper).toEntity(userModel);
        verify(userRepository, times(0)).save(any());
        Assertions.assertEquals("An error happened during while saving an user: 123 caused by: test", exception.getMessage());
        Assertions.assertTrue(exception.getCause() instanceof NullPointerException);
    }
}
