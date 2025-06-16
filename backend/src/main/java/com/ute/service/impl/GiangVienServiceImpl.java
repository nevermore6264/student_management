package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.entity.GiangVien;
import com.ute.repository.GiangVienRepository;
import com.ute.service.GiangVienService;

@Service
public class GiangVienServiceImpl implements GiangVienService {

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiangVien> getAllGiangVien() {
        return giangVienRepository.findAllWithRelations();
    }

    @Override
    public GiangVien getGiangVienById(String id) {
        return giangVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GiangVien not found with id: " + id));
    }

    @Override
    public List<GiangVien> getGiangVienByKhoa(String maKhoa) {
        return giangVienRepository.findByKhoa_MaKhoa(maKhoa);
    }

    @Override
    public GiangVien createGiangVien(GiangVien giangVien) {
        return giangVienRepository.save(giangVien);
    }

    @Override
    public GiangVien updateGiangVien(String id, GiangVien giangVien) {
        GiangVien existingGiangVien = giangVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GiangVien not found with id: " + id));
        
        existingGiangVien.setTenGiangVien(giangVien.getTenGiangVien());
        existingGiangVien.setSoDienThoai(giangVien.getSoDienThoai());
        existingGiangVien.setEmail(giangVien.getEmail());
        existingGiangVien.setKhoa(giangVien.getKhoa());

        return giangVienRepository.save(existingGiangVien);
    }

    @Override
    public void deleteGiangVien(String id) {
        GiangVien giangVien = giangVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GiangVien not found with id: " + id));
        giangVienRepository.delete(giangVien);
    }
} 
