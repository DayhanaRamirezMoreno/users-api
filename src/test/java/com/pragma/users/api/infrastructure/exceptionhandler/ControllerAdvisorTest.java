package com.pragma.users.api.infrastructure.exceptionhandler;

import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.application.handler.IUserHandler;
import com.pragma.users.api.domain.model.Role;
import com.pragma.users.api.infrastructure.exception.BadRequestException;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.exception.SignUpException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PseudoController.class)
@AutoConfigureMockMvc(addFilters = false) // TODO: Eliminar cuando se implemente spring security
// TODO: Crear test para la condición del binding result
class ControllerAdvisorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @Test
    void handleBadRequestExceptionTest() throws Exception {
        doThrow(new BadRequestException("Test")).when(userHandler).save(any(), any());

        mockMvc.perform(get("/test"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Test"));
    }

    @Test
    void handleRepositoryExceptionTest() throws Exception {
        doThrow(new RepositoryException("Test", new Exception())).when(userHandler).save(any(), any());

        mockMvc.perform(get("/test"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Test"));
    }

    @Test
    void handleValidationExceptionTest() throws Exception {
        doThrow(new ValidationException(new Exception("Test"))).when(userHandler).save(any(), any());

        mockMvc.perform(get("/test"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Test"));
    }

//    @Test
//    void handleExceptionTest() throws Exception {
//        doThrow(new RuntimeException("Test")).when(userHandler).save(any(), any());
//
//        mockMvc.perform(get("/test"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.message").value("Test"));
//    }

    @Test
    void handleSignUpExceptionTest() throws Exception {
        doThrow(new SignUpException("Test", new Exception())).when(userHandler).save(any(), any());

        mockMvc.perform(get("/test"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Test"));
    }
}

@RestController
class PseudoController {

    @Autowired
    private IUserHandler userHandler;

    @GetMapping("/test")
    void test() {
        UserRequestDto dto = new UserRequestDto(
                "testName",
                "testLastName",
                "123456789",
                "3104721560",
                "2000-01-25",
                "test@test.com",
                "123456");
        userHandler.save(dto, Role.ROLE_ADMIN);
    }
}