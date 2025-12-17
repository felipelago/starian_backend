package com.starian.backend.presentation.controller;

import com.starian.backend.application.dto.request.UserRegisterRequest;
import com.starian.backend.application.dto.response.UserRegisterResponse;
import com.starian.backend.application.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
