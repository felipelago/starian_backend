package com.starian.backend.application.repository;

import com.starian.backend.domain.entity.UserEntity;

public interface UserRepositoryPort {
    UserEntity save(UserEntity user);
}
