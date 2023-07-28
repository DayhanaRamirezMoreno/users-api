package com.pragma.users.api.application.mapper;

import com.pragma.users.api.application.dto.request.UserRequestDto;
import com.pragma.users.api.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    UserModel dtoToUserModel(UserRequestDto dto);
}
