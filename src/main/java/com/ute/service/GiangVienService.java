package com.ute.service;

import java.util.List;

import com.ute.dto.request.GiangVienRequest;
import com.ute.dto.response.GiangVienResponse;

public interface GiangVienService {
    List<GiangVienResponse> getAllGiangVien();
    GiangVienResponse getGiangVienById(String id);
    GiangVienResponse createGiangVien(GiangVienRequest request);
    GiangVienResponse updateGiangVien(String id, GiangVienRequest request);
    void deleteGiangVien(String id);
    List<GiangVienResponse> getGiangVienByKhoa(String maKhoa);
} 