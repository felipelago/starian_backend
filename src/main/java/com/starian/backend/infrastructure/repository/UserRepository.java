package com.starian.backend.infrastructure.repository;

import com.starian.backend.application.repository.UserRepositoryPort;
import com.starian.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryPort {
}
