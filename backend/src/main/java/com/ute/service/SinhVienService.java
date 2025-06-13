package com.ute.service;

import java.util.List;

import com.ute.entity.SinhVien;

public interface SinhVienService {
    List<SinhVien> getAllSinhVien();
    SinhVien getSinhVienById(String id);
    List<SinhVien> getSinhVienByLop(String maLop);
    SinhVien createSinhVien(SinhVien sinhVien);
    SinhVien updateSinhVien(String id, SinhVien sinhVien);
    void deleteSinhVien(String id);
} 