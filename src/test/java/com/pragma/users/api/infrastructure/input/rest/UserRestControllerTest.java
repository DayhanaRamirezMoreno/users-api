package com.pragma.users.api.infrastructure.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.application.handler.IUserHandler;
import com.pragma.users.api.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @MockBean
    private IControllerSignIn controllerSignIn;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveOwnerTest() throws Exception {
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                "2000-01-25",
                "test@test.com",
                "123456");
        doNothing().when(userHandler).save(dto, Role.ROLE_OWNER);

        mockMvc.perform(post("/api/v1/user/save/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        verify(userHandler).save(any(), any());
    }

    @Test
    void saveOwnerFailsWhenBirthdateIsNull() throws Exception {
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                null,
                "test@test.com",
                "123456");

        mockMvc.perform(post("/api/v1/user/save/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, times(0)).save(any(), any());
    }

    @Test
    void saveClientTest() throws Exception {
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                null,
                "test@test.com",
                "123456");
        doNothing().when(userHandler).save(dto, Role.ROLE_CLIENT);

        mockMvc.perform(post("/api/v1/user/save/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        verify(userHandler).save(any(), any());
    }

    @Test
    void saveEmployeeTest() throws Exception {
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                null,
                "test@test.com",
                "123456");
        doNothing().when(userHandler).save(dto, Role.ROLE_EMPLOYEE);

        mockMvc.perform(post("/api/v1/user/save/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        verify(userHandler).save(any(), any());
    }

    @Test
    void signInUserTest() throws Exception {
        SignInDto dto = new SignInDto("test@test.com", "123456");
        when(userHandler.signIn(dto)).thenReturn("token");
        doNothing().when(controllerSignIn).signIn(any());
        mockMvc.perform(post("/api/v1/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());


        verify(userHandler).signIn(any());
        verify(controllerSignIn).signIn(any());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}