package com.starian.backend.infrastructure.repository;

import com.starian.backend.application.port.UserRepositoryPort;
import com.starian.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryPort {
}
