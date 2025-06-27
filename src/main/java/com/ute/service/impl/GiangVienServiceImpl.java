package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.GiangVienRequest;
import com.ute.dto.response.GiangVienResponse;
import com.ute.entity.GiangVien;
import com.ute.entity.Khoa;
import com.ute.entity.LopHocPhan;
import com.ute.repository.GiangVienRepository;
import com.ute.repository.KhoaRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.service.GiangVienService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiangVienServiceImpl implements GiangVienService {
    private final GiangVienRepository giangVienRepository;
    private final KhoaRepository khoaRepository;
    private final LopHocPhanRepository lopHocPhanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiangVienResponse> getAllGiangVien() {
        return giangVienRepository.findAllWithRelations().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GiangVienResponse getGiangVienById(String id) {
        GiangVien giangVien = giangVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giảng viên với mã: " + id));
        return mapToResponse(giangVien);
    }

    @Override
    @Transactional
    public GiangVienResponse createGiangVien(GiangVienRequest request) {
        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));

        GiangVien giangVien = new GiangVien();
        giangVien.setMaGiangVien(request.getMaGiangVien());
        giangVien.setTenGiangVien(request.getTenGiangVien());
        giangVien.setEmail(request.getEmail());
        giangVien.setSoDienThoai(request.getSoDienThoai());
        giangVien.setKhoa(khoa);

        return mapToResponse(giangVienRepository.save(giangVien));
    }

    @Override
    @Transactional
    public GiangVienResponse updateGiangVien(String id, GiangVienRequest request) {
        GiangVien giangVien = giangVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giảng viên với mã: " + id));

        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));

        giangVien.setTenGiangVien(request.getTenGiangVien());
        giangVien.setEmail(request.getEmail());
        giangVien.setSoDienThoai(request.getSoDienThoai());
        giangVien.setKhoa(khoa);

        return mapToResponse(giangVienRepository.save(giangVien));
    }

    @Override
    @Transactional
    public void deleteGiangVien(String id) {
        GiangVien giangVien = giangVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giảng viên với mã: " + id));

        // Xóa tất cả các lớp học phần liên quan
        List<LopHocPhan> lopHocPhans = lopHocPhanRepository.findByGiangVien(id);
        for (LopHocPhan lopHocPhan : lopHocPhans) {
            lopHocPhan.setGiangVien(null);
            lopHocPhanRepository.save(lopHocPhan);
        }

        // Xóa giảng viên
        giangVienRepository.delete(giangVien);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiangVienResponse> getGiangVienByKhoa(String maKhoa) {
        return giangVienRepository.findByKhoa_MaKhoa(maKhoa).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GiangVienResponse mapToResponse(GiangVien giangVien) {
        GiangVienResponse response = new GiangVienResponse();
        response.setMaGiangVien(giangVien.getMaGiangVien());
        response.setTenGiangVien(giangVien.getTenGiangVien());
        response.setEmail(giangVien.getEmail());
        response.setSoDienThoai(giangVien.getSoDienThoai());
        response.setMaKhoa(giangVien.getKhoa().getMaKhoa());
        response.setTenKhoa(giangVien.getKhoa().getTenKhoa());
        return response;
    }
} 
