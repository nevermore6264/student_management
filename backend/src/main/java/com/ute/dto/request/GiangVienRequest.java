package com.ute.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GiangVienRequest {
    @NotBlank(message = "Mã giảng viên không được để trống")
    private String maGiangVien;

    @NotBlank(message = "Tên giảng viên không được để trống")
    private String tenGiangVien;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String soDienThoai;

    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;
} 