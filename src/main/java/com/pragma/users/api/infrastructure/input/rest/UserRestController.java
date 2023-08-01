package com.pragma.users.api.infrastructure.input.rest;

import com.pragma.users.api.application.dto.request.SignInDto;
import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.application.dto.response.ResponseMessageDto;
import com.pragma.users.api.application.handler.IUserHandler;
import com.pragma.users.api.domain.model.Role;
import com.pragma.users.api.infrastructure.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;
    private final IControllerSignIn controllerSignIn;

    @PostMapping(value = "/save/owner", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> saveOwner(@Valid @RequestBody UserRequestDto dto) {
        if (dto.getBirthdate() == null) {
            throw new BadRequestException("Birthdate required");
        }
        userHandler.save(dto, Role.ROLE_OWNER);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/save/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveClient(@Valid @RequestBody UserRequestDto dto) {
        userHandler.save(dto, Role.ROLE_CLIENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/save/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Void> saveEmployee(@Valid @RequestBody UserRequestDto dto) {
        userHandler.save(dto, Role.ROLE_EMPLOYEE);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageDto> signInUser(@Valid @RequestBody SignInDto dto) {
        String token = userHandler.signIn(dto);
        controllerSignIn.signIn(dto.getEmail());
        return new ResponseEntity<>(new ResponseMessageDto(token), HttpStatus.OK);
    }
}