package com.ute.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ApiResponse<List<DotDangKyResponse>> getAllDotDangKy() {
        try {
            List<DotDangKy> dotDangKys = dotDangKyRepository.findAll();
            
            List<DotDangKyResponse> responses = dotDangKys.stream()
                .map(dot -> {
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
                    
                    return response;
                })
                .collect(Collectors.toList());
            
            return new ApiResponse<>(true, "Lấy danh sách đợt đăng ký thành công", responses);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy danh sách đợt đăng ký: " + e.getMessage(), null);
        }
    }

    @GetMapping("/period")
    public ApiResponse<DotDangKyResponse> getDotDangKyByMaDotDK(@PathVariable("period") String maDotDK) {
        try {
            DotDangKy dot = dotDangKyRepository.findByMaDotDK(maDotDK);
            
            if (dot == null) {
                return new ApiResponse<>(false, "Không tìm thấy đợt đăng ký với mã: " + maDotDK, null);
            }
            
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
            
            return new ApiResponse<>(true, "Lấy đợt đăng ký thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy đợt đăng ký: " + e.getMessage(), null);
        }
    }
} 
