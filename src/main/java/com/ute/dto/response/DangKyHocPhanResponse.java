package com.ute.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DangKyHocPhanResponse {
    private String maSinhVien;
    private String hoTenSinhVien;
    private String maLopHP;
    private String tenLopHP;
    private String maHocPhan;
    private String tenHocPhan;
    private Integer soTinChi;
    private String giangVien;
    private String phongHoc;
    private LocalDateTime thoiGianDangKy;
    private Boolean trangThai;
    private Integer ketQuaDangKy;
    private String ghiChu;
} 