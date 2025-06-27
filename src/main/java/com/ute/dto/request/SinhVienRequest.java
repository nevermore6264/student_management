package com.ute.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SinhVienRequest {
    private String maSinhVien;
    private String hoTenSinhVien;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private LocalDateTime ngaySinh;
    private Boolean gioiTinh;
    private String maLop;
} 