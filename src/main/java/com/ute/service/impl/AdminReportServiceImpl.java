package com.ute.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.response.AdminReportResponse;
import com.ute.entity.DangKyHocPhan;
import com.ute.entity.Diem;
import com.ute.entity.GiangVien;
import com.ute.entity.HocPhan;
import com.ute.entity.Khoa;
import com.ute.entity.Lop;
import com.ute.entity.LopHocPhan;
import com.ute.entity.SinhVien;
import com.ute.repository.DangKyHocPhanRepository;
import com.ute.repository.DiemRepository;
import com.ute.repository.GiangVienRepository;
import com.ute.repository.HocPhanRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.KhoaRepository;
import com.ute.repository.LopHocPhanRepository;
import com.ute.repository.LopRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.AdminReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReportServiceImpl implements AdminReportService {

    private final SinhVienRepository sinhVienRepository;
    private final GiangVienRepository giangVienRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    private final HocPhanRepository hocPhanRepository;
    private final KhoaRepository khoaRepository;
    private final LopRepository lopRepository;
    private final DiemRepository diemRepository;
    private final DangKyHocPhanRepository dangKyHocPhanRepository;
    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminReportResponse getTongHopBaoCao() {
        AdminReportResponse response = new AdminReportResponse();
        
        // 1. Thống kê tổng quan
        response.setThongKeTongQuan(calculateThongKeTongQuan());
        
        // 2. Thống kê theo khoa
        response.setThongKeTheoKhoa(calculateThongKeTheoKhoa());
        
        // 3. Phân bố điểm
        response.setPhanBoDiem(calculatePhanBoDiem());
        
        // 4. Top giảng viên
        response.setTopGiangVien(calculateTopGiangVien());
        
        // 5. Top sinh viên
        response.setTopSinhVien(calculateTopSinhVien());
        
        // 6. Thống kê đăng ký
        response.setThongKeDangKy(calculateThongKeDangKy());
        
        // 7. Thống kê theo học kỳ
        response.setThongKeTheoHocKy(calculateThongKeTheoHocKy());
        
        return response;
    }

    private AdminReportResponse.ThongKeTongQuan calculateThongKeTongQuan() {
        AdminReportResponse.ThongKeTongQuan thongKe = new AdminReportResponse.ThongKeTongQuan();
        
        List<SinhVien> allSinhVien = sinhVienRepository.findAll();
        List<GiangVien> allGiangVien = giangVienRepository.findAll();
        List<LopHocPhan> allLopHocPhan = lopHocPhanRepository.findAll();
        List<HocPhan> allHocPhan = hocPhanRepository.findAll();
        List<Khoa> allKhoa = khoaRepository.findAll();
        List<Lop> allLop = lopRepository.findAll();
        List<Diem> allDiem = diemRepository.findAll();
        List<DangKyHocPhan> allDangKy = dangKyHocPhanRepository.findAll();
        
        thongKe.setTongSoSinhVien(allSinhVien.size());
        thongKe.setTongSoGiangVien(allGiangVien.size());
        thongKe.setTongSoLopHocPhan(allLopHocPhan.size());
        thongKe.setTongSoHocPhan(allHocPhan.size());
        thongKe.setTongSoKhoa(allKhoa.size());
        thongKe.setTongSoLop(allLop.size());
        
        // Tính tỷ lệ đăng ký trung bình
        if (!allLopHocPhan.isEmpty()) {
            double tyLeTrungBinh = allDangKy.size() * 100.0 / allLopHocPhan.size();
            thongKe.setTyLeDangKyTrungBinh(Math.round(tyLeTrungBinh * 100.0) / 100.0);
        }
        
        // Tính điểm trung bình toàn trường
        if (!allDiem.isEmpty()) {
            double diemTrungBinh = allDiem.stream()
                    .filter(d -> d.getDiemTongKet() != null)
                    .mapToDouble(Diem::getDiemTongKet)
                    .average()
                    .orElse(0.0);
            thongKe.setDiemTrungBinhToanTruong(Math.round(diemTrungBinh * 100.0) / 100.0);
        }
        
        return thongKe;
    }

    private List<AdminReportResponse.ThongKeTheoKhoa> calculateThongKeTheoKhoa() {
        List<AdminReportResponse.ThongKeTheoKhoa> result = new ArrayList<>();
        List<Khoa> allKhoa = khoaRepository.findAll();
        
        for (Khoa khoa : allKhoa) {
            AdminReportResponse.ThongKeTheoKhoa thongKe = new AdminReportResponse.ThongKeTheoKhoa();
            thongKe.setMaKhoa(khoa.getMaKhoa());
            thongKe.setTenKhoa(khoa.getTenKhoa());
            
            // Đếm sinh viên theo khoa
            List<Lop> lopKhoa = lopRepository.findByKhoa_MaKhoa(khoa.getMaKhoa());
            List<String> maLopList = lopKhoa.stream().map(Lop::getMaLop).collect(Collectors.toList());
            List<SinhVien> sinhVienKhoa = sinhVienRepository.findByLop_MaLopIn(maLopList);
            thongKe.setSoSinhVien(sinhVienKhoa.size());
            
            // Đếm giảng viên theo khoa
            List<GiangVien> giangVienKhoa = giangVienRepository.findByKhoa_MaKhoa(khoa.getMaKhoa());
            thongKe.setSoGiangVien(giangVienKhoa.size());
            
            // Đếm lớp học phần theo khoa
            List<HocPhan> hocPhanKhoa = hocPhanRepository.findByKhoa_MaKhoa(khoa.getMaKhoa());
            thongKe.setSoHocPhan(hocPhanKhoa.size());
            
            List<String> maHocPhanList = hocPhanKhoa.stream().map(HocPhan::getMaHocPhan).collect(Collectors.toList());
            List<LopHocPhan> lopHocPhanKhoa = lopHocPhanRepository.findByHocPhan_MaHocPhanIn(maHocPhanList);
            thongKe.setSoLopHocPhan(lopHocPhanKhoa.size());
            
            // Tính điểm trung bình theo khoa
            List<String> maLopHPList = lopHocPhanKhoa.stream().map(LopHocPhan::getMaLopHP).collect(Collectors.toList());
            List<Diem> diemKhoa = diemRepository.findByLopHocPhan_MaLopHPIn(maLopHPList);
            
            if (!diemKhoa.isEmpty()) {
                double diemTrungBinh = diemKhoa.stream()
                        .filter(d -> d.getDiemTongKet() != null)
                        .mapToDouble(Diem::getDiemTongKet)
                        .average()
                        .orElse(0.0);
                thongKe.setDiemTrungBinh(Math.round(diemTrungBinh * 100.0) / 100.0);
            }
            
            // Tính tỷ lệ đăng ký theo khoa
            if (!lopHocPhanKhoa.isEmpty()) {
                List<DangKyHocPhan> dangKyKhoa = dangKyHocPhanRepository.findByLopHocPhan_MaLopHPIn(maLopHPList);
                double tyLeDangKy = dangKyKhoa.size() * 100.0 / lopHocPhanKhoa.size();
                thongKe.setTyLeDangKy(Math.round(tyLeDangKy * 100.0) / 100.0);
            }
            
            result.add(thongKe);
        }
        
        return result;
    }

    private AdminReportResponse.PhanBoDiem calculatePhanBoDiem() {
        AdminReportResponse.PhanBoDiem phanBo = new AdminReportResponse.PhanBoDiem();
        List<Diem> allDiem = diemRepository.findAll();
        
        Map<String, Integer> xepLoai = new HashMap<>();
        int duoi4 = 0, tu4Den55 = 0, tu55Den7 = 0, tu7Den85 = 0, tu85Den10 = 0;
        
        for (Diem diem : allDiem) {
            if (diem.getDiemTongKet() != null) {
                double diemSo = diem.getDiemTongKet();
                
                if (diemSo < 4) duoi4++;
                else if (diemSo < 5.5) tu4Den55++;
                else if (diemSo < 7) tu55Den7++;
                else if (diemSo < 8.5) tu7Den85++;
                else tu85Den10++;
                
                // Xếp loại
                String xepLoaiChu = xepLoaiChu(diemSo);
                xepLoai.put(xepLoaiChu, xepLoai.getOrDefault(xepLoaiChu, 0) + 1);
            }
        }
        
        phanBo.setSoSinhVienDiemDuoi4(duoi4);
        phanBo.setSoSinhVienDiem4Den55(tu4Den55);
        phanBo.setSoSinhVienDiem55Den7(tu55Den7);
        phanBo.setSoSinhVienDiem7Den85(tu7Den85);
        phanBo.setSoSinhVienDiem85Den10(tu85Den10);
        phanBo.setXepLoai(xepLoai);
        
        return phanBo;
    }

    private List<AdminReportResponse.TopGiangVien> calculateTopGiangVien() {
        List<AdminReportResponse.TopGiangVien> result = new ArrayList<>();
        List<GiangVien> allGiangVien = giangVienRepository.findAll();
        
        for (GiangVien giangVien : allGiangVien) {
            AdminReportResponse.TopGiangVien topGV = new AdminReportResponse.TopGiangVien();
            topGV.setMaGiangVien(giangVien.getMaGiangVien());
            topGV.setTenGiangVien(giangVien.getTenGiangVien());
            topGV.setTenKhoa(giangVien.getKhoa().getTenKhoa());
            
            // Đếm số lớp phụ trách
            List<LopHocPhan> lopPhuTrach = lopHocPhanRepository.findByGiangVien(giangVien.getMaGiangVien());
            topGV.setSoLopPhuTrach(lopPhuTrach.size());
            
            // Đếm tổng số sinh viên
            List<String> maLopHPList = lopPhuTrach.stream().map(LopHocPhan::getMaLopHP).collect(Collectors.toList());
            List<Diem> diemLop = diemRepository.findByLopHocPhan_MaLopHPIn(maLopHPList);
            topGV.setTongSoSinhVien(diemLop.size());
            
            // Tính điểm trung bình
            if (!diemLop.isEmpty()) {
                double diemTrungBinh = diemLop.stream()
                        .filter(d -> d.getDiemTongKet() != null)
                        .mapToDouble(Diem::getDiemTongKet)
                        .average()
                        .orElse(0.0);
                topGV.setDiemTrungBinh(Math.round(diemTrungBinh * 100.0) / 100.0);
            }
            
            result.add(topGV);
        }
        
        // Sắp xếp theo số lớp phụ trách giảm dần và lấy top 10
        return result.stream()
                .sorted((a, b) -> Integer.compare(b.getSoLopPhuTrach(), a.getSoLopPhuTrach()))
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<AdminReportResponse.TopSinhVien> calculateTopSinhVien() {
        List<AdminReportResponse.TopSinhVien> result = new ArrayList<>();
        List<SinhVien> allSinhVien = sinhVienRepository.findAll();
        
        for (SinhVien sinhVien : allSinhVien) {
            AdminReportResponse.TopSinhVien topSV = new AdminReportResponse.TopSinhVien();
            topSV.setMaSinhVien(sinhVien.getMaSinhVien());
            topSV.setHoTenSinhVien(sinhVien.getHoTenSinhVien());
            topSV.setTenLop(sinhVien.getLop().getTenLop());
            topSV.setTenKhoa(sinhVien.getLop().getKhoa().getTenKhoa());
            
            // Tính điểm trung bình của sinh viên
            List<Diem> diemSV = diemRepository.findBySinhVien_MaSinhVien(sinhVien.getMaSinhVien());
            if (!diemSV.isEmpty()) {
                double diemTrungBinh = diemSV.stream()
                        .filter(d -> d.getDiemTongKet() != null)
                        .mapToDouble(Diem::getDiemTongKet)
                        .average()
                        .orElse(0.0);
                topSV.setDiemTrungBinh(Math.round(diemTrungBinh * 100.0) / 100.0);
            }
            
            // Tính số tín chỉ hoàn thành
            int soTinChi = diemSV.stream()
                    .filter(d -> d.getDiemTongKet() != null && d.getDiemTongKet() >= 5.0)
                    .mapToInt(d -> d.getLopHocPhan().getHocPhan().getSoTinChi())
                    .sum();
            topSV.setSoTinChiHoanThanh(soTinChi);
            
            result.add(topSV);
        }
        
        // Sắp xếp theo điểm trung bình giảm dần và lấy top 10
        return result.stream()
                .sorted((a, b) -> Double.compare(b.getDiemTrungBinh(), a.getDiemTrungBinh()))
                .limit(10)
                .collect(Collectors.toList());
    }

    private AdminReportResponse.ThongKeDangKy calculateThongKeDangKy() {
        AdminReportResponse.ThongKeDangKy thongKe = new AdminReportResponse.ThongKeDangKy();
        List<DangKyHocPhan> allDangKy = dangKyHocPhanRepository.findAll();
        
        thongKe.setTongSoDangKy(allDangKy.size());
        thongKe.setSoDangKyThanhCong(allDangKy.size()); // Giả sử tất cả đều thành công
        thongKe.setSoDangKyThatBai(0);
        thongKe.setTyLeThanhCong(100.0);
        
        // Thống kê đăng ký theo học phần
        Map<String, Integer> dangKyTheoHocPhan = new HashMap<>();
        for (DangKyHocPhan dk : allDangKy) {
            String maHocPhan = dk.getLopHocPhan().getHocPhan().getMaHocPhan();
            dangKyTheoHocPhan.put(maHocPhan, dangKyTheoHocPhan.getOrDefault(maHocPhan, 0) + 1);
        }
        thongKe.setDangKyTheoHocPhan(dangKyTheoHocPhan);
        
        return thongKe;
    }

    private List<AdminReportResponse.ThongKeTheoHocKy> calculateThongKeTheoHocKy() {
        List<AdminReportResponse.ThongKeTheoHocKy> result = new ArrayList<>();
        List<LopHocPhan> allLopHocPhan = lopHocPhanRepository.findAll();
        
        // Nhóm theo học kỳ (dựa vào mã lớp học phần)
        Map<String, List<LopHocPhan>> lopTheoHocKy = allLopHocPhan.stream()
                .collect(Collectors.groupingBy(lhp -> {
                    // Giả sử mã lớp học phần có format: LHP202501001 (năm học + học kỳ)
                    if (lhp.getMaLopHP().length() >= 9) {
                        String namHoc = lhp.getMaLopHP().substring(3, 7);
                        String hocKy = lhp.getMaLopHP().substring(7, 8);
                        return namHoc + "-" + hocKy;
                    }
                    return "Unknown";
                }));
        
        for (Map.Entry<String, List<LopHocPhan>> entry : lopTheoHocKy.entrySet()) {
            AdminReportResponse.ThongKeTheoHocKy thongKe = new AdminReportResponse.ThongKeTheoHocKy();
            String[] parts = entry.getKey().split("-");
            thongKe.setNamHoc(parts[0]);
            thongKe.setHocKy(parts[1]);
            thongKe.setSoLopHocPhan(entry.getValue().size());
            
            // Đếm số sinh viên đăng ký
            List<String> maLopHP = entry.getValue().stream()
                    .map(LopHocPhan::getMaLopHP)
                    .collect(Collectors.toList());
            List<DangKyHocPhan> dangKy = dangKyHocPhanRepository.findByLopHocPhan_MaLopHPIn(maLopHP);
            thongKe.setSoSinhVienDangKy(dangKy.size());
            
            // Tính điểm trung bình
            List<Diem> diem = diemRepository.findByLopHocPhan_MaLopHPIn(maLopHP);
            if (!diem.isEmpty()) {
                double diemTrungBinh = diem.stream()
                        .filter(d -> d.getDiemTongKet() != null)
                        .mapToDouble(Diem::getDiemTongKet)
                        .average()
                        .orElse(0.0);
                thongKe.setDiemTrungBinh(Math.round(diemTrungBinh * 100.0) / 100.0);
            }
            
            // Tính tỷ lệ đăng ký
            if (!entry.getValue().isEmpty()) {
                double tyLeDangKy = dangKy.size() * 100.0 / entry.getValue().size();
                thongKe.setTyLeDangKy(Math.round(tyLeDangKy * 100.0) / 100.0);
            }
            
            result.add(thongKe);
        }
        
        return result;
    }

    private String xepLoaiChu(double diem) {
        if (diem >= 8.5) return "A";
        else if (diem >= 7.0) return "B";
        else if (diem >= 5.5) return "C";
        else if (diem >= 4.0) return "D";
        else return "F";
    }
} 