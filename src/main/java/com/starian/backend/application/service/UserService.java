package com.starian.backend.application.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starian.backend.application.dto.request.UserRegisterCepAutoRequest;
import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserListResponse;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.application.dto.response.ViaCepResponse;
import com.starian.backend.application.port.UserRepositoryPort;
import com.starian.backend.domain.entity.UserEntity;
import com.starian.backend.domain.exception.BusinessException;
import com.starian.backend.infrastructure.client.viacep.ViaCepAdapter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositoryPort userRepository;
    private final ObjectMapper objectMapper;
    private final ViaCepAdapter viaCepAdapter;

    public UserService(UserRepositoryPort userRepository, ObjectMapper objectMapper, ViaCepAdapter viaCepAdapter) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.viaCepAdapter = viaCepAdapter;
    }

    @Transactional
    public UserRegisterResponse criarUsuario(UserRegisterRequest request) {

        if (userRepository.findByCpf(request.cpf()).isPresent()) {
            throw new BusinessException("CPF Já cadastrado: " + request.cpf());
        }

        UserEntity entity = objectMapper.convertValue(request, UserEntity.class);
        UserEntity salvo = userRepository.save(entity);

        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }

    public Page<UserListResponse> listarUsuarios(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);

        return page.map(entity -> objectMapper.convertValue(entity, UserListResponse.class));
    }

    public UserListResponse listarUsuarioPorId(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));
        return objectMapper.convertValue(userEntity, UserListResponse.class);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!userRepository.existsById(id))
            throw new BusinessException("Usuário não encontrado: " + id);
        userRepository.deleteById(id);
    }

    @Transactional
    public UserListResponse atualizarUsuario(Long id, UserRegisterRequest request) throws JsonMappingException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com id: " + id));

        if (!userEntity.getCpf().equals(request.cpf()) && userRepository.findByCpf(request.cpf()).isPresent()) {
            throw new BusinessException("CPF Já cadastrado: " + request.cpf());
        }

        objectMapper.updateValue(userEntity, request);

        UserEntity atualizado = userRepository.save(userEntity);
        return objectMapper.convertValue(atualizado, UserListResponse.class);
    }

    @Transactional
    public UserRegisterResponse criarUsuarioCepAuto(UserRegisterCepAutoRequest request) {

        if (userRepository.findByCpf(request.cpf()).isPresent()) {
            throw new BusinessException("CPF Já cadastrado: " + request.cpf());
        }

        ViaCepResponse viaCepResponse = viaCepAdapter.findByCep(request.cep());

        UserEntity entity = new UserEntity();
        entity.setNome(request.nome());
        entity.setCpf(request.cpf());
        entity.setCep(request.cep());
        entity.setLogradouro(viaCepResponse.logradouro());
        entity.setBairro(viaCepResponse.bairro());
        entity.setCidade(viaCepResponse.cidade());
        entity.setEstado(viaCepResponse.estado());

        UserEntity salvo = userRepository.save(entity);

        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }
}