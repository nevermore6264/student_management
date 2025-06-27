package com.ute.dto.response;

import lombok.Data;

@Data
public class SinhVienTrongLopResponse {
    private String maSinhVien;
    private String hoTenSinhVien;
    private String email;
    private String soDienThoai;
    private String maLop;
    private String tenLop;
    private String maKhoa;
    private String tenKhoa;
    private Boolean gioiTinh;
    private String diaChi;
} 