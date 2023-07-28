package com.pragma.users.api.infrastructure.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

@ExtendWith(MockitoExtension.class)
class EncryptImplTest {
    @InjectMocks
    EncryptImpl encrypt;

    @Test
    void encryptPasswordTest() {
        String password = "123456";
        String hashedPassword = encrypt.encryptPassword(password);

        Assertions.assertNotNull(hashedPassword);
        Assertions.assertNotEquals(password, hashedPassword);
        Assertions.assertTrue(BCrypt.checkpw(password, hashedPassword));
    }
}
