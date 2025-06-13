package com.ute.service.impl;

import com.ute.entity.Lop;
import com.ute.repository.LopRepository;
import com.ute.service.LopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopServiceImpl implements LopService {

    @Autowired
    private LopRepository lopRepository;

    @Override
    public List<Lop> getAllLop() {
        return lopRepository.findAll();
    }

    @Override
    public Lop getLopById(String id) {
        return lopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lop not found with id: " + id));
    }

    @Override
    public List<Lop> getLopByKhoa(String maKhoa) {
        return lopRepository.findByKhoa_MaKhoa(maKhoa);
    }

    @Override
    public Lop createLop(Lop lop) {
        return lopRepository.save(lop);
    }

    @Override
    public Lop updateLop(String id, Lop lop) {
        Lop existingLop = getLopById(id);
        
        existingLop.setTenLop(lop.getTenLop());
        existingLop.setKhoa(lop.getKhoa());
        existingLop.setKhoaHoc(lop.getKhoaHoc());
        existingLop.setHeDaoTao(lop.getHeDaoTao());
        
        return lopRepository.save(existingLop);
    }

    @Override
    public void deleteLop(String id) {
        Lop lop = getLopById(id);
        lopRepository.delete(lop);
    }
} 