package com.ute.service;

import java.util.List;

import com.ute.entity.LopHocPhan;

public interface LopHocPhanService {
    List<LopHocPhan> findAll();
    LopHocPhan findById(String id);
    LopHocPhan save(LopHocPhan lopHocPhan);
    void deleteById(String id);
    List<LopHocPhan> findByHocPhan_MaHocPhan(String maHocPhan);
    List<LopHocPhan> findByGiangVien(String maGiangVien);
    LopHocPhan createLopHocPhan(LopHocPhan lopHocPhan);
    LopHocPhan updateLopHocPhan(String id, LopHocPhan lopHocPhan);
} 