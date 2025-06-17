package com.ute.dto.response;

import lombok.Data;

@Data
public class DiemChiTietAllSinhVienResponse {
    private String maSinhVien;
    private String hoTen;
    private String tenMon;
    private int soTinChi;
    private double diem;
    private String xepLoai;
    private String hocKy;
    private String namHoc;
} 