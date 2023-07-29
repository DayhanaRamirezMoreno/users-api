package com.pragma.users.api.infrastructure.aws.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.domain.spi.IUserPersistencePort;
import com.pragma.users.api.infrastructure.exception.SignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CognitoService implements IUserPersistencePort {

    @Autowired
    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Override
    public void save(UserModel userModel) {
        try{
            AttributeType attributeType = new AttributeType().withName("email").withValue(userModel.getEmail());
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withPassword(userModel.getPassword())
                    .withUsername(userModel.getEmail())
                    .withUserAttributes(attributeType);
            awsCognitoIdentityProvider.signUp(signUpRequest);
        } catch(Exception e){
            throw new SignUpException("Cognito sign up failed", e);
        }
    }
}