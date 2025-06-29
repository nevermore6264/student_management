package com.ute.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.dto.response.KeHoachHocTapResponse;
import com.ute.entity.Diem;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.SinhVien;
import com.ute.repository.DiemRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.KeHoachHocTapService;

@Service
public class KeHoachHocTapServiceImpl implements KeHoachHocTapService {

    @Autowired
    private KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;
    
    @Autowired
    private SinhVienRepository sinhVienRepository;
    
    @Autowired
    private DiemRepository diemRepository;

    @Override
    public KeHoachHocTapResponse getKeHoachHocTap(String maSinhVien) {
        KeHoachHocTapResponse response = new KeHoachHocTapResponse();
        
        // Lấy thông tin sinh viên
        SinhVien sinhVien = sinhVienRepository.findById(maSinhVien).orElse(null);
        if (sinhVien != null) {
            response.setMaSinhVien(sinhVien.getMaSinhVien());
            response.setHoTenSinhVien(sinhVien.getHoTenSinhVien());
        } else {
            response.setMaSinhVien(maSinhVien);
            response.setHoTenSinhVien("N/A");
        }
        
        // Lấy kế hoạch học tập của sinh viên
        List<KeHoachCoSinhVien> keHoachList = keHoachCoSinhVienRepository.findBySinhVien_MaSinhVien(maSinhVien);
        
        // Lấy điểm của sinh viên để so sánh
        List<Diem> diemList = diemRepository.findBySinhVien_MaSinhVien(maSinhVien);
        Map<String, Diem> diemMap = diemList.stream()
                .collect(Collectors.toMap(d -> d.getId().getMaLopHP(), d -> d));
        
        // Lấy danh sách đăng ký học phần để kiểm tra trạng thái
        List<String> dangKyHocPhan = diemList.stream()
                .filter(d -> d.getLopHocPhan() != null && d.getLopHocPhan().getHocPhan() != null)
                .map(d -> d.getLopHocPhan().getHocPhan().getMaHocPhan())
                .collect(Collectors.toList());
        
        List<KeHoachHocTapResponse.HocPhanKeHoach> keHoachChiTiet = new ArrayList<>();
        int tongSoTinChi = 0;
        
        for (KeHoachCoSinhVien keHoach : keHoachList) {
            if (keHoach.getHocPhan() != null) {
                KeHoachHocTapResponse.HocPhanKeHoach hocPhanKeHoach = new KeHoachHocTapResponse.HocPhanKeHoach();
                
                hocPhanKeHoach.setMaHocPhan(keHoach.getHocPhan().getMaHocPhan());
                hocPhanKeHoach.setTenHocPhan(keHoach.getHocPhan().getTenHocPhan());
                hocPhanKeHoach.setSoTinChi(keHoach.getHocPhan().getSoTinChi());
                hocPhanKeHoach.setHocKyDuKien(keHoach.getHocKyDuKien());
                hocPhanKeHoach.setNamHocDuKien(keHoach.getNamHocDuKien());
                
                // Tìm điểm của học phần này
                Diem diem = diemList.stream()
                        .filter(d -> d.getLopHocPhan() != null && 
                                   d.getLopHocPhan().getHocPhan() != null &&
                                   d.getLopHocPhan().getHocPhan().getMaHocPhan().equals(keHoach.getHocPhan().getMaHocPhan()))
                        .findFirst()
                        .orElse(null);
                
                if (diem != null && diem.getDiemTongKet() != null) {
                    // Đã học và có điểm
                    hocPhanKeHoach.setTrangThai("Đã học");
                    hocPhanKeHoach.setTrangThaiChiTiet("Hoàn thành");
                    hocPhanKeHoach.setDiemTongKet(diem.getDiemTongKet());
                    hocPhanKeHoach.setXepLoai(calculateXepLoai(diem.getDiemTongKet()));
                } else if (dangKyHocPhan.contains(keHoach.getHocPhan().getMaHocPhan())) {
                    // Đã đăng ký nhưng chưa có điểm
                    hocPhanKeHoach.setTrangThai("Đang học");
                    hocPhanKeHoach.setTrangThaiChiTiet("Đang học");
                    hocPhanKeHoach.setDiemTongKet(null);
                    hocPhanKeHoach.setXepLoai(null);
                } else {
                    // Chưa học
                    hocPhanKeHoach.setTrangThai("Chưa học");
                    hocPhanKeHoach.setTrangThaiChiTiet("Chưa đăng ký");
                    hocPhanKeHoach.setDiemTongKet(null);
                    hocPhanKeHoach.setXepLoai(null);
                }
                
                keHoachChiTiet.add(hocPhanKeHoach);
                tongSoTinChi += keHoach.getHocPhan().getSoTinChi();
            }
        }
        
        // Sắp xếp theo học kỳ và năm học
        keHoachChiTiet.sort((a, b) -> {
            int compareNamHoc = a.getNamHocDuKien().compareTo(b.getNamHocDuKien());
            if (compareNamHoc != 0) return compareNamHoc;
            return Integer.compare(a.getHocKyDuKien(), b.getHocKyDuKien());
        });
        
        response.setKeHoachTheoHocKy(keHoachChiTiet);
        response.setTongSoHocPhan(keHoachChiTiet.size());
        response.setTongSoTinChi(tongSoTinChi);
        
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
} 