package com.ute.service.impl;

import com.ute.dto.request.ForgotPasswordRequest;
import com.ute.dto.request.RegisterRequest;
import com.ute.dto.request.ResetPasswordRequest;
import com.ute.entity.NguoiDung;
import com.ute.entity.VaiTro;
import com.ute.repository.NguoiDungRepository;
import com.ute.repository.VaiTroRepository;
import com.ute.service.AuthService;
import com.ute.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
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

    @Autowired
    private EmailService emailService;

    @Override
    public NguoiDung registerUser(RegisterRequest registerRequest) {
        if (nguoiDungRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung("SV" + UUID.randomUUID().toString().substring(0, 8));
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

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với email: " + request.getEmail()));

        // Tạo mã xác thực ngẫu nhiên 6 chữ số
        String resetCode = String.format("%06d", new Random().nextInt(1000000));
        
        // Lưu mã xác thực và thời gian hết hạn (15 phút)
        nguoiDung.setResetCode(resetCode);
        nguoiDung.setResetCodeExpiry(LocalDateTime.now().plusMinutes(15));
        nguoiDungRepository.save(nguoiDung);

        // Gửi email chứa mã xác thực
        String subject = "Yêu cầu đặt lại mật khẩu";
        String content = String.format(
            "Xin chào %s,\n\n" +
            "Bạn đã yêu cầu đặt lại mật khẩu. Mã xác thực của bạn là: %s\n\n" +
            "Mã này sẽ hết hạn sau 15 phút.\n\n" +
            "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n" +
            "Trân trọng,\n" +
            "Hệ thống",
            nguoiDung.getTenNguoiDung(),
            resetCode
        );
        
        emailService.sendEmail(nguoiDung.getEmail(), subject, content);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findByResetCode(request.getCode())
                .orElseThrow(() -> new RuntimeException("Mã xác thực không hợp lệ"));

        if (nguoiDung.getResetCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã xác thực đã hết hạn");
        }

        // Cập nhật mật khẩu mới
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getNewPassword()));
        nguoiDung.setResetCode(null);
        nguoiDung.setResetCodeExpiry(null);
        nguoiDungRepository.save(nguoiDung);
    }
} 
