package com.ute.service;

import java.util.List;

import com.ute.entity.Diem;

public interface DiemService {
    List<Diem> getAllDiem();
    Diem getDiemById(String id);
    List<Diem> getDiemBySinhVien(String maSinhVien);
    List<Diem> getDiemByLopHocPhan(String maLopHP);
    Diem createDiem(Diem diem);
    Diem updateDiem(String id, Diem diem);
    void deleteDiem(String id);
} 