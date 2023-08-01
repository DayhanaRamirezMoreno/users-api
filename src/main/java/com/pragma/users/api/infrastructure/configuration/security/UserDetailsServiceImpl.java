package com.pragma.users.api.infrastructure.configuration.security;

import com.pragma.users.api.domain.api.IUserServicePort;
import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.infrastructure.configuration.security.entity.UserMain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserServicePort userServicePort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userServicePort.getUserByEmail(email);
        return UserMain.build(userModel);
    }
}
