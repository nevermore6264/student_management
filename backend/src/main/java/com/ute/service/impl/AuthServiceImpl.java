package com.ute.service.impl;

import com.ute.dto.request.RegisterRequest;
import com.ute.entity.NguoiDung;
import com.ute.entity.VaiTro;
import com.ute.repository.NguoiDungRepository;
import com.ute.repository.VaiTroRepository;
import com.ute.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public NguoiDung registerUser(RegisterRequest registerRequest) {
        if (nguoiDungRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung("ND" + UUID.randomUUID().toString().substring(0, 8));
        nguoiDung.setTenNguoiDung(registerRequest.getTenNguoiDung());
        nguoiDung.setEmail(registerRequest.getEmail());
        nguoiDung.setSoDienThoai(registerRequest.getSoDienThoai());
        nguoiDung.setDiaChi(registerRequest.getDiaChi());
        nguoiDung.setMatKhau(passwordEncoder.encode(registerRequest.getMatKhau()));
        nguoiDung.setNgayTao(LocalDateTime.now());

        Set<VaiTro> vaiTros = new HashSet<>();
        VaiTro userRole = vaiTroRepository.findByMaVaiTro("SV")
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        vaiTros.add(userRole);
        nguoiDung.setVaiTros(vaiTros);

        return nguoiDungRepository.save(nguoiDung);
    }

    @Override
    public NguoiDung findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
} 