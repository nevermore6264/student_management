package com.ute.service;

import java.util.List;

import com.ute.entity.Lop;

public interface LopService {
    List<Lop> getAllLop();
    Lop getLopById(String id);
    List<Lop> getLopByKhoa(String maKhoa);
    Lop createLop(Lop lop);
    Lop updateLop(String id, Lop lop);
    void deleteLop(String id);
} 