package com.pragma.users.api.infrastructure.aws.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserCognitoPersistencePort;
import com.pragma.users.api.infrastructure.exception.SignInException;
import com.pragma.users.api.infrastructure.exception.SignUpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CognitoService implements IUserCognitoPersistencePort {

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Override
    public void save(UserModel userModel) {
        try {
            AttributeType attributeType = new AttributeType().withName("email").withValue(userModel.getEmail());
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withPassword(userModel.getPassword())
                    .withUsername(userModel.getEmail())
                    .withUserAttributes(attributeType);
            awsCognitoIdentityProvider.signUp(signUpRequest);
        } catch (RuntimeException e) {
            throw new SignUpException("Cognito sign up failed", e);
        }
    }

    @Override
    public String signIn(SignInDto dto) {
        try {
            final Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", dto.getEmail());
            authParams.put("PASSWORD", dto.getPassword());

            InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .withClientId(clientId)
                    .withAuthParameters(authParams);

            InitiateAuthResult initiateAuthResult = awsCognitoIdentityProvider.initiateAuth(initiateAuthRequest);
            return initiateAuthResult.getAuthenticationResult().getAccessToken();

        } catch (RuntimeException e) {
            throw new SignInException("Cognito sign in failed", e);
        }
    }
}