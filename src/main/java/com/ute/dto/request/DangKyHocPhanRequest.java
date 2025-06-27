package com.ute.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DangKyHocPhanRequest {
    private String maSinhVien;
    private String maLopHP;
    private Integer maPhienDK;
    private LocalDateTime thoiGianDangKy;
    private Boolean trangThai;
    private Integer ketQuaDangKy;
} 