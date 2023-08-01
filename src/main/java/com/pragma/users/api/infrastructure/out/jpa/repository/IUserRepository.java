package com.pragma.users.api.infrastructure.out.jpa.repository;

import com.pragma.users.api.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByHashedEmail(String hashedEmail);
    Optional<UserEntity> findByEmail(String email);
}
