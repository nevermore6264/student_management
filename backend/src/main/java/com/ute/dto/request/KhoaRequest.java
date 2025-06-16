package com.ute.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KhoaRequest {
    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;

    @NotBlank(message = "Tên khoa không được để trống")
    private String tenKhoa;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String soDienThoai;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotBlank(message = "Mã trường không được để trống")
    private String maTruong;

    private Integer trangThai;
} 