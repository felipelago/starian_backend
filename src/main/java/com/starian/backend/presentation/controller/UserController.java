package com.starian.backend.presentation.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserListResponse;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.application.service.UserService;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterResponse> criarUsuario(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.criarUsuario(request));
    }

    @GetMapping
    public ResponseEntity<List<UserListResponse>> listarTodos() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserListResponse> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.listarUsuarioPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserListResponse> atualizarUsuario(@PathVariable Long id, @RequestBody UserRegisterRequest request) throws JsonMappingException {
        return ResponseEntity.ok(userService.atualizarUsuario(id, request));
    }
}
