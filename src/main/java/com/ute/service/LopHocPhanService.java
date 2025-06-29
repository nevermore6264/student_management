package com.ute.service;

import java.util.List;

import com.ute.dto.request.LopHocPhanRequest;
import com.ute.dto.response.LopHocPhanResponse;

public interface LopHocPhanService {
    List<LopHocPhanResponse> findAll();
    LopHocPhanResponse findById(String id);
    List<LopHocPhanResponse> findByHocPhan_MaHocPhan(String maHocPhan);
    List<LopHocPhanResponse> findByGiangVien(String maGiangVien);
    List<LopHocPhanResponse> findByDotDangKy(String maDotDK);
    LopHocPhanResponse createLopHocPhan(LopHocPhanRequest request);
    LopHocPhanResponse updateLopHocPhan(String id, LopHocPhanRequest request);
    void deleteById(String id);
} 