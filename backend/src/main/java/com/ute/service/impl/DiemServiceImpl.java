package com.ute.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.response.DiemChiTietAllSinhVienResponse;
import com.ute.dto.response.DiemChiTietResponse;
import com.ute.dto.response.DiemResponse;
import com.ute.dto.response.DiemTongQuanAllSinhVienResponse;
import com.ute.dto.response.DiemTongQuanResponse;
import com.ute.dto.response.TongKetHocKyResponse;
import com.ute.entity.Diem;
import com.ute.entity.DiemId;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.KeHoachCoSinhVienId;
import com.ute.repository.DiemRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.service.DiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiemServiceImpl implements DiemService {
    private final DiemRepository diemRepository;
    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;

    @Override
    public List<DiemResponse> getAllDiem() {
        return diemRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DiemResponse getDiemById(String id) {
        return diemRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @Override
    public List<DiemResponse> getDiemBySinhVien(String maSinhVien) {
        return diemRepository.findBySinhVien_MaSinhVien(maSinhVien).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiemResponse> getDiemByLopHocPhan(String maLopHP) {
        return diemRepository.findByLopHocPhan_MaLopHP(maLopHP).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DiemResponse createDiem(DiemResponse diemDto) {
        Diem diem = new Diem();
        // Tạo DiemId từ maSinhVien và maLopHP
        DiemId id = new DiemId(diemDto.getMaSinhVien(), diemDto.getMaLopHP());
        diem.setId(id);
        // Map các trường khác nếu có
        // ...
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    @Transactional
    public DiemResponse updateDiem(String id, DiemResponse diemDto) {
        Diem diem = diemRepository.findById(id).orElseThrow();
        // ... cập nhật các trường từ diemDto
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    @Transactional
    public void deleteDiem(String id) {
        diemRepository.deleteById(id);
    }

    @Override
    public DiemTongQuanResponse getTongQuanDiem(String maSinhVien) {
        List<Diem> diemList = diemRepository.findBySinhVien_MaSinhVien(maSinhVien);
        DiemTongQuanResponse response = new DiemTongQuanResponse();
        
        // Group scores by semester
        Map<String, List<Diem>> diemByHocKy = diemList.stream()
                .collect(Collectors.groupingBy(d -> {
                    if (d.getLopHocPhan() != null && d.getLopHocPhan().getHocPhan() != null) {
                        // Get semester and academic year from KeHoachCoSinhVien
                        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository
                            .findById(new KeHoachCoSinhVienId(1, maSinhVien, d.getLopHocPhan().getHocPhan().getMaHocPhan()))
                            .orElse(null);
                        if (keHoach != null) {
                            return keHoach.getHocKyDuKien() + "_" + keHoach.getNamHocDuKien();
                        }
                    }
                    return "unknown";
                }));

        List<TongKetHocKyResponse> tongKetHocKyList = new ArrayList<>();
        double tongDiemTrungBinh = 0;
        int tongSoTinChi = 0;

        for (Map.Entry<String, List<Diem>> entry : diemByHocKy.entrySet()) {
            if (entry.getKey().equals("unknown")) continue;
            
            String[] hocKyInfo = entry.getKey().split("_");
            String hocKy = hocKyInfo[0];
            String namHoc = hocKyInfo[1];

            List<Diem> diemHocKy = entry.getValue();
            double diemTrungBinh = 0;
            int soTinChi = 0;
            int tongTinChi = 0;

            for (Diem diem : diemHocKy) {
                if (diem.getLopHocPhan() != null && diem.getLopHocPhan().getHocPhan() != null) {
                    int tinChi = diem.getLopHocPhan().getHocPhan().getSoTinChi();
                    tongTinChi += tinChi;
                    diemTrungBinh += diem.getDiemTongKet() * tinChi;
                    soTinChi += tinChi;
                }
            }

            if (tongTinChi > 0) {
                diemTrungBinh /= tongTinChi;
            }

            TongKetHocKyResponse tongKetHocKy = new TongKetHocKyResponse();
            tongKetHocKy.setHocKy(Integer.parseInt(hocKy));
            tongKetHocKy.setNamHoc(namHoc);
            tongKetHocKy.setDiemTrungBinh(diemTrungBinh);
            tongKetHocKy.setTongSoTinChi(soTinChi);
            tongKetHocKy.setXepLoai(calculateXepLoai(diemTrungBinh));

            tongKetHocKyList.add(tongKetHocKy);
            tongDiemTrungBinh += diemTrungBinh * soTinChi;
            tongSoTinChi += soTinChi;
        }

        if (tongSoTinChi > 0) {
            tongDiemTrungBinh /= tongSoTinChi;
        }

        response.setTongKetTheoHocKy(tongKetHocKyList);
        response.setDiemTrungBinh(tongDiemTrungBinh);
        response.setTongSoTinChi(tongSoTinChi);
        response.setSoHocPhan(diemList.size());
        response.setXepLoai(calculateXepLoai(tongDiemTrungBinh));

        return response;
    }

    private String calculateXepLoai(double diem) {
        if (diem >= 9.0) return "Xuất sắc";
        if (diem >= 8.0) return "Giỏi";
        if (diem >= 7.0) return "Khá";
        if (diem >= 6.0) return "Trung bình khá";
        if (diem >= 5.0) return "Trung bình";
        return "Yếu";
    }

    @Override
    public List<DiemChiTietResponse> getChiTietDiem(String maSinhVien) {
        List<Diem> diemList = diemRepository.findBySinhVien_MaSinhVien(maSinhVien);
        return diemList.stream()
                .map(diem -> {
                    DiemChiTietResponse response = new DiemChiTietResponse();
                    if (diem.getLopHocPhan() != null && diem.getLopHocPhan().getHocPhan() != null) {
                        response.setTenMon(diem.getLopHocPhan().getHocPhan().getTenHocPhan());
                        response.setSoTinChi(diem.getLopHocPhan().getHocPhan().getSoTinChi());
                        
                        // Get semester and academic year from KeHoachCoSinhVien
                        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository
                            .findById(new KeHoachCoSinhVienId(1, maSinhVien, diem.getLopHocPhan().getHocPhan().getMaHocPhan()))
                            .orElse(null);
                        if (keHoach != null) {
                            response.setHocKy(String.valueOf(keHoach.getHocKyDuKien()));
                            response.setNamHoc(keHoach.getNamHocDuKien());
                        }
                    }
                    response.setDiem(diem.getDiemTongKet());
                    response.setXepLoai(calculateXepLoai(diem.getDiemTongKet()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DiemTongQuanAllSinhVienResponse> getTongQuanTatCaSinhVien() {
        List<Diem> diemList = diemRepository.findAll();
        Map<String, List<Diem>> diemBySinhVien = diemList.stream()
                .collect(Collectors.groupingBy(d -> d.getId().getMaSinhVien()));

        return diemBySinhVien.entrySet().stream()
                .map(entry -> {
                    String maSinhVien = entry.getKey();
                    List<Diem> diemSinhVien = entry.getValue();
                    
                    DiemTongQuanAllSinhVienResponse response = new DiemTongQuanAllSinhVienResponse();
                    response.setMaSinhVien(maSinhVien);
                    
                    if (!diemSinhVien.isEmpty() && diemSinhVien.get(0).getSinhVien() != null) {
                        response.setHoTen(diemSinhVien.get(0).getSinhVien().getHoTenSinhVien());
                    }

                    // Group scores by semester
                    Map<String, List<Diem>> diemByHocKy = diemSinhVien.stream()
                            .collect(Collectors.groupingBy(d -> {
                                if (d.getLopHocPhan() != null && d.getLopHocPhan().getHocPhan() != null) {
                                    KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository
                                        .findById(new KeHoachCoSinhVienId(1, maSinhVien, d.getLopHocPhan().getHocPhan().getMaHocPhan()))
                                        .orElse(null);
                                    if (keHoach != null) {
                                        return keHoach.getHocKyDuKien() + "_" + keHoach.getNamHocDuKien();
                                    }
                                }
                                return "unknown";
                            }));

                    List<TongKetHocKyResponse> tongKetHocKyList = new ArrayList<>();
                    double tongDiemTrungBinh = 0;
                    int tongSoTinChi = 0;

                    for (Map.Entry<String, List<Diem>> hocKyEntry : diemByHocKy.entrySet()) {
                        if (hocKyEntry.getKey().equals("unknown")) continue;
                        
                        String[] hocKyInfo = hocKyEntry.getKey().split("_");
                        String hocKy = hocKyInfo[0];
                        String namHoc = hocKyInfo[1];

                        List<Diem> diemHocKy = hocKyEntry.getValue();
                        double diemTrungBinh = 0;
                        int soTinChi = 0;
                        int tongTinChi = 0;

                        for (Diem diem : diemHocKy) {
                            if (diem.getLopHocPhan() != null && diem.getLopHocPhan().getHocPhan() != null) {
                                int tinChi = diem.getLopHocPhan().getHocPhan().getSoTinChi();
                                tongTinChi += tinChi;
                                diemTrungBinh += diem.getDiemTongKet() * tinChi;
                                soTinChi += tinChi;
                            }
                        }

                        if (tongTinChi > 0) {
                            diemTrungBinh /= tongTinChi;
                        }

                        TongKetHocKyResponse tongKetHocKy = new TongKetHocKyResponse();
                        tongKetHocKy.setHocKy(Integer.parseInt(hocKy));
                        tongKetHocKy.setNamHoc(namHoc);
                        tongKetHocKy.setDiemTrungBinh(diemTrungBinh);
                        tongKetHocKy.setTongSoTinChi(soTinChi);
                        tongKetHocKy.setXepLoai(calculateXepLoai(diemTrungBinh));

                        tongKetHocKyList.add(tongKetHocKy);
                        tongDiemTrungBinh += diemTrungBinh * soTinChi;
                        tongSoTinChi += soTinChi;
                    }

                    if (tongSoTinChi > 0) {
                        tongDiemTrungBinh /= tongSoTinChi;
                    }

                    response.setTongSoTinChi(tongSoTinChi);
                    response.setDiemTrungBinh(tongDiemTrungBinh);
                    response.setXepLoai(calculateXepLoai(tongDiemTrungBinh));
                    response.setSoHocPhan(diemSinhVien.size());
                    response.setTongKetTheoHocKy(tongKetHocKyList);

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DiemChiTietAllSinhVienResponse> getChiTietTatCaSinhVien() {
        List<Diem> diemList = diemRepository.findAll();
        Map<String, List<Diem>> diemBySinhVien = diemList.stream()
                .collect(Collectors.groupingBy(d -> d.getId().getMaSinhVien()));

        return diemBySinhVien.entrySet().stream()
                .map(entry -> {
                    String maSinhVien = entry.getKey();
                    List<Diem> diemSinhVien = entry.getValue();
                    
                    DiemChiTietAllSinhVienResponse response = new DiemChiTietAllSinhVienResponse();
                    response.setMaSinhVien(maSinhVien);
                    
                    if (!diemSinhVien.isEmpty() && diemSinhVien.get(0).getSinhVien() != null) {
                        response.setHoTen(diemSinhVien.get(0).getSinhVien().getHoTenSinhVien());
                    }

                    if (!diemSinhVien.isEmpty() && diemSinhVien.get(0).getLopHocPhan() != null && 
                        diemSinhVien.get(0).getLopHocPhan().getHocPhan() != null) {
                        response.setTenMon(diemSinhVien.get(0).getLopHocPhan().getHocPhan().getTenHocPhan());
                        response.setSoTinChi(diemSinhVien.get(0).getLopHocPhan().getHocPhan().getSoTinChi());
                        
                        // Get semester and academic year from KeHoachCoSinhVien
                        KeHoachCoSinhVien keHoach = keHoachCoSinhVienRepository
                            .findById(new KeHoachCoSinhVienId(1, maSinhVien, diemSinhVien.get(0).getLopHocPhan().getHocPhan().getMaHocPhan()))
                            .orElse(null);
                        if (keHoach != null) {
                            response.setHocKy(String.valueOf(keHoach.getHocKyDuKien()));
                            response.setNamHoc(keHoach.getNamHocDuKien());
                        }
                    }

                    if (!diemSinhVien.isEmpty()) {
                        response.setDiem(diemSinhVien.get(0).getDiemTongKet());
                        response.setXepLoai(calculateXepLoai(diemSinhVien.get(0).getDiemTongKet()));
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    private DiemResponse mapToResponse(Diem diem) {
        DiemResponse dto = new DiemResponse();
        // Lấy mã điểm là ghép từ mã sinh viên + mã lớp học phần (hoặc để null nếu không cần)
        dto.setMaDiem(diem.getId() != null ? diem.getId().getMaSinhVien() + "-" + diem.getId().getMaLopHP() : null);
        dto.setMaSinhVien(diem.getId() != null ? diem.getId().getMaSinhVien() : null);
        dto.setMaLopHP(diem.getId() != null ? diem.getId().getMaLopHP() : null);
        if (diem.getSinhVien() != null) {
            dto.setHoTenSinhVien(diem.getSinhVien().getHoTenSinhVien());
        }
        if (diem.getLopHocPhan() != null) {
            dto.setTenLopHP(diem.getLopHocPhan().getTenLopHP());
            if (diem.getLopHocPhan().getHocPhan() != null) {
                dto.setTenMon(diem.getLopHocPhan().getHocPhan().getTenHocPhan());
                dto.setSoTinChi(diem.getLopHocPhan().getHocPhan().getSoTinChi() != null ? diem.getLopHocPhan().getHocPhan().getSoTinChi() : 0);
            }
        }
        dto.setDiem(diem.getDiemTongKet() != null ? diem.getDiemTongKet() : 0);
        // TODO: Map các trường xếp loại, học kỳ, năm học nếu có
        return dto;
    }
} 
