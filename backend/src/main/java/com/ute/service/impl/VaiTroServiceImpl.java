package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.entity.VaiTro;
import com.ute.repository.VaiTroRepository;
import com.ute.service.VaiTroService;

@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getAllVaiTro() {
        return vaiTroRepository.findAll();
    }

    @Override
    public VaiTro updateVaiTro(VaiTro vaiTro) {
        VaiTro existingVaiTro = vaiTroRepository.findById(vaiTro.getMaVaiTro())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò với mã: " + vaiTro.getMaVaiTro()));
        
        // Chỉ cập nhật miêu tả
        existingVaiTro.setMoTa(vaiTro.getMoTa());
        
        return vaiTroRepository.save(existingVaiTro);
    }
} 