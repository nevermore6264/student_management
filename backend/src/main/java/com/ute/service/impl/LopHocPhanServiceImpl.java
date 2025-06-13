package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.entity.LopHocPhan;
import com.ute.repository.LopHocPhanRepository;
import com.ute.service.LopHocPhanService;

@Service
public class LopHocPhanServiceImpl implements LopHocPhanService {

    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;

    @Override
    public List<LopHocPhan> findAll() {
        return lopHocPhanRepository.findAll();
    }

    @Override
    public LopHocPhan findById(String id) {
        return lopHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lớp học phần không tồn tại"));
    }

    @Override
    public LopHocPhan save(LopHocPhan lopHocPhan) {
        return lopHocPhanRepository.save(lopHocPhan);
    }

    @Override
    public void deleteById(String id) {
        LopHocPhan lopHocPhan = findById(id);
        lopHocPhanRepository.delete(lopHocPhan);
    }

    @Override
    public List<LopHocPhan> findByHocPhan_MaHocPhan(String maHocPhan) {
        return lopHocPhanRepository.findByHocPhan_MaHocPhan(maHocPhan);
    }

    @Override
    public List<LopHocPhan> findByGiangVien(String maGiangVien) {
        return lopHocPhanRepository.findByGiangVien(maGiangVien);
    }

    @Override
    public LopHocPhan createLopHocPhan(LopHocPhan lopHocPhan) {
        return lopHocPhanRepository.save(lopHocPhan);
    }

    @Override
    public LopHocPhan updateLopHocPhan(String id, LopHocPhan lopHocPhan) {
        LopHocPhan existingLopHocPhan = lopHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lớp học phần không tồn tại"));

        existingLopHocPhan.setHocPhan(lopHocPhan.getHocPhan());
        existingLopHocPhan.setTenLopHP(lopHocPhan.getTenLopHP());
        existingLopHocPhan.setSoLuong(lopHocPhan.getSoLuong());
        existingLopHocPhan.setGiangVien(lopHocPhan.getGiangVien());
        existingLopHocPhan.setThoiGianBatDau(lopHocPhan.getThoiGianBatDau());
        existingLopHocPhan.setThoiGianKetThuc(lopHocPhan.getThoiGianKetThuc());
        existingLopHocPhan.setPhongHoc(lopHocPhan.getPhongHoc());
        existingLopHocPhan.setTrangThai(lopHocPhan.getTrangThai());
        existingLopHocPhan.setDangKyHocPhans(lopHocPhan.getDangKyHocPhans());
        existingLopHocPhan.setDiems(lopHocPhan.getDiems());
        existingLopHocPhan.setThoiKhoaBieus(lopHocPhan.getThoiKhoaBieus());
        existingLopHocPhan.setLichSuDangKys(lopHocPhan.getLichSuDangKys());

        return lopHocPhanRepository.save(existingLopHocPhan);
    }
} 
