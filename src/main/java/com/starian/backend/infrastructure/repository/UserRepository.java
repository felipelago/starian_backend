package com.starian.backend.infrastructure.repository;

import com.starian.backend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByCpf(String cpf);
}
