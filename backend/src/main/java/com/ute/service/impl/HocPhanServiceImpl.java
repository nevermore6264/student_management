package com.ute.service.impl;

import com.ute.entity.HocPhan;
import com.ute.repository.HocPhanRepository;
import com.ute.service.HocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HocPhanServiceImpl implements HocPhanService {

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Override
    public List<HocPhan> getAllHocPhan() {
        return hocPhanRepository.findAll();
    }

    @Override
    public HocPhan getHocPhanById(String id) {
        return hocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HocPhan not found with id: " + id));
    }

    @Override
    public List<HocPhan> getHocPhanByKhoa(String maKhoa) {
        return hocPhanRepository.findByKhoa_MaKhoa(maKhoa);
    }

    @Override
    public HocPhan createHocPhan(HocPhan hocPhan) {
        return hocPhanRepository.save(hocPhan);
    }

    @Override
    public HocPhan updateHocPhan(String id, HocPhan hocPhan) {
        HocPhan existingHocPhan = getHocPhanById(id);
        
        existingHocPhan.setTenHocPhan(hocPhan.getTenHocPhan());
        existingHocPhan.setSoTinChi(hocPhan.getSoTinChi());
        existingHocPhan.setKhoa(hocPhan.getKhoa());
        
        return hocPhanRepository.save(existingHocPhan);
    }

    @Override
    public void deleteHocPhan(String id) {
        HocPhan hocPhan = getHocPhanById(id);
        hocPhanRepository.delete(hocPhan);
    }
} 