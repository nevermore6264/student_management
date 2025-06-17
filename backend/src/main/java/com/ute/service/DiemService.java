package com.ute.service;

import java.util.List;

import com.ute.entity.Diem;
import com.ute.dto.response.*;

public interface DiemService {
    List<DiemResponse> getAllDiem();
    DiemResponse getDiemById(String id);
    List<DiemResponse> getDiemBySinhVien(String maSinhVien);
    List<DiemResponse> getDiemByLopHocPhan(String maLopHP);
    DiemResponse createDiem(DiemResponse diem);
    DiemResponse updateDiem(String id, DiemResponse diem);
    void deleteDiem(String id);

    // Tổng quan và chi tiết cho 1 sinh viên
    DiemTongQuanResponse getTongQuanDiem(String maSinhVien);
    List<DiemChiTietResponse> getChiTietDiem(String maSinhVien);

    // Tổng quan và chi tiết cho toàn bộ sinh viên
    List<DiemTongQuanAllSinhVienResponse> getTongQuanTatCaSinhVien();
    List<DiemChiTietAllSinhVienResponse> getChiTietTatCaSinhVien();
} 