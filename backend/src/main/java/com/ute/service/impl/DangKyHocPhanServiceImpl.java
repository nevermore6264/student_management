package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.entity.DangKyHocPhan;
import com.ute.repository.DangKyHocPhanRepository;
import com.ute.service.DangKyHocPhanService;

@Service
public class DangKyHocPhanServiceImpl implements DangKyHocPhanService {

    @Autowired
    private DangKyHocPhanRepository dangKyHocPhanRepository;

    @Override
    public List<DangKyHocPhan> getDangKyBySinhVien(String maSinhVien) {
        return dangKyHocPhanRepository.findByPhienDangKy_SinhVien_MaSinhVien(maSinhVien);
    }

    @Override
    public List<DangKyHocPhan> getDangKyByLopHP(String maLopHP) {
        return dangKyHocPhanRepository.findByLopHocPhan_MaLopHP(maLopHP);
    }

    @Override
    public DangKyHocPhan createDangKy(DangKyHocPhan dangKyHocPhan) {
        return dangKyHocPhanRepository.save(dangKyHocPhan);
    }

    @Override
    public DangKyHocPhan updateDangKy(String id, DangKyHocPhan dangKyHocPhan) {
        DangKyHocPhan existingDangKy = dangKyHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DangKyHocPhan not found with id: " + id));
        
        existingDangKy.setTrangThai(dangKyHocPhan.getTrangThai());
        existingDangKy.setKetQuaDangKy(dangKyHocPhan.getKetQuaDangKy());
        
        return dangKyHocPhanRepository.save(existingDangKy);
    }

    @Override
    public void deleteDangKy(String id) {
        DangKyHocPhan dangKyHocPhan = dangKyHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DangKyHocPhan not found with id: " + id));
        dangKyHocPhanRepository.delete(dangKyHocPhan);
    }
} 