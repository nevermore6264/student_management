package com.ute.service.impl;

import com.ute.dto.request.KhoaRequest;
import com.ute.dto.response.KhoaResponse;
import com.ute.entity.Khoa;
import com.ute.repository.KhoaRepository;
import com.ute.service.KhoaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoaServiceImpl implements KhoaService {
    private final KhoaRepository khoaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> getAllKhoa() {
        return khoaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KhoaResponse getKhoaById(String id) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));
        return mapToResponse(khoa);
    }

    @Override
    @Transactional
    public KhoaResponse createKhoa(KhoaRequest request) {
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(request.getMaKhoa());
        khoa.setTenKhoa(request.getTenKhoa());
        khoa.setSoDienThoai(request.getSoDienThoai());
        khoa.setEmail(request.getEmail());
        khoa.setDiaChi(request.getDiaChi());
        khoa.setMaTruong(request.getMaTruong());
        khoa.setTrangThai(request.getTrangThai());
        return mapToResponse(khoaRepository.save(khoa));
    }

    @Override
    @Transactional
    public KhoaResponse updateKhoa(String id, KhoaRequest request) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));
        khoa.setTenKhoa(request.getTenKhoa());
        khoa.setSoDienThoai(request.getSoDienThoai());
        khoa.setEmail(request.getEmail());
        khoa.setDiaChi(request.getDiaChi());
        khoa.setMaTruong(request.getMaTruong());
        khoa.setTrangThai(request.getTrangThai());
        return mapToResponse(khoaRepository.save(khoa));
    }

    @Override
    @Transactional
    public void deleteKhoa(String id) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));

        // Xử lý các lớp liên quan
        if (khoa.getLops() != null) {
            khoa.getLops().forEach(lop -> lop.setKhoa(null));
        }

        // Xử lý các giảng viên liên quan
        if (khoa.getGiangViens() != null) {
            khoa.getGiangViens().forEach(gv -> gv.setKhoa(null));
        }

        // Xử lý các học phần liên quan
        if (khoa.getHocPhans() != null) {
            khoa.getHocPhans().forEach(hp -> hp.setKhoa(null));
        }

        // Xử lý các đợt đăng ký liên quan
        if (khoa.getDotDangKys() != null) {
            khoa.getDotDangKys().forEach(ddk -> ddk.setKhoa(null));
        }

        // Lưu lại các thay đổi (nếu cần)
        // (Có thể cần inject thêm các repository tương ứng để save các entity này)

        // Xóa khoa
        khoaRepository.delete(khoa);
    }

    private KhoaResponse mapToResponse(Khoa khoa) {
        KhoaResponse response = new KhoaResponse();
        response.setMaKhoa(khoa.getMaKhoa());
        response.setTenKhoa(khoa.getTenKhoa());
        response.setSoDienThoai(khoa.getSoDienThoai());
        response.setEmail(khoa.getEmail());
        response.setDiaChi(khoa.getDiaChi());
        response.setMaTruong(khoa.getMaTruong());
        response.setTrangThai(khoa.getTrangThai());
        return response;
    }
} 
