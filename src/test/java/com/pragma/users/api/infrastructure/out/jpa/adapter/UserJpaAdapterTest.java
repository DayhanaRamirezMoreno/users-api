package com.pragma.users.api.infrastructure.out.jpa.adapter;

import com.pragma.users.api.domain.model.Role;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.exception.SignInException;
import com.pragma.users.api.infrastructure.out.jpa.entity.RoleEntity;
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
import java.util.Optional;

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
        RoleEntity roleEntity = new RoleEntity(1L, Role.ROLE_ADMIN, "testDescription");
        UserEntity userEntity = new UserEntity(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                roleEntity,
                "email@email.com"
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
                1L,
                "email@email.com"
        );
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);

        userJpaAdapter.save(userModel);

        verify(userEntityMapper).toEntity(userModel);
        verify(userRepository).save(userEntity);
    }

    @Test
    void throwRepositoryExceptionWhenSaveFailsTest() {
        RoleEntity roleEntity = new RoleEntity(1L, Role.ROLE_ADMIN, "testDescription");
        UserEntity userEntity = new UserEntity(
                123L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                roleEntity,
                "email@email.com"
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
                1L,
                "email@email.com"
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
                1L,
                "email@email.com"
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

    @Test
    void getUserByEmailTest() {
        RoleEntity roleEntity = new RoleEntity(1L, Role.ROLE_ADMIN, "testDescription");
        UserEntity userEntity = new UserEntity(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                roleEntity,
                "email@email.com"
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
                1L,
                "email@email.com"
        );
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);

        userJpaAdapter.getUserByEmail("email@email.com");

        verify(userRepository).findByEmail("email@email.com");
        verify(userRepository, times(0)).findByHashedEmail("email@email.com");
        verify(userEntityMapper).toUserModel(userEntity);
    }

    @Test
    void throwSignInExceptionWhenGetUserByEmailDoesNotReturnUserTest() {
        when(userRepository.findByHashedEmail("email@email.com")).thenReturn(Optional.empty());

        RepositoryException exception = Assertions.assertThrows(RepositoryException.class, () -> {
            userJpaAdapter.getUserByEmail("email@email.com");
        });

        verify(userRepository).findByHashedEmail("email@email.com");
        verify(userEntityMapper, times(0)).toUserModel(any());
        Assertions.assertEquals("An error happened while fetching user by email caused by: Cannot find email", exception.getMessage());
        Assertions.assertTrue(exception.getCause() instanceof SignInException);
    }

    @Test
    void throwRepositoryExceptionWhenGetUserByEmailTest() {
        when(userRepository.findByHashedEmail("email@email.com")).thenThrow(new RuntimeException("test", new Exception()));

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            userJpaAdapter.getUserByEmail("email@email.com");
        });

        Assertions.assertEquals("An error happened while fetching user by email caused by: test", exception.getMessage());
        Assertions.assertTrue(exception.getCause() instanceof RuntimeException);
    }

    @Test
    void getUserByHashedEmailTest() {
        RoleEntity roleEntity = new RoleEntity(1L, Role.ROLE_ADMIN, "testDescription");
        UserEntity userEntity = new UserEntity(
                1L,
                "anyName",
                "anyLastName",
                "123456789",
                "+57123456789",
                LocalDate.now(),
                "email@email.com",
                "123456",
                roleEntity,
                "email@email.com"
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
                1L,
                "email@email.com"
        );
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.empty());
        when(userRepository.findByHashedEmail("email@email.com")).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);

        userJpaAdapter.getUserByEmail("email@email.com");

        verify(userRepository).findByEmail("email@email.com");
        verify(userRepository).findByHashedEmail("email@email.com");
        verify(userEntityMapper).toUserModel(userEntity);
    }

    @Test
    void throwSignInExceptionWhenGetUserByHashedEmailDoesNotReturnUserTest() {
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.empty());
        when(userRepository.findByHashedEmail("email@email.com")).thenReturn(Optional.empty());

        RepositoryException exception = Assertions.assertThrows(RepositoryException.class, () -> {
            userJpaAdapter.getUserByEmail("email@email.com");
        });

        verify(userRepository).findByEmail("email@email.com");
        verify(userRepository).findByHashedEmail("email@email.com");
        verify(userEntityMapper, times(0)).toUserModel(any());
        Assertions.assertEquals("An error happened while fetching user by email caused by: Cannot find email", exception.getMessage());
        Assertions.assertTrue(exception.getCause() instanceof SignInException);
    }
}
