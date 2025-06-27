package com.ute.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DotDangKyResponse {
    private String maDotDK;
    private String tenDotDK;
    private LocalDateTime ngayGioBatDau;
    private LocalDateTime ngayGioKetThuc;
    private Integer thoiGian;
    private String maKhoa;
    private String tenKhoa;
    private String moTa;
    private Boolean trangThai;
} 