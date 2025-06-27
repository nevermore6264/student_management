package com.ute.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.dto.response.DangKyHocPhanResponse;
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

    // ==================== CÁC METHOD MỚI CHO GIẢNG VIÊN ====================

    @Override
    public List<DangKyHocPhanResponse> getLichSuDangKyByHocPhan(String maHocPhan) {
        List<DangKyHocPhan> dangKyList = dangKyHocPhanRepository.findByLopHocPhan_HocPhan_MaHocPhan(maHocPhan);
        return dangKyList.stream()
                .map(this::mapToDangKyResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Object getThongKeDangKyByHocPhan(String maHocPhan) {
        List<DangKyHocPhan> dangKyList = dangKyHocPhanRepository.findByLopHocPhan_HocPhan_MaHocPhan(maHocPhan);
        
        Map<String, Object> thongKe = new HashMap<>();
        thongKe.put("tongSoDangKy", dangKyList.size());
        
        long soDangKyThanhCong = dangKyList.stream()
                .filter(dk -> dk.getTrangThai() != null && dk.getTrangThai())
                .count();
        thongKe.put("soDangKyThanhCong", soDangKyThanhCong);
        thongKe.put("soDangKyThatBai", dangKyList.size() - soDangKyThanhCong);
        
        // Thống kê theo lớp học phần
        Map<String, Long> thongKeTheoLop = dangKyList.stream()
                .collect(Collectors.groupingBy(
                    dk -> dk.getLopHocPhan() != null ? dk.getLopHocPhan().getMaLopHP() : "Unknown",
                    Collectors.counting()
                ));
        thongKe.put("thongKeTheoLop", thongKeTheoLop);
        
        return thongKe;
    }

    private DangKyHocPhanResponse mapToDangKyResponse(DangKyHocPhan dangKy) {
        DangKyHocPhanResponse response = new DangKyHocPhanResponse();
        
        if (dangKy.getPhienDangKy() != null && dangKy.getPhienDangKy().getSinhVien() != null) {
            response.setMaSinhVien(dangKy.getPhienDangKy().getSinhVien().getMaSinhVien());
            response.setHoTenSinhVien(dangKy.getPhienDangKy().getSinhVien().getHoTenSinhVien());
        }
        
        if (dangKy.getLopHocPhan() != null) {
            response.setMaLopHP(dangKy.getLopHocPhan().getMaLopHP());
            response.setTenLopHP(dangKy.getLopHocPhan().getTenLopHP());
            
            if (dangKy.getLopHocPhan().getHocPhan() != null) {
                response.setMaHocPhan(dangKy.getLopHocPhan().getHocPhan().getMaHocPhan());
                response.setTenHocPhan(dangKy.getLopHocPhan().getHocPhan().getTenHocPhan());
            }
        }
        
        response.setThoiGianDangKy(dangKy.getThoiGianDangKy());
        response.setTrangThai(dangKy.getTrangThai());
        response.setKetQuaDangKy(dangKy.getKetQuaDangKy());
        
        return response;
    }
} 