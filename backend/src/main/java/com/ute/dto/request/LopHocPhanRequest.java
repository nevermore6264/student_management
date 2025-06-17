package com.ute.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LopHocPhanRequest {
    @NotBlank(message = "Mã lớp học phần không được để trống")
    private String maLopHP;

    @NotBlank(message = "Mã học phần không được để trống")
    private String maHocPhan;

    @NotBlank(message = "Tên lớp học phần không được để trống")
    private String tenLopHP;

    @NotNull(message = "Số lượng không được để trống")
    private Integer soLuong;

    @NotBlank(message = "Giảng viên không được để trống")
    private String giangVien;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalDateTime thoiGianBatDau;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalDateTime thoiGianKetThuc;

    @NotBlank(message = "Phòng học không được để trống")
    private String phongHoc;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean trangThai;
} 