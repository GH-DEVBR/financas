package com.ghdev.financas.service;

import com.ghdev.financas.dto.LoginDTO;
import com.ghdev.financas.dto.RegisterDTO;
import com.ghdev.financas.model.Usuario;
import com.ghdev.financas.repository.UsuarioRepository;
import com.ghdev.financas.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final JwtUtil jwtUtil;

    public String cadastrar(RegisterDTO dto) {
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario user = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(new BCryptPasswordEncoder().encode(dto.getSenha()))
                .build();

        usuarioRepo.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(LoginDTO dto) {
        Usuario user = usuarioRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!new BCryptPasswordEncoder().matches(dto.getSenha(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}

