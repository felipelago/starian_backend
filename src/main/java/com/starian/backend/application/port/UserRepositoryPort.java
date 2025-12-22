package com.starian.backend.application.port;

import com.starian.backend.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserEntity> findByCpf(String cpf);

    Optional<UserEntity> findById(Long id);

    UserEntity save(UserEntity entity);

    Page<UserEntity> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);
}
