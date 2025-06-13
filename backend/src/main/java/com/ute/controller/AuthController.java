package com.ute.controller;

import com.ute.dto.request.LoginRequest;
import com.ute.dto.request.RegisterRequest;
import com.ute.dto.response.AuthResponse;
import com.ute.entity.NguoiDung;
import com.ute.entity.VaiTro;
import com.ute.security.JwtTokenProvider;
import com.ute.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMatKhau()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        NguoiDung nguoiDung = authService.findByEmail(loginRequest.getEmail());
        String[] vaiTros = nguoiDung.getVaiTros().stream()
                .map(VaiTro::getMaVaiTro)
                .toArray(String[]::new);

        return ResponseEntity.ok(new AuthResponse(
                jwt,
                nguoiDung.getMaNguoiDung(),
                nguoiDung.getTenNguoiDung(),
                nguoiDung.getEmail(),
                vaiTros
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        NguoiDung nguoiDung = authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }
} 
