package com.pragma.users.api.infrastructure.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of(""));//TODO revisar si es necesrio organizar lo del a´pi getaway
                    corsConfiguration.setAllowedMethods(List.of("POST"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    return corsConfiguration;
                }).and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/save/client", "/api/v1/user/sing-in")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
