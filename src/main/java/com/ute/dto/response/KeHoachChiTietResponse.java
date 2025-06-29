package com.ute.dto.response;

import lombok.Data;

@Data
public class KeHoachChiTietResponse {
    // Thông tin kế hoạch
    private Integer maKeHoach;
    private Integer hocKyDuKien;
    private String namHocDuKien;
    private Integer trangThai;
    private String trangThaiText;
    
    // Thông tin sinh viên
    private String maSinhVien;
    private String hoTenSinhVien;
    private String email;
    private String soDienThoai;
    private String maLop;
    private String tenLop;
    private String maKhoa;
    private String tenKhoa;
    
    // Thông tin học phần
    private String maHocPhan;
    private String tenHocPhan;
    private Integer soTinChi;
    private String maKhoaHocPhan;
    private String tenKhoaHocPhan;
    
    // Thông tin tiến độ học tập
    private Boolean daDangKy;
    private Boolean coDiem;
    private Float diemTongKet;
    private String xepLoai;
    private String ghiChu;
} 