package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.HocPhanRequest;
import com.ute.dto.response.HeSoDiemResponse;
import com.ute.dto.response.HocPhanResponse;
import com.ute.dto.response.KeHoachCoSinhVienResponse;
import com.ute.dto.response.LopHocPhanResponse;
import com.ute.entity.HeSoDiem;
import com.ute.entity.HocPhan;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.Khoa;
import com.ute.entity.LopHocPhan;
import com.ute.repository.HeSoDiemRepository;
import com.ute.repository.HocPhanRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.KhoaRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.service.HocPhanService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HocPhanServiceImpl implements HocPhanService {

    private final HocPhanRepository hocPhanRepository;
    private final KhoaRepository khoaRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;
    private final HeSoDiemRepository heSoDiemRepository;

    @Override
    public List<HocPhanResponse> getAllHocPhan() {
        List<HocPhan> hocPhans = hocPhanRepository.findAllWithRelations();
        return hocPhans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HocPhanResponse getHocPhanById(String id) {
        HocPhan hocPhan = hocPhanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học phần với mã: " + id));
        return mapToResponse(hocPhan);
    }

    @Override
    public List<HocPhanResponse> getHocPhanByKhoa(String maKhoa) {
        List<HocPhan> hocPhans = hocPhanRepository.findByKhoa_MaKhoa(maKhoa);
        return hocPhans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HocPhanResponse createHocPhan(HocPhanRequest request) {
        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));

        HocPhan hocPhan = new HocPhan();
        hocPhan.setMaHocPhan(request.getMaHocPhan());
        hocPhan.setTenHocPhan(request.getTenHocPhan());
        hocPhan.setSoTinChi(request.getSoTinChi());
        hocPhan.setKhoa(khoa);

        HocPhan savedHocPhan = hocPhanRepository.save(hocPhan);
        return mapToResponse(savedHocPhan);
    }

    @Override
    @Transactional
    public HocPhanResponse updateHocPhan(String id, HocPhanRequest request) {
        HocPhan existingHocPhan = hocPhanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học phần với mã: " + id));

        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));

        existingHocPhan.setTenHocPhan(request.getTenHocPhan());
        existingHocPhan.setSoTinChi(request.getSoTinChi());
        existingHocPhan.setKhoa(khoa);

        HocPhan updatedHocPhan = hocPhanRepository.save(existingHocPhan);
        return mapToResponse(updatedHocPhan);
    }

    @Override
    @Transactional
    public void deleteHocPhan(String id) {
        // Xử lý các lớp học phần liên quan
        List<LopHocPhan> lopHocPhans = lopHocPhanRepository.findByHocPhan_MaHocPhan(id);
        for (LopHocPhan lopHocPhan : lopHocPhans) {
            lopHocPhan.setHocPhan(null);
            lopHocPhanRepository.save(lopHocPhan);
        }

        // Xử lý các kế hoạch có sinh viên liên quan
        List<KeHoachCoSinhVien> keHoachCoSinhViens = keHoachCoSinhVienRepository.findByHocPhan_MaHocPhan(id);
        for (KeHoachCoSinhVien keHoach : keHoachCoSinhViens) {
            keHoach.setHocPhan(null);
            keHoachCoSinhVienRepository.save(keHoach);
        }

        // Xóa học phần
        hocPhanRepository.deleteById(id);
    }

    private HocPhanResponse mapToResponse(HocPhan hocPhan) {
        HocPhanResponse response = new HocPhanResponse();
        response.setMaHocPhan(hocPhan.getMaHocPhan());
        response.setTenHocPhan(hocPhan.getTenHocPhan());
        response.setSoTinChi(hocPhan.getSoTinChi());
        
        if (hocPhan.getKhoa() != null) {
            response.setMaKhoa(hocPhan.getKhoa().getMaKhoa());
            response.setTenKhoa(hocPhan.getKhoa().getTenKhoa());
        }

        // Map các lớp học phần
        List<LopHocPhan> lopHocPhans = lopHocPhanRepository.findByHocPhan_MaHocPhan(hocPhan.getMaHocPhan());
        response.setLopHocPhans(lopHocPhans.stream()
                .map(this::mapToLopHocPhanResponse)
                .collect(Collectors.toList()));

        // Map các kế hoạch có sinh viên
        List<KeHoachCoSinhVien> keHoachCoSinhViens = keHoachCoSinhVienRepository.findByHocPhan_MaHocPhan(hocPhan.getMaHocPhan());
        response.setKeHoachCoSinhViens(keHoachCoSinhViens.stream()
                .map(this::mapToKeHoachCoSinhVienResponse)
                .collect(Collectors.toList()));

        // Map hệ số điểm
        if (hocPhan.getHeSoDiem() != null) {
            response.setHeSoDiem(mapToHeSoDiemResponse(hocPhan.getHeSoDiem()));
        }

        return response;
    }

    private LopHocPhanResponse mapToLopHocPhanResponse(LopHocPhan lopHocPhan) {
        LopHocPhanResponse response = new LopHocPhanResponse();
        response.setMaLopHP(lopHocPhan.getMaLopHP());
        response.setTenLopHP(lopHocPhan.getTenLopHP());
        response.setSoLuong(lopHocPhan.getSoLuong());
        response.setGiangVien(lopHocPhan.getGiangVien());
        response.setThoiGianBatDau(lopHocPhan.getThoiGianBatDau());
        response.setThoiGianKetThuc(lopHocPhan.getThoiGianKetThuc());
        response.setPhongHoc(lopHocPhan.getPhongHoc());
        response.setTrangThai(lopHocPhan.getTrangThai());
        return response;
    }

    private KeHoachCoSinhVienResponse mapToKeHoachCoSinhVienResponse(KeHoachCoSinhVien keHoach) {
        KeHoachCoSinhVienResponse response = new KeHoachCoSinhVienResponse();
        response.setMaSinhVien(keHoach.getSinhVien().getMaSinhVien());
        response.setHoTenSinhVien(keHoach.getSinhVien().getHoTenSinhVien());
        response.setHocKyDuKien(keHoach.getHocKyDuKien());
        response.setNamHocDuKien(keHoach.getNamHocDuKien());
        response.setTrangThai(keHoach.getTrangThai());
        return response;
    }

    private HeSoDiemResponse mapToHeSoDiemResponse(HeSoDiem heSoDiem) {
        HeSoDiemResponse response = new HeSoDiemResponse();
        response.setHeSoChuyenCan(heSoDiem.getHeSoChuyenCan());
        response.setHeSoGiuaKy(heSoDiem.getHeSoGiuaKy());
        response.setHeSoCuoiKy(heSoDiem.getHeSoCuoiKy());
        return response;
    }
} 
