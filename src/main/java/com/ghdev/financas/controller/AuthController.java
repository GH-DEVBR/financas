package com.ghdev.financas.controller;

import com.ghdev.financas.dto.LoginDTO;
import com.ghdev.financas.dto.RegisterDTO;
import com.ghdev.financas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto) {
        return authService.cadastrar(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return authService.login(dto);
    }
}

