package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.domain.Usuario;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import com.raizes_do_nordeste.api.security.AuthRequest;
import com.raizes_do_nordeste.api.security.AuthResponse;
import com.raizes_do_nordeste.api.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenhaHash().equals(request.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.gerarToken(usuario);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}