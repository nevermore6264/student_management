package com.ute.service;

import java.util.List;

import com.ute.entity.Khoa;

public interface KhoaService {
    List<Khoa> getAllKhoa();
    Khoa getKhoaById(String id);
    Khoa createKhoa(Khoa khoa);
    Khoa updateKhoa(String id, Khoa khoa);
    void deleteKhoa(String id);
} 