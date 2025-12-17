package com.starian.backend.presentation.controller;

import com.starian.backend.application.dto.request.UserRegisterRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UserController {

    @PostMapping
    public void criarUsuario(@RequestBody UserRegisterRequest user) {

    }
}
