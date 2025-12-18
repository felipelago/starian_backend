package com.starian.backend.presentation.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserListResponse;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/v1/usuarios",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Validated
@Tag(name = "Usuários", description = "Operações relacionadas ao gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<UserRegisterResponse> criarUsuario(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.criarUsuario(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna a lista de todos os usuários cadastrados")
    public ResponseEntity<List<UserListResponse>> listarTodos() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID")
    public ResponseEntity<UserListResponse> listarPorId(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.listarUsuarioPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza parcialmente os dados de um usuário")
    public ResponseEntity<UserListResponse> atualizarUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @RequestBody UserRegisterRequest request) throws JsonMappingException {
        return ResponseEntity.ok(userService.atualizarUsuario(id, request));
    }
}
