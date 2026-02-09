package com.starian.backend.application.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
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
        logger.debug("Criando usuário com CPF: {}", request.cpf());

        if (userRepository.findByCpf(request.cpf()).isPresent()) {
            logger.warn("Tentativa de cadastro com CPF já existente: {}", request.cpf());
            throw new BusinessException(String.format("CPF já cadastrado: %s", request.cpf()));
        }

        UserEntity entity = objectMapper.convertValue(request, UserEntity.class);
        UserEntity salvo = userRepository.save(entity);
        
        logger.info("Usuário criado com sucesso. ID: {}", salvo.getId());
        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }

    public Page<UserListResponse> listarUsuarios(Pageable pageable) {
        logger.debug("Listando usuários - Página: {}, Tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<UserEntity> page = userRepository.findAll(pageable);

        return page.map(entity -> objectMapper.convertValue(entity, UserListResponse.class));
    }

    public UserListResponse listarUsuarioPorId(Long id) {
        logger.debug("Buscando usuário por ID: {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado com ID: {}", id);
                    return new BusinessException(String.format("Usuário não encontrado com id: %d", id));
                });
        return objectMapper.convertValue(userEntity, UserListResponse.class);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        logger.debug("Deletando usuário com ID: {}", id);
        if (!userRepository.existsById(id)) {
            logger.warn("Tentativa de deletar usuário inexistente. ID: {}", id);
            throw new BusinessException(String.format("Usuário não encontrado: %d", id));
        }
        userRepository.deleteById(id);
        logger.info("Usuário deletado com sucesso. ID: {}", id);
    }

    @Transactional
    public UserListResponse atualizarUsuario(Long id, UserRegisterRequest request) {
        logger.debug("Atualizando usuário com ID: {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado para atualização. ID: {}", id);
                    return new BusinessException(String.format("Usuário não encontrado com id: %d", id));
                });

        if (!userEntity.getCpf().equals(request.cpf()) && userRepository.findByCpf(request.cpf()).isPresent()) {
            logger.warn("Tentativa de atualizar para CPF já existente: {}", request.cpf());
            throw new BusinessException(String.format("CPF já cadastrado: %s", request.cpf()));
        }

        try {
            objectMapper.updateValue(userEntity, request);
        } catch (Exception e) {
            logger.error("Erro ao atualizar usuário ID: {}", id, e);
            throw new BusinessException(String.format("Erro ao atualizar usuário: %s", e.getMessage()));
        }

        UserEntity atualizado = userRepository.save(userEntity);
        logger.info("Usuário atualizado com sucesso. ID: {}", id);
        return objectMapper.convertValue(atualizado, UserListResponse.class);
    }

    @Transactional
    public UserRegisterResponse criarUsuarioCepAuto(UserRegisterCepAutoRequest request) {
        logger.debug("Criando usuário com busca automática de CEP: {}", request.cep());

        if (userRepository.findByCpf(request.cpf()).isPresent()) {
            logger.warn("Tentativa de cadastro com CPF já existente: {}", request.cpf());
            throw new BusinessException(String.format("CPF já cadastrado: %s", request.cpf()));
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
        
        logger.info("Usuário criado com sucesso usando CEP automático. ID: {}", salvo.getId());
        return objectMapper.convertValue(salvo, UserRegisterResponse.class);
    }
}