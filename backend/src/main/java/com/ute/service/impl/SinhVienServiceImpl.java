package com.ute.service.impl;

import com.ute.entity.SinhVien;
import com.ute.repository.SinhVienRepository;
import com.ute.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Override
    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    @Override
    public SinhVien getSinhVienById(String id) {
        return sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SinhVien not found with id: " + id));
    }

    @Override
    public List<SinhVien> getSinhVienByLop(String maLop) {
        return sinhVienRepository.findByLop_MaLop(maLop);
    }

    @Override
    public SinhVien createSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public SinhVien updateSinhVien(String id, SinhVien sinhVien) {
        SinhVien existingSinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SinhVien not found with id: " + id));
        
        existingSinhVien.setHoTenSinhVien(sinhVien.getHoTenSinhVien());
        existingSinhVien.setNgaySinh(sinhVien.getNgaySinh());
        existingSinhVien.setGioiTinh(sinhVien.getGioiTinh());
        existingSinhVien.setDiaChi(sinhVien.getDiaChi());
        existingSinhVien.setSoDienThoai(sinhVien.getSoDienThoai());
        existingSinhVien.setEmail(sinhVien.getEmail());
        existingSinhVien.setLop(sinhVien.getLop());
        
        return sinhVienRepository.save(existingSinhVien);
    }

    @Override
    public void deleteSinhVien(String id) {
        SinhVien sinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SinhVien not found with id: " + id));
        sinhVienRepository.delete(sinhVien);
    }
} 
