package com.ute.service;

import java.util.List;

import com.ute.dto.request.DangKyHocPhanRequest;
import com.ute.dto.response.DangKyHocPhanResponse;
import com.ute.entity.DangKyHocPhan;
import com.ute.entity.DangKyHocPhanId;

public interface DangKyHocPhanService {
    List<DangKyHocPhan> getDangKyBySinhVien(String maSinhVien);
    List<DangKyHocPhan> getDangKyByLopHP(String maLopHP);
    DangKyHocPhan createDangKy(DangKyHocPhan dangKyHocPhan);
    DangKyHocPhan createDangKyFromRequest(DangKyHocPhanRequest request);
    DangKyHocPhan updateDangKy(DangKyHocPhanId id, DangKyHocPhan dangKyHocPhan);
    void deleteDangKy(DangKyHocPhanId id);
    
    // Các method mới cho giảng viên
    List<DangKyHocPhanResponse> getLichSuDangKyByHocPhan(String maHocPhan);
    Object getThongKeDangKyByHocPhan(String maHocPhan);
} 