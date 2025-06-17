package com.ute.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LopHocPhanResponse {
    private String maLopHP;
    private String tenLopHP;
    private Integer soLuong;
    private String giangVien;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private String phongHoc;
    private Boolean trangThai;
    private String maHocPhan;
    private String tenHocPhan;
} 