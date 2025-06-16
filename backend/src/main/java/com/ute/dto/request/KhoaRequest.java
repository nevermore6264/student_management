package com.ute.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KhoaRequest {
    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;

    @NotBlank(message = "Tên khoa không được để trống")
    private String tenKhoa;
} 