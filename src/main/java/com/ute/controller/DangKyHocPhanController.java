package com.ute.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.request.DangKyHocPhanRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.DangKyHocPhanResponse;
import com.ute.entity.DangKyHocPhan;
import com.ute.entity.DangKyHocPhanId;
import com.ute.service.DangKyHocPhanService;

@RestController
@RequestMapping("/api/dangky")
@CrossOrigin(origins = "*")
public class DangKyHocPhanController {

    @Autowired
    private DangKyHocPhanService dangKyHocPhanService;

    @GetMapping("/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('ADMIN') or #maSinhVien == authentication.principal.maNguoiDung")
    public ApiResponse<List<DangKyHocPhanResponse>> getDangKyBySinhVien(@PathVariable String maSinhVien) {
        try {
            List<DangKyHocPhan> dangKyList = dangKyHocPhanService.getDangKyBySinhVien(maSinhVien);
            List<DangKyHocPhanResponse> responseList = dangKyList.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, "Lấy danh sách đăng ký học phần thành công", responseList);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy danh sách đăng ký: " + e.getMessage(), null);
        }
    }

    @GetMapping("/lophp/{maLopHP}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<DangKyHocPhanResponse>> getDangKyByLopHP(@PathVariable String maLopHP) {
        try {
            List<DangKyHocPhan> dangKyList = dangKyHocPhanService.getDangKyByLopHP(maLopHP);
            List<DangKyHocPhanResponse> responseList = dangKyList.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, "Lấy danh sách đăng ký theo lớp học phần thành công", responseList);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy danh sách đăng ký: " + e.getMessage(), null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #request.maSinhVien == authentication.principal.maNguoiDung")
    public ApiResponse<DangKyHocPhanResponse> createDangKy(@RequestBody DangKyHocPhanRequest request) {
        try {
            DangKyHocPhan savedDangKy = dangKyHocPhanService.createDangKyFromRequest(request);
            DangKyHocPhanResponse response = mapToResponse(savedDangKy);
            return new ApiResponse<>(true, "Đăng ký học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi đăng ký học phần: " + e.getMessage(), null);
        }
    }

    @PutMapping("/{maPhienDK}/{maSinhVien}/{maLopHP}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DangKyHocPhanResponse> updateDangKy(
            @PathVariable Integer maPhienDK,
            @PathVariable String maSinhVien,
            @PathVariable String maLopHP,
            @RequestBody DangKyHocPhan dangKyHocPhan) {
        try {
            DangKyHocPhanId id = new DangKyHocPhanId(maPhienDK, maSinhVien, maLopHP);
            DangKyHocPhan updatedDangKy = dangKyHocPhanService.updateDangKy(id, dangKyHocPhan);
            DangKyHocPhanResponse response = mapToResponse(updatedDangKy);
            return new ApiResponse<>(true, "Cập nhật đăng ký học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi cập nhật đăng ký học phần: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/{maPhienDK}/{maSinhVien}/{maLopHP}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteDangKy(
            @PathVariable Integer maPhienDK,
            @PathVariable String maSinhVien,
            @PathVariable String maLopHP) {
        try {
            DangKyHocPhanId id = new DangKyHocPhanId(maPhienDK, maSinhVien, maLopHP);
            dangKyHocPhanService.deleteDangKy(id);
            return new ApiResponse<>(true, "Xóa đăng ký học phần thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi xóa đăng ký học phần: " + e.getMessage(), null);
        }
    }

    private DangKyHocPhanResponse mapToResponse(DangKyHocPhan dangKy) {
        DangKyHocPhanResponse response = new DangKyHocPhanResponse();
        
        if (dangKy.getPhienDangKy() != null && dangKy.getPhienDangKy().getSinhVien() != null) {
            response.setMaSinhVien(dangKy.getPhienDangKy().getSinhVien().getMaSinhVien());
            response.setHoTenSinhVien(dangKy.getPhienDangKy().getSinhVien().getHoTenSinhVien());
        }
        
        if (dangKy.getLopHocPhan() != null) {
            response.setMaLopHP(dangKy.getLopHocPhan().getMaLopHP());
            response.setTenLopHP(dangKy.getLopHocPhan().getTenLopHP());
            
            if (dangKy.getLopHocPhan().getHocPhan() != null) {
                response.setMaHocPhan(dangKy.getLopHocPhan().getHocPhan().getMaHocPhan());
                response.setTenHocPhan(dangKy.getLopHocPhan().getHocPhan().getTenHocPhan());
            }
        }
        
        response.setThoiGianDangKy(dangKy.getThoiGianDangKy());
        response.setTrangThai(dangKy.getTrangThai());
        response.setKetQuaDangKy(dangKy.getKetQuaDangKy());
        
        return response;
    }
} 
