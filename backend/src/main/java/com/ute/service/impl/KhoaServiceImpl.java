package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.entity.Khoa;
import com.ute.repository.KhoaRepository;
import com.ute.service.KhoaService;

@Service
public class KhoaServiceImpl implements KhoaService {

    @Autowired
    private KhoaRepository khoaRepository;

    @Override
    public List<Khoa> getAllKhoa() {
        return khoaRepository.findAll();
    }

    @Override
    public Khoa getKhoaById(String id) {
        return khoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khoa not found with id: " + id));
    }

    @Override
    public Khoa createKhoa(Khoa khoa) {
        return khoaRepository.save(khoa);
    }

    @Override
    public Khoa updateKhoa(String id, Khoa khoa) {
        Khoa existingKhoa = getKhoaById(id);
        
        existingKhoa.setTenKhoa(khoa.getTenKhoa());
        existingKhoa.setSoDienThoai(khoa.getSoDienThoai());
        existingKhoa.setEmail(khoa.getEmail());
        existingKhoa.setDiaChi(khoa.getDiaChi());
        existingKhoa.setMaTruong(khoa.getMaTruong());
        existingKhoa.setTrangThai(khoa.getTrangThai());
        
        return khoaRepository.save(existingKhoa);
    }

    @Override
    public void deleteKhoa(String id) {
        Khoa khoa = getKhoaById(id);
        khoaRepository.delete(khoa);
    }
} 