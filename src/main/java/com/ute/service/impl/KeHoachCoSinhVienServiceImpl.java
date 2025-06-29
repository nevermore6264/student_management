package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.KeHoachCoSinhVienRequest;
import com.ute.dto.response.KeHoachCoSinhVienResponse;
import com.ute.entity.HocPhan;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.KeHoachCoSinhVienId;
import com.ute.entity.SinhVien;
import com.ute.repository.HocPhanRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.KeHoachCoSinhVienService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeHoachCoSinhVienServiceImpl implements KeHoachCoSinhVienService {

    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;
    private final SinhVienRepository sinhVienRepository;
    private final HocPhanRepository hocPhanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachCoSinhVienResponse> getAllKeHoach() {
        return keHoachCoSinhVienRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachCoSinhVienResponse> getKeHoachBySinhVien(String maSinhVien) {
        return keHoachCoSinhVienRepository.findBySinhVien_MaSinhVien(maSinhVien).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachCoSinhVienResponse> getKeHoachByHocPhan(String maHocPhan) {
        return keHoachCoSinhVienRepository.findByHocPhan_MaHocPhan(maHocPhan).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KeHoachCoSinhVienResponse getKeHoachById(Integer maKeHoach, String maSinhVien, String maHocPhan) {
        KeHoachCoSinhVienId id = new KeHoachCoSinhVienId(maKeHoach, maSinhVien, maHocPhan);
        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kế hoạch học tập với ID: " + id));
        return mapToResponse(keHoach);
    }

    @Override
    @Transactional
    public KeHoachCoSinhVienResponse createKeHoach(KeHoachCoSinhVienRequest request) {
        // Kiểm tra sinh viên tồn tại
        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSinhVien())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sinh viên với mã: " + request.getMaSinhVien()));

        // Kiểm tra học phần tồn tại
        HocPhan hocPhan = hocPhanRepository.findById(request.getMaHocPhan())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học phần với mã: " + request.getMaHocPhan()));

        // Tạo ID mới cho kế hoạch
        Integer maKeHoach = generateNewMaKeHoach(request.getMaSinhVien());

        // Tạo kế hoạch mới
        KeHoachCoSinhVien keHoach = new KeHoachCoSinhVien();
        keHoach.setId(new KeHoachCoSinhVienId(maKeHoach, request.getMaSinhVien(), request.getMaHocPhan()));
        keHoach.setSinhVien(sinhVien);
        keHoach.setHocPhan(hocPhan);
        keHoach.setHocKyDuKien(request.getHocKyDuKien());
        keHoach.setNamHocDuKien(request.getNamHocDuKien());
        keHoach.setTrangThai(request.getTrangThai());

        KeHoachCoSinhVien savedKeHoach = keHoachCoSinhVienRepository.save(keHoach);
        return mapToResponse(savedKeHoach);
    }

    @Override
    @Transactional
    public KeHoachCoSinhVienResponse updateKeHoach(Integer maKeHoach, String maSinhVien, String maHocPhan, KeHoachCoSinhVienRequest request) {
        KeHoachCoSinhVienId id = new KeHoachCoSinhVienId(maKeHoach, maSinhVien, maHocPhan);
        KeHoachCoSinhVien existingKeHoach = keHoachCoSinhVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kế hoạch học tập với ID: " + id));

        // Cập nhật thông tin
        existingKeHoach.setHocKyDuKien(request.getHocKyDuKien());
        existingKeHoach.setNamHocDuKien(request.getNamHocDuKien());
        existingKeHoach.setTrangThai(request.getTrangThai());

        KeHoachCoSinhVien updatedKeHoach = keHoachCoSinhVienRepository.save(existingKeHoach);
        return mapToResponse(updatedKeHoach);
    }

    @Override
    @Transactional
    public void deleteKeHoach(Integer maKeHoach, String maSinhVien, String maHocPhan) {
        KeHoachCoSinhVienId id = new KeHoachCoSinhVienId(maKeHoach, maSinhVien, maHocPhan);
        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kế hoạch học tập với ID: " + id));
        
        keHoachCoSinhVienRepository.delete(keHoach);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachCoSinhVienResponse> getKeHoachByHocKyAndNamHoc(Integer hocKy, String namHoc) {
        return keHoachCoSinhVienRepository.findAll().stream()
                .filter(keHoach -> keHoach.getHocKyDuKien().equals(hocKy) && keHoach.getNamHocDuKien().equals(namHoc))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachCoSinhVienResponse> getKeHoachByTrangThai(Integer trangThai) {
        return keHoachCoSinhVienRepository.findAll().stream()
                .filter(keHoach -> keHoach.getTrangThai().equals(trangThai))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private KeHoachCoSinhVienResponse mapToResponse(KeHoachCoSinhVien keHoach) {
        KeHoachCoSinhVienResponse response = new KeHoachCoSinhVienResponse();
        response.setMaKeHoach(keHoach.getId().getMaKeHoach());
        response.setMaSinhVien(keHoach.getSinhVien().getMaSinhVien());
        response.setHoTenSinhVien(keHoach.getSinhVien().getHoTenSinhVien());
        response.setMaHocPhan(keHoach.getHocPhan().getMaHocPhan());
        response.setTenHocPhan(keHoach.getHocPhan().getTenHocPhan());
        response.setSoTinChi(keHoach.getHocPhan().getSoTinChi());
        response.setHocKyDuKien(keHoach.getHocKyDuKien());
        response.setNamHocDuKien(keHoach.getNamHocDuKien());
        response.setTrangThai(keHoach.getTrangThai());
        response.setTrangThaiText(getTrangThaiText(keHoach.getTrangThai()));
        return response;
    }

    private String getTrangThaiText(Integer trangThai) {
        switch (trangThai) {
            case 0:
                return "Chưa học";
            case 1:
                return "Đã học";
            case 2:
                return "Đang học";
            default:
                return "Không xác định";
        }
    }

    private Integer generateNewMaKeHoach(String maSinhVien) {
        List<KeHoachCoSinhVien> existingKeHoach = keHoachCoSinhVienRepository.findBySinhVien_MaSinhVien(maSinhVien);
        if (existingKeHoach.isEmpty()) {
            return 1;
        }
        return existingKeHoach.stream()
                .mapToInt(keHoach -> keHoach.getId().getMaKeHoach())
                .max()
                .orElse(0) + 1;
    }
} 