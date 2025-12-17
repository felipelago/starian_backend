package com.starian.backend.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.application.repository.UserRepositoryPort;
import com.starian.backend.domain.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositoryPort userRepositoryPort;
    private final ObjectMapper objectMapper;

    public UserService(UserRepositoryPort userRepositoryPort, ObjectMapper objectMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.objectMapper = objectMapper;
    }

    public UserRegisterResponse criarUsuario(UserRegisterRequest request) {

        UserEntity entity = objectMapper.convertValue(request, UserEntity.class);
        UserEntity salvo = userRepositoryPort.save(entity);

        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }
}
