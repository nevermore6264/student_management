package com.ute.service;

import java.util.List;

import com.ute.dto.response.DangKyHocPhanResponse;
import com.ute.entity.DangKyHocPhan;

public interface DangKyHocPhanService {
    List<DangKyHocPhan> getDangKyBySinhVien(String maSinhVien);
    List<DangKyHocPhan> getDangKyByLopHP(String maLopHP);
    DangKyHocPhan createDangKy(DangKyHocPhan dangKyHocPhan);
    DangKyHocPhan updateDangKy(String id, DangKyHocPhan dangKyHocPhan);
    void deleteDangKy(String id);
    
    // Các method mới cho giảng viên
    List<DangKyHocPhanResponse> getLichSuDangKyByHocPhan(String maHocPhan);
    Object getThongKeDangKyByHocPhan(String maHocPhan);
} 