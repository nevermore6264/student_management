package com.ute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HocPhanRequest {
    @NotBlank(message = "Mã học phần không được để trống")
    private String maHocPhan;

    @NotBlank(message = "Tên học phần không được để trống")
    private String tenHocPhan;

    @NotNull(message = "Số tín chỉ không được để trống")
    private Integer soTinChi;

    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;
} 