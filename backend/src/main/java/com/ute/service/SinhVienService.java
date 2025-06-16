package com.ute.service;

import java.util.List;

import com.ute.dto.request.SinhVienRequest;
import com.ute.dto.response.SinhVienResponse;

public interface SinhVienService {
    List<SinhVienResponse> getAllSinhVien();
    SinhVienResponse getSinhVienById(String id);
    SinhVienResponse createSinhVien(SinhVienRequest request);
    SinhVienResponse updateSinhVien(String id, SinhVienRequest request);
    void deleteSinhVien(String id);
    List<SinhVienResponse> getSinhVienByLop(String maLop);
} 