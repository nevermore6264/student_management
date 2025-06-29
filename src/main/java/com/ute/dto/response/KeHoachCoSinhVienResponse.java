package com.ute.dto.response;

import lombok.Data;

@Data
public class KeHoachCoSinhVienResponse {
    private Integer maKeHoach;
    private String maSinhVien;
    private String hoTenSinhVien;
    private String maHocPhan;
    private String tenHocPhan;
    private Integer soTinChi;
    private Integer hocKyDuKien;
    private String namHocDuKien;
    private Integer trangThai;
    private String trangThaiText; // "Chưa học", "Đã học", "Đang học"
} 