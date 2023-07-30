package com.pragma.users.api.infrastructure.aws.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.infrastructure.exception.SignInException;
import com.pragma.users.api.infrastructure.exception.SignUpException;
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
class CognitoServiceTest {
    @InjectMocks
    private CognitoService cognitoService;

    @Mock
    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    private String clientId;

    @Captor
    private ArgumentCaptor<SignUpRequest> signUpRequestCaptor;

    @Captor
    private ArgumentCaptor<InitiateAuthRequest> initiateAuthRequestArgumentCaptor;

    @Test
    void saveTest() {
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
        when(awsCognitoIdentityProvider.signUp(any())).thenReturn(new SignUpResult());

        cognitoService.save(userModel);

        verify(awsCognitoIdentityProvider).signUp(signUpRequestCaptor.capture());
        Assertions.assertNotNull(signUpRequestCaptor.getValue());
        SignUpRequest signUpRequestCaptured = signUpRequestCaptor.getValue();
        Assertions.assertEquals("123456", signUpRequestCaptured.getPassword());
        Assertions.assertEquals("test@test.com", signUpRequestCaptured.getUsername());
        Assertions.assertEquals(clientId, signUpRequestCaptured.getClientId());
        Assertions.assertEquals("email", signUpRequestCaptured.getUserAttributes().get(0).getName());
        Assertions.assertEquals("test@test.com", signUpRequestCaptured.getUserAttributes().get(0).getValue());
    }

    @Test
    void saveFailedTest() {
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
        when(awsCognitoIdentityProvider.signUp(any())).thenThrow(new RuntimeException("test"));

        Assertions.assertThrows(SignUpException.class, () -> cognitoService.save(userModel));

        verify(awsCognitoIdentityProvider).signUp(signUpRequestCaptor.capture());
        Assertions.assertNotNull(signUpRequestCaptor.getValue());
    }

    @Test
    void signInTest(){
        SignInDto dto = new SignInDto("test@test.com", "123456");
        InitiateAuthResult initiateAuthResult = new InitiateAuthResult();
        AuthenticationResultType authenticationResultType = new AuthenticationResultType();
        authenticationResultType.setAccessToken("token");
        initiateAuthResult.setAuthenticationResult(authenticationResultType);

        initiateAuthResult.setAuthenticationResult(new AuthenticationResultType());

        when(awsCognitoIdentityProvider.initiateAuth(any())).thenReturn(initiateAuthResult);

        cognitoService.signIn(dto);

        verify(awsCognitoIdentityProvider).initiateAuth(initiateAuthRequestArgumentCaptor.capture());
        Assertions.assertNotNull(initiateAuthRequestArgumentCaptor.getValue());
        InitiateAuthRequest initiateAuthRequest = initiateAuthRequestArgumentCaptor.getValue();
        Assertions.assertTrue(initiateAuthRequest.getAuthParameters().containsValue("test@test.com"));
        Assertions.assertTrue(initiateAuthRequest.getAuthParameters().containsValue("123456"));

    }

    @Test
    void signInFailedTest(){
        SignInDto dto = new SignInDto("test@test.com", "123456");

        when(awsCognitoIdentityProvider.initiateAuth(any())).thenThrow(new RuntimeException("test"));

        Assertions.assertThrows(SignInException.class, () -> cognitoService.signIn(dto));
        verify(awsCognitoIdentityProvider).initiateAuth(initiateAuthRequestArgumentCaptor.capture());
        Assertions.assertNotNull(initiateAuthRequestArgumentCaptor.getValue());
    }
}