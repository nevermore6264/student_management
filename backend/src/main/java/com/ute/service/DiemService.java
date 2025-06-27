package com.ute.service;

import java.util.List;

import com.ute.dto.request.DiemRequest;
import com.ute.dto.response.DiemChiTietAllSinhVienResponse;
import com.ute.dto.response.DiemChiTietResponse;
import com.ute.dto.response.DiemFullInfoResponse;
import com.ute.dto.response.DiemResponse;
import com.ute.dto.response.DiemTongQuanAllSinhVienResponse;
import com.ute.dto.response.DiemTongQuanLopResponse;
import com.ute.dto.response.DiemTongQuanResponse;
import com.ute.dto.response.SinhVienTrongLopResponse;

public interface DiemService {
    List<DiemFullInfoResponse> getAllDiem();
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
    
    // Các method mới cho giảng viên
    List<SinhVienTrongLopResponse> getSinhVienTrongLop(String maLopHP);
    DiemResponse nhapDiem(DiemRequest request);
    DiemResponse capNhatDiem(String id, DiemRequest request);
    DiemTongQuanLopResponse getTongQuanDiemLop(String maLopHP);
    byte[] xuatBaoCaoDiem(String maLopHP);
} 