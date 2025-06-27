package com.ute.dto.response;

import lombok.Data;

@Data
public class DiemResponse {
    private String maDiem;
    private String maSinhVien;
    private String hoTenSinhVien;
    private String maLopHP;
    private String tenLopHP;
    private String tenMon;
    private int soTinChi;
    private double diem;
    private String xepLoai;
    private String hocKy;
    private String namHoc;
    
    // Các field mới cho giảng viên quản lý điểm
    private Float diemChuyenCan;
    private Float diemGiuaKy;
    private Float diemCuoiKy;
    private Float diemTongKet;
    private String ghiChu;
} 