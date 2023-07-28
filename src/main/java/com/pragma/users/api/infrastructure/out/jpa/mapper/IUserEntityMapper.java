package com.pragma.users.api.infrastructure.out.jpa.mapper;

import com.pragma.users.api.domain.model.UserModel;
import com.pragma.users.api.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel userModel);
    UserModel toUserModel(UserEntity userEntity);
    List<UserModel> toUserModelList(List<UserEntity> userEntityList);
}