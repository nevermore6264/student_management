package com.ute.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.dto.request.DangKyHocPhanRequest;
import com.ute.dto.response.DangKyHocPhanResponse;
import com.ute.entity.DangKyHocPhan;
import com.ute.entity.DangKyHocPhanId;
import com.ute.entity.LopHocPhan;
import com.ute.entity.PhienDangKy;
import com.ute.entity.PhienDangKyId;
import com.ute.entity.SinhVien;
import com.ute.repository.DangKyHocPhanRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.repository.PhienDangKyRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.DangKyHocPhanService;

@Service
public class DangKyHocPhanServiceImpl implements DangKyHocPhanService {

    @Autowired
    private DangKyHocPhanRepository dangKyHocPhanRepository;
    
    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private LopHocPhanRepository lopHocPhanRepository;
    
    @Autowired
    private PhienDangKyRepository phienDangKyRepository;

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
    public DangKyHocPhan createDangKyFromRequest(DangKyHocPhanRequest request) {
        // Kiểm tra sinh viên tồn tại
        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSinhVien())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với mã: " + request.getMaSinhVien()));
        
        // Kiểm tra lớp học phần tồn tại
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(request.getMaLopHP())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học phần với mã: " + request.getMaLopHP()));
        
        // Tìm hoặc tạo phiên đăng ký
        PhienDangKy phienDangKy = null;
        if (request.getMaPhienDK() != null) {
            PhienDangKyId phienDangKyId = new PhienDangKyId(request.getMaPhienDK(), request.getMaSinhVien());
            phienDangKy = phienDangKyRepository.findById(phienDangKyId).orElse(null);
        }
        
        // Nếu không có phiên đăng ký, tạo mới
        if (phienDangKy == null) {
            phienDangKy = new PhienDangKy();
            phienDangKy.setId(new PhienDangKyId(request.getMaPhienDK() != null ? request.getMaPhienDK() : 1, request.getMaSinhVien()));
            phienDangKy.setSinhVien(sinhVien);
            phienDangKy.setNgayGioBatDau(LocalDateTime.now());
            phienDangKy.setThoiGian(30); // 30 phút mặc định
            phienDangKy.setTrangThai(true);
            phienDangKy = phienDangKyRepository.save(phienDangKy);
        }
        
        // Tạo composite key
        DangKyHocPhanId dangKyId = new DangKyHocPhanId(
            phienDangKy.getId().getMaPhienDK(),
            request.getMaSinhVien(),
            request.getMaLopHP()
        );
        
        // Kiểm tra xem đã đăng ký chưa
        if (dangKyHocPhanRepository.findById(dangKyId).isPresent()) {
            throw new RuntimeException("Sinh viên đã đăng ký lớp học phần này rồi");
        }
        
        // Tạo đăng ký học phần
        DangKyHocPhan dangKyHocPhan = new DangKyHocPhan();
        dangKyHocPhan.setId(dangKyId);
        dangKyHocPhan.setPhienDangKy(phienDangKy);
        dangKyHocPhan.setLopHocPhan(lopHocPhan);
        dangKyHocPhan.setThoiGianDangKy(request.getThoiGianDangKy() != null ? request.getThoiGianDangKy() : LocalDateTime.now());
        dangKyHocPhan.setTrangThai(request.getTrangThai() != null ? request.getTrangThai() : true);
        dangKyHocPhan.setKetQuaDangKy(request.getKetQuaDangKy() != null ? request.getKetQuaDangKy() : 1);
        
        return dangKyHocPhanRepository.save(dangKyHocPhan);
    }

    @Override
    public DangKyHocPhan updateDangKy(DangKyHocPhanId id, DangKyHocPhan dangKyHocPhan) {
        DangKyHocPhan existingDangKy = dangKyHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DangKyHocPhan not found with id: " + id));
        
        existingDangKy.setTrangThai(dangKyHocPhan.getTrangThai());
        existingDangKy.setKetQuaDangKy(dangKyHocPhan.getKetQuaDangKy());
        
        return dangKyHocPhanRepository.save(existingDangKy);
    }

    @Override
    public void deleteDangKy(DangKyHocPhanId id) {
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
            response.setGiangVien(dangKy.getLopHocPhan().getGiangVien());
            response.setPhongHoc(dangKy.getLopHocPhan().getPhongHoc());
            
            if (dangKy.getLopHocPhan().getHocPhan() != null) {
                response.setMaHocPhan(dangKy.getLopHocPhan().getHocPhan().getMaHocPhan());
                response.setTenHocPhan(dangKy.getLopHocPhan().getHocPhan().getTenHocPhan());
                response.setSoTinChi(dangKy.getLopHocPhan().getHocPhan().getSoTinChi());
            }
        }
        
        response.setThoiGianDangKy(dangKy.getThoiGianDangKy());
        response.setTrangThai(dangKy.getTrangThai());
        response.setKetQuaDangKy(dangKy.getKetQuaDangKy());
        
        return response;
    }
} 