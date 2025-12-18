package com.starian.backend.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserListResponse;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.domain.entity.UserEntity;
import com.starian.backend.domain.exception.BusinessException;
import com.starian.backend.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public UserRegisterResponse criarUsuario(UserRegisterRequest request) {

        UserEntity entity = objectMapper.convertValue(request, UserEntity.class);
        UserEntity salvo = userRepository.save(entity);

        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }

    public List<UserListResponse> listarUsuarios() {
        List<UserEntity> listUserEntity = userRepository.findAll();

        return listUserEntity.stream().map(item -> objectMapper.convertValue(item, UserListResponse.class)).toList();
    }

    public UserListResponse listarUsuarioPorId(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));
        return objectMapper.convertValue(userEntity, UserListResponse.class);
    }
}