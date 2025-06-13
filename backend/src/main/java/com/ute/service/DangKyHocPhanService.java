package com.ute.service;

import java.util.List;

import com.ute.entity.DangKyHocPhan;

public interface DangKyHocPhanService {
    List<DangKyHocPhan> getDangKyBySinhVien(String maSinhVien);
    List<DangKyHocPhan> getDangKyByLopHP(String maLopHP);
    DangKyHocPhan createDangKy(DangKyHocPhan dangKyHocPhan);
    DangKyHocPhan updateDangKy(String id, DangKyHocPhan dangKyHocPhan);
    void deleteDangKy(String id);
} 