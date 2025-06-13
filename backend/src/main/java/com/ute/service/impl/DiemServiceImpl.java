package com.ute.service.impl;

import com.ute.entity.Diem;
import com.ute.repository.DiemRepository;
import com.ute.service.DiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiemServiceImpl implements DiemService {

    @Autowired
    private DiemRepository diemRepository;

    @Override
    public List<Diem> getAllDiem() {
        return diemRepository.findAll();
    }

    @Override
    public Diem getDiemById(String id) {
        return diemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diem not found with id: " + id));
    }

    @Override
    public List<Diem> getDiemBySinhVien(String maSinhVien) {
        return diemRepository.findBySinhVien_MaSinhVien(maSinhVien);
    }

    @Override
    public List<Diem> getDiemByLopHocPhan(String maLopHP) {
        return diemRepository.findByLopHocPhan_MaLopHP(maLopHP);
    }

    @Override
    public Diem createDiem(Diem diem) {
        return diemRepository.save(diem);
    }

    @Override
    public Diem updateDiem(String id, Diem diem) {
        Diem existingDiem = diemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diem not found with id: " + id));
        
        existingDiem.setDiemChuyenCan(diem.getDiemChuyenCan());
        existingDiem.setDiemGiuaKy(diem.getDiemGiuaKy());
        existingDiem.setDiemTongKet(diem.getDiemTongKet());

        return diemRepository.save(existingDiem);
    }

    @Override
    public void deleteDiem(String id) {
        Diem diem = diemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diem not found with id: " + id));
        diemRepository.delete(diem);
    }
} 
