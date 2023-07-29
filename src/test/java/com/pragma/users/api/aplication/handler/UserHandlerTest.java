package com.pragma.users.api.aplication.handler;

import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.application.handler.impl.UserHandler;
import com.pragma.users.api.application.mapper.IUserRequestMapper;
import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.Role;
import com.pragma.users.api.domain.model.UserModel;
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
class UserHandlerTest {

    @InjectMocks
    private UserHandler userHandler;

    @Mock
    private IUserServicePort servicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private Encrypt encrypt;

    @Captor
    ArgumentCaptor<UserModel> userModelCaptor;

    @Test
    void saveTest(){
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                "2000-01-25",
                "test@test.com",
                "123456");

        UserModel userModel = new UserModel(
                123456L,
                "testName",
                "testLastName",
                "123456789",
                "+3104721560",
                LocalDate.now(),
                "test@test.com",
                "123456",
                1L
        );
        when(userRequestMapper.dtoToUserModel(dto)).thenReturn(userModel);
        when(encrypt.encryptPassword(anyString())).thenReturn("hashedPassword");
        doNothing().when(servicePort).save(any(UserModel.class));

        userHandler.save(dto, Role.ROLE_CLIENT);

        verify(userRequestMapper).dtoToUserModel(dto);
        verify(encrypt).encryptPassword("123456");
        verify(servicePort).save(userModelCaptor.capture());
        Assertions.assertNotNull(userModelCaptor.getValue());
        UserModel userModelCaptured = userModelCaptor.getValue();
        Assertions.assertEquals(123456L, userModelCaptured.getId());
        Assertions.assertEquals("testName", userModelCaptured.getName());
        Assertions.assertEquals("testLastName", userModelCaptured.getLastName());
        Assertions.assertEquals("123456789", userModelCaptured.getDocument());
        Assertions.assertEquals("+3104721560", userModelCaptured.getCellphone());
        Assertions.assertNotNull(userModelCaptured.getBirthdate());
        Assertions.assertEquals("test@test.com", userModelCaptured.getEmail());
        Assertions.assertEquals("hashedPassword", userModelCaptured.getPassword());
        Assertions.assertEquals(4L, userModelCaptured.getIdRole());
    }
}