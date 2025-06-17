package com.ute.service.impl;

import com.ute.dto.response.HeSoDiemResponse;
import com.ute.dto.response.HocPhanResponse;
import com.ute.dto.response.KeHoachCoSinhVienResponse;
import com.ute.dto.response.LopHocPhanResponse;
import com.ute.entity.HeSoDiem;
import com.ute.entity.HocPhan;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.LopHocPhan;
import com.ute.repository.HeSoDiemRepository;
import com.ute.repository.HocPhanRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.service.HocPhanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HocPhanServiceImpl implements HocPhanService {

    private final HocPhanRepository hocPhanRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;
    private final HeSoDiemRepository heSoDiemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HocPhanResponse> getAllHocPhan() {
        List<HocPhan> hocPhans = hocPhanRepository.findAllWithRelations();
        return hocPhans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HocPhanResponse getHocPhanById(String id) {
        HocPhan hocPhan = hocPhanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy học phần với mã: " + id));
        return mapToResponse(hocPhan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HocPhanResponse> getHocPhanByKhoa(String maKhoa) {
        return hocPhanRepository.findByKhoa_MaKhoa(maKhoa).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HocPhan createHocPhan(HocPhan hocPhan) {
        return hocPhanRepository.save(hocPhan);
    }

    @Override
    @Transactional
    public HocPhan updateHocPhan(String id, HocPhan hocPhan) {
        HocPhan existingHocPhan = hocPhanRepository.findById(id).get();

        existingHocPhan.setTenHocPhan(hocPhan.getTenHocPhan());
        existingHocPhan.setSoTinChi(hocPhan.getSoTinChi());
        existingHocPhan.setKhoa(hocPhan.getKhoa());

        return hocPhanRepository.save(existingHocPhan);
    }

    @Override
    @Transactional
    public void deleteHocPhan(String id) {
        // Xử lý các lớp học phần liên quan
        List<LopHocPhan> lopHocPhans = lopHocPhanRepository.findByHocPhan_MaHocPhan(id);
        if (lopHocPhans != null) {
            lopHocPhans.forEach(lhp -> {
                // Xóa các bảng liên quan đến lớp học phần
                if (lhp.getDiems() != null) {
                    lhp.getDiems().forEach(diem -> diem.setLopHocPhan(null));
                }
                if (lhp.getDangKyHocPhans() != null) {
                    lhp.getDangKyHocPhans().forEach(dkhp -> dkhp.setLopHocPhan(null));
                }
                if (lhp.getThoiKhoaBieus() != null) {
                    lhp.getThoiKhoaBieus().forEach(tkb -> tkb.setLopHocPhan(null));
                }
                if (lhp.getLichSuDangKys() != null) {
                    lhp.getLichSuDangKys().forEach(lsdk -> lsdk.setLopHocPhan(null));
                }
            });
            lopHocPhanRepository.deleteAll(lopHocPhans);
        }

        // Xử lý kế hoạch có sinh viên liên quan
        List<KeHoachCoSinhVien> keHoachCoSinhViens = keHoachCoSinhVienRepository.findByHocPhan_MaHocPhan(id);
        if (keHoachCoSinhViens != null) {
            keHoachCoSinhVienRepository.deleteAll(keHoachCoSinhViens);
        }

        // Xử lý hệ số điểm liên quan
        HeSoDiem heSoDiem = heSoDiemRepository.findById(id).orElse(null);
        if (heSoDiem != null) {
            heSoDiemRepository.delete(heSoDiem);
        }

        // Xóa học phần

        hocPhanRepository.delete(hocPhanRepository.findById(id).get());
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

        // Map LopHocPhans
        if (hocPhan.getLopHocPhans() != null) {
            response.setLopHocPhans(hocPhan.getLopHocPhans().stream()
                    .map(this::mapToLopHocPhanResponse)
                    .collect(Collectors.toList()));
        }

        // Map KeHoachCoSinhViens
        if (hocPhan.getKeHoachCoSinhViens() != null) {
            response.setKeHoachCoSinhViens(hocPhan.getKeHoachCoSinhViens().stream()
                    .map(this::mapToKeHoachCoSinhVienResponse)
                    .collect(Collectors.toList()));
        }

        // Map HeSoDiem
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
