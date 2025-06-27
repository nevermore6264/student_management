package com.ute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.DotDangKyResponse;
import com.ute.entity.DotDangKy;
import com.ute.repository.DotDangKyRepository;

@RestController
@RequestMapping("/api/dotdangky")
@CrossOrigin(origins = "*")
public class DotDangKyController {
    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @GetMapping("/hientai")
    public ApiResponse<DotDangKyResponse> getDotDangKyHienTai() {
        try {
            java.util.List<DotDangKy> dotDangKys = dotDangKyRepository.findCurrentDotDangKy();
            
            if (dotDangKys.isEmpty()) {
                return new ApiResponse<>(false, "Không có đợt đăng ký hiện tại", null);
            }
            
            DotDangKy dot = dotDangKys.get(0); // Lấy đợt đăng ký đầu tiên (mới nhất)
            
            // Chuyển đổi sang DTO để tránh infinite loop
            DotDangKyResponse response = new DotDangKyResponse();
            response.setMaDotDK(dot.getMaDotDK());
            response.setTenDotDK(dot.getTenDotDK());
            response.setNgayGioBatDau(dot.getNgayGioBatDau());
            response.setNgayGioKetThuc(dot.getNgayGioKetThuc());
            response.setThoiGian(dot.getThoiGian());
            response.setMoTa(dot.getMoTa());
            response.setTrangThai(dot.getTrangThai());
            
            // Xử lý thông tin khoa
            if (dot.getKhoa() != null) {
                response.setMaKhoa(dot.getKhoa().getMaKhoa());
                response.setTenKhoa(dot.getKhoa().getTenKhoa());
            }
            
            return new ApiResponse<>(true, "Lấy đợt đăng ký hiện tại thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy đợt đăng ký hiện tại: " + e.getMessage(), null);
        }
    }
} 