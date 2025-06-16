package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.SinhVienRequest;
import com.ute.dto.response.SinhVienResponse;
import com.ute.entity.Lop;
import com.ute.entity.SinhVien;
import com.ute.repository.LopRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.SinhVienService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SinhVienServiceImpl implements SinhVienService {
    private final SinhVienRepository sinhVienRepository;
    private final LopRepository lopRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SinhVienResponse> getAllSinhVien() {
        return sinhVienRepository.findAllWithRelations().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SinhVienResponse getSinhVienById(String id) {
        SinhVien sinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với mã: " + id));
        return mapToResponse(sinhVien);
    }

    @Override
    @Transactional
    public SinhVienResponse createSinhVien(SinhVienRequest request) {
        SinhVien sinhVien = mapToEntity(request);
        SinhVien savedSinhVien = sinhVienRepository.save(sinhVien);
        return mapToResponse(savedSinhVien);
    }

    @Override
    @Transactional
    public SinhVienResponse updateSinhVien(String id, SinhVienRequest request) {
        SinhVien existingSinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với mã: " + id));
        
        updateEntityFromRequest(existingSinhVien, request);
        SinhVien updatedSinhVien = sinhVienRepository.save(existingSinhVien);
        return mapToResponse(updatedSinhVien);
    }

    @Override
    @Transactional
    public void deleteSinhVien(String id) {
        if (!sinhVienRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sinh viên với mã: " + id);
        }
        sinhVienRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVienResponse> getSinhVienByLop(String maLop) {
        return sinhVienRepository.findByLop_MaLop(maLop).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SinhVienResponse mapToResponse(SinhVien sinhVien) {
        SinhVienResponse response = new SinhVienResponse();
        response.setMaSinhVien(sinhVien.getMaSinhVien());
        response.setHoTenSinhVien(sinhVien.getHoTenSinhVien());
        response.setEmail(sinhVien.getEmail());
        response.setSoDienThoai(sinhVien.getSoDienThoai());
        response.setDiaChi(sinhVien.getDiaChi());
        response.setNgaySinh(sinhVien.getNgaySinh());
        response.setGioiTinh(sinhVien.getGioiTinh());
        
        if (sinhVien.getLop() != null) {
            response.setMaLop(sinhVien.getLop().getMaLop());
            response.setTenLop(sinhVien.getLop().getTenLop());
        }
        
        return response;
    }

    private SinhVien mapToEntity(SinhVienRequest request) {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien(request.getMaSinhVien());
        sinhVien.setHoTenSinhVien(request.getHoTenSinhVien());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setSoDienThoai(request.getSoDienThoai());
        sinhVien.setDiaChi(request.getDiaChi());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setGioiTinh(request.getGioiTinh());
        
        if (request.getMaLop() != null) {
            Lop lop = lopRepository.findById(request.getMaLop())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp với mã: " + request.getMaLop()));
            sinhVien.setLop(lop);
        }
        
        return sinhVien;
    }

    private void updateEntityFromRequest(SinhVien sinhVien, SinhVienRequest request) {
        sinhVien.setHoTenSinhVien(request.getHoTenSinhVien());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setSoDienThoai(request.getSoDienThoai());
        sinhVien.setDiaChi(request.getDiaChi());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setGioiTinh(request.getGioiTinh());
        
        if (request.getMaLop() != null) {
            Lop lop = lopRepository.findById(request.getMaLop())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp với mã: " + request.getMaLop()));
            sinhVien.setLop(lop);
        }
    }
} 
