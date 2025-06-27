package com.ute.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String tenNguoiDung;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String matKhau;
} 