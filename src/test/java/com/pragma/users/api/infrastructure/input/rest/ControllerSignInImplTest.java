package com.pragma.users.api.infrastructure.input.rest;

import com.pragma.users.api.infrastructure.configuration.security.UserDetailsServiceImpl;
import com.pragma.users.api.infrastructure.configuration.security.entity.UserMain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerSignInImplTest {
    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private ControllerSignInImpl controllerSignIn;

    @Test
    void signInTest() {

        UserMain userMain = new UserMain("test@test.com", List.of(new SimpleGrantedAuthority("any")));
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userMain);

        controllerSignIn.signIn("test@test.com");

        verify(userDetailsService).loadUserByUsername("test@test.com");
    }
}