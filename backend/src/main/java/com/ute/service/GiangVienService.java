package com.ute.service;

import java.util.List;

import com.ute.entity.GiangVien;

public interface GiangVienService {
    List<GiangVien> getAllGiangVien();
    GiangVien getGiangVienById(String id);
    List<GiangVien> getGiangVienByKhoa(String maKhoa);
    GiangVien createGiangVien(GiangVien giangVien);
    GiangVien updateGiangVien(String id, GiangVien giangVien);
    void deleteGiangVien(String id);
} 