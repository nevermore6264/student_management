package com.ute.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KeHoachCoSinhVienRequest {
    
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSinhVien;
    
    @NotBlank(message = "Mã học phần không được để trống")
    private String maHocPhan;
    
    @NotNull(message = "Học kỳ dự kiến không được để trống")
    @Min(value = 1, message = "Học kỳ dự kiến phải từ 1 trở lên")
    @Max(value = 8, message = "Học kỳ dự kiến không được vượt quá 8")
    private Integer hocKyDuKien;
    
    @NotBlank(message = "Năm học dự kiến không được để trống")
    private String namHocDuKien;
    
    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Trạng thái phải từ 0-2")
    @Max(value = 2, message = "Trạng thái phải từ 0-2")
    private Integer trangThai; // 0: Chưa học, 1: Đã học, 2: Đang học
} 