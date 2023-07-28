package com.pragma.users.api.application.handler;

import com.pragma.users.api.application.dto.request.ObjectRequestDto;
import com.pragma.users.api.application.dto.response.ObjectResponseDto;

import java.util.List;

public interface IObjectHandler {

    void saveObject(ObjectRequestDto objectRequestDto);

    List<ObjectResponseDto> getAllObjects();
}