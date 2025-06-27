package com.ute.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.ute.dto.response.DiemExportResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.DiemRequest;
import com.ute.dto.response.DiemChiTietAllSinhVienResponse;
import com.ute.dto.response.DiemChiTietResponse;
import com.ute.dto.response.DiemFullInfoResponse;
import com.ute.dto.response.DiemResponse;
import com.ute.dto.response.DiemTongQuanAllSinhVienResponse;
import com.ute.dto.response.DiemTongQuanLopResponse;
import com.ute.dto.response.DiemTongQuanResponse;
import com.ute.dto.response.SinhVienTrongLopResponse;
import com.ute.dto.response.TongKetHocKyResponse;
import com.ute.entity.Diem;
import com.ute.entity.DiemId;
import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.KeHoachCoSinhVienId;
import com.ute.repository.DiemRepository;
import com.ute.repository.KeHoachCoSinhVienRepository;
import com.ute.repository.SinhVienRepository;
import com.ute.service.DiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiemServiceImpl implements DiemService {
    private final DiemRepository diemRepository;
    private final KeHoachCoSinhVienRepository keHoachCoSinhVienRepository;
    private final SinhVienRepository sinhVienRepository;

    @Override
    public List<DiemFullInfoResponse> getAllDiem() {
        return diemRepository.findAllDiemFullInfo();
    }

    @Override
    public DiemResponse getDiemById(String id) {
        // Tách id thành maSinhVien và maLopHP
        String[] parts = id.split("-");
        if (parts.length != 2) {
            return null;
        }
        DiemId diemId = new DiemId(parts[0], parts[1]);
        return diemRepository.findById(diemId)
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
        
        // Map các trường khác
        diem.setDiemChuyenCan(diemDto.getDiemChuyenCan());
        diem.setDiemGiuaKy(diemDto.getDiemGiuaKy());
        diem.setDiemCuoiKy(diemDto.getDiemCuoiKy());
        diem.setDiemTongKet(diemDto.getDiemTongKet());
        diem.setGhiChu(diemDto.getGhiChu());
        
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    @Transactional
    public DiemResponse updateDiem(String id, DiemResponse diemDto) {
        String[] parts = id.split("-");
        if (parts.length != 2) {
            throw new RuntimeException("ID không hợp lệ");
        }
        DiemId diemId = new DiemId(parts[0], parts[1]);
        Diem diem = diemRepository.findById(diemId).orElseThrow();
        
        // Cập nhật các trường từ diemDto
        diem.setDiemChuyenCan(diemDto.getDiemChuyenCan());
        diem.setDiemGiuaKy(diemDto.getDiemGiuaKy());
        diem.setDiemCuoiKy(diemDto.getDiemCuoiKy());
        diem.setDiemTongKet(diemDto.getDiemTongKet());
        diem.setGhiChu(diemDto.getGhiChu());
        
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    @Transactional
    public void deleteDiem(String id) {
        String[] parts = id.split("-");
        if (parts.length != 2) {
            throw new RuntimeException("ID không hợp lệ");
        }
        DiemId diemId = new DiemId(parts[0], parts[1]);
        diemRepository.deleteById(diemId);
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
        DiemResponse response = new DiemResponse();
        response.setMaSinhVien(diem.getSinhVien().getMaSinhVien());
        response.setHoTenSinhVien(diem.getSinhVien().getHoTenSinhVien());
        response.setMaLopHP(diem.getLopHocPhan().getMaLopHP());
        response.setDiemChuyenCan(diem.getDiemChuyenCan());
        response.setDiemGiuaKy(diem.getDiemGiuaKy());
        response.setDiemCuoiKy(diem.getDiemCuoiKy());
        response.setDiemTongKet(diem.getDiemTongKet());
        response.setGhiChu(diem.getGhiChu());
        // Map thêm các trường khác nếu cần
        return response;
    }

    // ==================== CÁC METHOD MỚI CHO GIẢNG VIÊN ====================

    @Override
    public List<SinhVienTrongLopResponse> getSinhVienTrongLop(String maLopHP) {
        List<Diem> diemList = diemRepository.findByLopHocPhan_MaLopHP(maLopHP);
        return diemList.stream()
                .map(diem -> {
                    SinhVienTrongLopResponse response = new SinhVienTrongLopResponse();
                    if (diem.getSinhVien() != null) {
                        response.setMaSinhVien(diem.getSinhVien().getMaSinhVien());
                        response.setHoTenSinhVien(diem.getSinhVien().getHoTenSinhVien());
                        response.setEmail(diem.getSinhVien().getEmail());
                        response.setSoDienThoai(diem.getSinhVien().getSoDienThoai());
                        response.setGioiTinh(diem.getSinhVien().getGioiTinh());
                        response.setDiaChi(diem.getSinhVien().getDiaChi());
                        
                        if (diem.getSinhVien().getLop() != null) {
                            response.setMaLop(diem.getSinhVien().getLop().getMaLop());
                            response.setTenLop(diem.getSinhVien().getLop().getTenLop());
                            
                            if (diem.getSinhVien().getLop().getKhoa() != null) {
                                response.setMaKhoa(diem.getSinhVien().getLop().getKhoa().getMaKhoa());
                                response.setTenKhoa(diem.getSinhVien().getLop().getKhoa().getTenKhoa());
                            }
                        }
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DiemResponse nhapDiem(DiemRequest request) {
        // Kiểm tra xem điểm đã tồn tại chưa
        DiemId diemId = new DiemId(request.getMaSinhVien(), request.getMaLopHP());
        Diem existingDiem = diemRepository.findById(diemId).orElse(null);
        
        if (existingDiem != null) {
            throw new RuntimeException("Điểm của sinh viên này đã tồn tại trong lớp học phần");
        }
        
        // Tạo điểm mới
        Diem diem = new Diem();
        diem.setId(diemId);
        diem.setDiemChuyenCan(request.getDiemChuyenCan());
        diem.setDiemGiuaKy(request.getDiemGiuaKy());
        diem.setDiemCuoiKy(request.getDiemCuoiKy());
        diem.setGhiChu(request.getGhiChu());
        
        // Tính điểm tổng kết (có thể thêm logic tính toán dựa trên hệ số)
        float diemTongKet = calculateDiemTongKet(request.getDiemChuyenCan(), request.getDiemGiuaKy(), request.getDiemCuoiKy());
        diem.setDiemTongKet(diemTongKet);
        
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    @Transactional
    public DiemResponse capNhatDiem(String maSinhVien, String maLopHP, DiemRequest request) {
        DiemId diemId = new DiemId(maSinhVien, maLopHP);
        Diem diem = diemRepository.findById(diemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy điểm cần cập nhật"));
        diem.setDiemChuyenCan(request.getDiemChuyenCan());
        diem.setDiemGiuaKy(request.getDiemGiuaKy());
        diem.setDiemCuoiKy(request.getDiemCuoiKy());
        diem.setGhiChu(request.getGhiChu());
        // Tính lại điểm tổng kết
        float diemTongKet = calculateDiemTongKet(request.getDiemChuyenCan(), request.getDiemGiuaKy(), request.getDiemCuoiKy());
        diem.setDiemTongKet(diemTongKet);
        return mapToResponse(diemRepository.save(diem));
    }

    @Override
    public DiemTongQuanLopResponse getTongQuanDiemLop(String maLopHP) {
        List<Diem> diemList = diemRepository.findByLopHocPhan_MaLopHP(maLopHP);
        DiemTongQuanLopResponse response = new DiemTongQuanLopResponse();
        
        if (!diemList.isEmpty() && diemList.get(0).getLopHocPhan() != null) {
            response.setMaLopHP(maLopHP);
            response.setTenLopHP(diemList.get(0).getLopHocPhan().getTenLopHP());
            
            if (diemList.get(0).getLopHocPhan().getHocPhan() != null) {
                response.setTenHocPhan(diemList.get(0).getLopHocPhan().getHocPhan().getTenHocPhan());
                response.setSoTinChi(diemList.get(0).getLopHocPhan().getHocPhan().getSoTinChi());
            }
        }
        
        response.setTongSoSinhVien(diemList.size());
        
        List<Diem> diemCoDiem = diemList.stream()
                .filter(d -> d.getDiemTongKet() != null)
                .collect(Collectors.toList());
        
        response.setSoSinhVienCoDiem(diemCoDiem.size());
        
        if (!diemCoDiem.isEmpty()) {
            double diemTrungBinh = diemCoDiem.stream()
                    .mapToDouble(Diem::getDiemTongKet)
                    .average()
                    .orElse(0.0);
            response.setDiemTrungBinhLop(diemTrungBinh);
            
            double diemCaoNhat = diemCoDiem.stream()
                    .mapToDouble(Diem::getDiemTongKet)
                    .max()
                    .orElse(0.0);
            response.setDiemCaoNhat(diemCaoNhat);
            
            double diemThapNhat = diemCoDiem.stream()
                    .mapToDouble(Diem::getDiemTongKet)
                    .min()
                    .orElse(0.0);
            response.setDiemThapNhat(diemThapNhat);
            
            long soSinhVienDat = diemCoDiem.stream()
                    .filter(d -> d.getDiemTongKet() >= 5.0)
                    .count();
            response.setSoSinhVienDat((int) soSinhVienDat);
            response.setSoSinhVienKhongDat(diemCoDiem.size() - (int) soSinhVienDat);
            
            // Thống kê theo khoảng điểm
            response.setThongKeDiem(calculateThongKeDiem(diemCoDiem));
        }
        
        return response;
    }

    @Override
    public byte[] xuatBaoCaoDiem(String maLopHP) {
        List<Diem> diemList = diemRepository.findByLopHocPhan_MaLopHP(maLopHP);
    
        // Tạo DTO cho export
        List<DiemExportResponse> exportList = diemList.stream().map(diem -> {
            DiemExportResponse dto = new DiemExportResponse();
            dto.setMaSinhVien(diem.getSinhVien().getMaSinhVien());
            dto.setHoTenSinhVien(diem.getSinhVien().getHoTenSinhVien());
            dto.setDiemChuyenCan(diem.getDiemChuyenCan());
            dto.setDiemGiuaKy(diem.getDiemGiuaKy());
            dto.setDiemCuoiKy(diem.getDiemCuoiKy());
            dto.setDiemTongKet(diem.getDiemTongKet());
            dto.setGhiChu(diem.getGhiChu());
            return dto;
        }).toList();
    
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            EasyExcel.write(out, DiemExportResponse.class)
                .sheet("Báo cáo điểm")
                .doWrite(exportList);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    // ==================== HELPER METHODS ====================

    private float calculateDiemTongKet(Float diemChuyenCan, Float diemGiuaKy, Float diemCuoiKy) {
        // Logic tính điểm tổng kết (có thể thay đổi theo yêu cầu)
        // Ví dụ: Chuyên cần 10%, Giữa kỳ 30%, Cuối kỳ 60%
        float tongKet = 0.0f;
        
        if (diemChuyenCan != null) tongKet += diemChuyenCan * 0.1f;
        if (diemGiuaKy != null) tongKet += diemGiuaKy * 0.3f;
        if (diemCuoiKy != null) tongKet += diemCuoiKy * 0.6f;
        
        return Math.round(tongKet * 100.0f) / 100.0f; // Làm tròn 2 chữ số thập phân
    }

    private List<DiemTongQuanLopResponse.ThongKeDiemResponse> calculateThongKeDiem(List<Diem> diemList) {
        List<DiemTongQuanLopResponse.ThongKeDiemResponse> thongKe = new ArrayList<>();
        
        // Định nghĩa các khoảng điểm
        String[] khoangDiem = {"0-2", "2-4", "4-5", "5-6.5", "6.5-8", "8-9", "9-10"};
        double[] gioiHan = {0, 2, 4, 5, 6.5, 8, 9, 10};
        
        for (int i = 0; i < khoangDiem.length; i++) {
            DiemTongQuanLopResponse.ThongKeDiemResponse tk = new DiemTongQuanLopResponse.ThongKeDiemResponse();
            tk.setKhoangDiem(khoangDiem[i]);
            
            final double min = gioiHan[i];
            final double max = gioiHan[i + 1];
            
            long soLuong = diemList.stream()
                    .filter(d -> d.getDiemTongKet() >= min && d.getDiemTongKet() < max)
                    .count();
            tk.setSoLuong((int) soLuong);
            
            if (diemList.size() > 0) {
                tk.setTyLe((double) soLuong / diemList.size() * 100);
            } else {
                tk.setTyLe(0.0);
            }
            
            thongKe.add(tk);
        }
        
        return thongKe;
    }

    private DiemId parseDiemId(String id) {
        // Tách id thành maSinhVien và maLopHP
        String[] parts = id.split("-");
        if (parts.length != 2) {
            throw new RuntimeException("ID không hợp lệ");
        }
        return new DiemId(parts[0], parts[1]);
    }
} 
