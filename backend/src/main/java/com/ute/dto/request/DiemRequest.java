package com.ute.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiemRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSinhVien;
    
    @NotBlank(message = "Mã lớp học phần không được để trống")
    private String maLopHP;
    
    @DecimalMin(value = "0.0", message = "Điểm chuyên cần phải từ 0.0 đến 10.0")
    @DecimalMax(value = "10.0", message = "Điểm chuyên cần phải từ 0.0 đến 10.0")
    private Float diemChuyenCan;
    
    @DecimalMin(value = "0.0", message = "Điểm giữa kỳ phải từ 0.0 đến 10.0")
    @DecimalMax(value = "10.0", message = "Điểm giữa kỳ phải từ 0.0 đến 10.0")
    private Float diemGiuaKy;
    
    @DecimalMin(value = "0.0", message = "Điểm cuối kỳ phải từ 0.0 đến 10.0")
    @DecimalMax(value = "10.0", message = "Điểm cuối kỳ phải từ 0.0 đến 10.0")
    private Float diemCuoiKy;
    
    private String ghiChu;
} 