package com.ute.service.impl;

import com.ute.dto.request.LopHocPhanRequest;
import com.ute.dto.response.LopHocPhanResponse;
import com.ute.entity.HocPhan;
import com.ute.entity.LopHocPhan;
import com.ute.repository.DangKyHocPhanRepository;
import com.ute.repository.DiemRepository;
import com.ute.repository.HocPhanRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.service.LopHocPhanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LopHocPhanServiceImpl implements LopHocPhanService {

    private final LopHocPhanRepository lopHocPhanRepository;
    private final HocPhanRepository hocPhanRepository;
    private final DangKyHocPhanRepository dangKyHocPhanRepository;
    private final DiemRepository diemRepository;

    @Override
    public List<LopHocPhanResponse> findAll() {
        return lopHocPhanRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LopHocPhanResponse findById(String id) {
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lớp học phần không tồn tại"));
        return mapToResponse(lopHocPhan);
    }

    @Override
    public List<LopHocPhanResponse> findByHocPhan_MaHocPhan(String maHocPhan) {
        return lopHocPhanRepository.findByHocPhan_MaHocPhan(maHocPhan).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LopHocPhanResponse> findByGiangVien(String maGiangVien) {
        return lopHocPhanRepository.findByGiangVien(maGiangVien).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LopHocPhanResponse createLopHocPhan(LopHocPhanRequest request) {
        HocPhan hocPhan = hocPhanRepository.findById(request.getMaHocPhan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với mã: " + request.getMaHocPhan()));
        LopHocPhan lopHocPhan = new LopHocPhan();
        lopHocPhan.setMaLopHP(request.getMaLopHP());
        lopHocPhan.setHocPhan(hocPhan);
        lopHocPhan.setTenLopHP(request.getTenLopHP());
        lopHocPhan.setSoLuong(request.getSoLuong());
        lopHocPhan.setGiangVien(request.getGiangVien());
        lopHocPhan.setThoiGianBatDau(request.getThoiGianBatDau());
        lopHocPhan.setThoiGianKetThuc(request.getThoiGianKetThuc());
        lopHocPhan.setPhongHoc(request.getPhongHoc());
        lopHocPhan.setTrangThai(request.getTrangThai());
        return mapToResponse(lopHocPhanRepository.save(lopHocPhan));
    }

    @Override
    @Transactional
    public LopHocPhanResponse updateLopHocPhan(String id, LopHocPhanRequest request) {
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lớp học phần không tồn tại"));
        HocPhan hocPhan = hocPhanRepository.findById(request.getMaHocPhan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với mã: " + request.getMaHocPhan()));
        lopHocPhan.setHocPhan(hocPhan);
        lopHocPhan.setTenLopHP(request.getTenLopHP());
        lopHocPhan.setSoLuong(request.getSoLuong());
        lopHocPhan.setGiangVien(request.getGiangVien());
        lopHocPhan.setThoiGianBatDau(request.getThoiGianBatDau());
        lopHocPhan.setThoiGianKetThuc(request.getThoiGianKetThuc());
        lopHocPhan.setPhongHoc(request.getPhongHoc());
        lopHocPhan.setTrangThai(request.getTrangThai());
        return mapToResponse(lopHocPhanRepository.save(lopHocPhan));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lớp học phần không tồn tại"));
        // Xóa các bản ghi liên quan
        dangKyHocPhanRepository.findByLopHocPhan_MaLopHP(id).forEach(dk -> dangKyHocPhanRepository.delete(dk));
        diemRepository.findByLopHocPhan_MaLopHP(id).forEach(diem -> diemRepository.delete(diem));
        // TODO: Xóa ThoiKhoaBieu, LichSuDangKy nếu có repository
        lopHocPhanRepository.delete(lopHocPhan);
    }

    private LopHocPhanResponse mapToResponse(LopHocPhan lopHocPhan) {
        LopHocPhanResponse response = new LopHocPhanResponse();
        response.setMaLopHP(lopHocPhan.getMaLopHP());
        response.setTenLopHP(lopHocPhan.getTenLopHP());
        response.setSoLuong(lopHocPhan.getSoLuong());
        response.setGiangVien(lopHocPhan.getGiangVien());
        response.setThoiGianBatDau(lopHocPhan.getThoiGianBatDau());
        response.setThoiGianKetThuc(lopHocPhan.getThoiGianKetThuc());
        response.setPhongHoc(lopHocPhan.getPhongHoc());
        response.setTrangThai(lopHocPhan.getTrangThai());
        if (lopHocPhan.getHocPhan() != null) {
            response.setMaHocPhan(lopHocPhan.getHocPhan().getMaHocPhan());
            response.setTenHocPhan(lopHocPhan.getHocPhan().getTenHocPhan());
        }
        return response;
    }
} 
