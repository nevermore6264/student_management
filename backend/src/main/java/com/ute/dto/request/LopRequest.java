package com.ute.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LopRequest {
    @NotBlank(message = "Mã lớp không được để trống")
    private String maLop;

    @NotBlank(message = "Tên lớp không được để trống")
    private String tenLop;

    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;
} 