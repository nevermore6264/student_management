package com.ute.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.KeHoachCoSinhVienRequest;
import com.ute.dto.response.KeHoachChiTietResponse;
import com.ute.dto.response.KeHoachCoSinhVienResponse;
import com.ute.entity.Diem;
import com.ute.entity.HocPhan;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.KeHoachCoSinhVienId;
import com.ute.entity.SinhVien;
import com.ute.repository.DiemRepository;
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
    
    @Autowired
    private DiemRepository diemRepository;

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
    @Transactional(readOnly = true)
    public KeHoachChiTietResponse getKeHoachChiTiet(Integer maKeHoach, String maSinhVien, String maHocPhan) {
        KeHoachCoSinhVienId id = new KeHoachCoSinhVienId(maKeHoach, maSinhVien, maHocPhan);
        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kế hoạch học tập với ID: " + id));
        
        return mapToChiTietResponse(keHoach);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeHoachChiTietResponse> getAllKeHoachChiTietBySinhVien(String maSinhVien) {
        List<KeHoachCoSinhVien> keHoachList = keHoachCoSinhVienRepository.findBySinhVien_MaSinhVien(maSinhVien);
        return keHoachList.stream()
                .map(this::mapToChiTietResponse)
                .collect(Collectors.toList());
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

    private KeHoachChiTietResponse mapToChiTietResponse(KeHoachCoSinhVien keHoach) {
        KeHoachChiTietResponse response = new KeHoachChiTietResponse();
        
        // Thông tin kế hoạch
        response.setMaKeHoach(keHoach.getId().getMaKeHoach());
        response.setHocKyDuKien(keHoach.getHocKyDuKien());
        response.setNamHocDuKien(keHoach.getNamHocDuKien());
        response.setTrangThai(keHoach.getTrangThai());
        response.setTrangThaiText(getTrangThaiText(keHoach.getTrangThai()));
        
        // Thông tin sinh viên
        SinhVien sinhVien = keHoach.getSinhVien();
        response.setMaSinhVien(sinhVien.getMaSinhVien());
        response.setHoTenSinhVien(sinhVien.getHoTenSinhVien());
        response.setEmail(sinhVien.getEmail());
        response.setSoDienThoai(sinhVien.getSoDienThoai());
        
        if (sinhVien.getLop() != null) {
            response.setMaLop(sinhVien.getLop().getMaLop());
            response.setTenLop(sinhVien.getLop().getTenLop());
            
            if (sinhVien.getLop().getKhoa() != null) {
                response.setMaKhoa(sinhVien.getLop().getKhoa().getMaKhoa());
                response.setTenKhoa(sinhVien.getLop().getKhoa().getTenKhoa());
            }
        }
        
        // Thông tin học phần
        HocPhan hocPhan = keHoach.getHocPhan();
        response.setMaHocPhan(hocPhan.getMaHocPhan());
        response.setTenHocPhan(hocPhan.getTenHocPhan());
        response.setSoTinChi(hocPhan.getSoTinChi());
        
        if (hocPhan.getKhoa() != null) {
            response.setMaKhoaHocPhan(hocPhan.getKhoa().getMaKhoa());
            response.setTenKhoaHocPhan(hocPhan.getKhoa().getTenKhoa());
        }
        
        // Thông tin tiến độ học tập
        checkStudyProgress(keHoach, response);
        
        return response;
    }

    private void checkStudyProgress(KeHoachCoSinhVien keHoach, KeHoachChiTietResponse response) {
        String maSinhVien = keHoach.getSinhVien().getMaSinhVien();
        String maHocPhan = keHoach.getHocPhan().getMaHocPhan();
        
        // Kiểm tra xem sinh viên có đăng ký học phần này không
        List<Diem> diemList = diemRepository.findBySinhVien_MaSinhVien(maSinhVien);
        boolean daDangKy = diemList.stream()
                .anyMatch(diem -> diem.getLopHocPhan() != null && 
                                diem.getLopHocPhan().getHocPhan() != null &&
                                diem.getLopHocPhan().getHocPhan().getMaHocPhan().equals(maHocPhan));
        
        response.setDaDangKy(daDangKy);
        
        // Kiểm tra điểm
        if (daDangKy) {
            Optional<Diem> diemOpt = diemList.stream()
                    .filter(diem -> diem.getLopHocPhan() != null && 
                                   diem.getLopHocPhan().getHocPhan() != null &&
                                   diem.getLopHocPhan().getHocPhan().getMaHocPhan().equals(maHocPhan))
                    .findFirst();
            
            if (diemOpt.isPresent()) {
                Diem diem = diemOpt.get();
                response.setCoDiem(true);
                response.setDiemTongKet(diem.getDiemTongKet());
                response.setXepLoai(calculateXepLoai(diem.getDiemTongKet()));
                
                // Tạo ghi chú dựa trên điểm
                if (diem.getDiemTongKet() >= 8.0) {
                    response.setGhiChu("Xuất sắc - Hoàn thành tốt");
                } else if (diem.getDiemTongKet() >= 6.5) {
                    response.setGhiChu("Khá - Hoàn thành đạt yêu cầu");
                } else if (diem.getDiemTongKet() >= 5.0) {
                    response.setGhiChu("Trung bình - Cần cải thiện");
                } else {
                    response.setGhiChu("Yếu - Cần học lại");
                }
            } else {
                response.setCoDiem(false);
                response.setGhiChu("Đã đăng ký nhưng chưa có điểm");
            }
        } else {
            response.setCoDiem(false);
            response.setGhiChu("Chưa đăng ký học phần này");
        }
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

    private String calculateXepLoai(Float diem) {
        if (diem == null) return "Chưa có điểm";
        if (diem >= 9.0) return "Xuất sắc";
        if (diem >= 8.0) return "Giỏi";
        if (diem >= 7.0) return "Khá";
        if (diem >= 6.5) return "Trung bình khá";
        if (diem >= 5.0) return "Trung bình";
        if (diem >= 4.0) return "Yếu";
        return "Kém";
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