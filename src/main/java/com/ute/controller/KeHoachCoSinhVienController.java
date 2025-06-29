package com.ute.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.request.KeHoachCoSinhVienRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.KeHoachChiTietResponse;
import com.ute.dto.response.KeHoachCoSinhVienResponse;
import com.ute.service.KeHoachCoSinhVienService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kehoachcosinhvien")
@CrossOrigin(origins = "*")
public class KeHoachCoSinhVienController {

    @Autowired
    private KeHoachCoSinhVienService keHoachCoSinhVienService;

    // ==================== API LẤY DỮ LIỆU ====================

    /**
     * Lấy tất cả kế hoạch học tập
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ApiResponse<List<KeHoachCoSinhVienResponse>> getAllKeHoach() {
        try {
            List<KeHoachCoSinhVienResponse> list = keHoachCoSinhVienService.getAllKeHoach();
            return new ApiResponse<>(true, "Lấy danh sách kế hoạch học tập thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy kế hoạch học tập theo mã sinh viên
     */
    @GetMapping("/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('SINHVIEN') or hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<KeHoachCoSinhVienResponse>> getKeHoachBySinhVien(@PathVariable String maSinhVien) {
        try {
            List<KeHoachCoSinhVienResponse> list = keHoachCoSinhVienService.getKeHoachBySinhVien(maSinhVien);
            return new ApiResponse<>(true, "Lấy kế hoạch học tập của sinh viên thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy kế hoạch học tập theo mã học phần
     */
    @GetMapping("/hocphan/{maHocPhan}")
    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<KeHoachCoSinhVienResponse>> getKeHoachByHocPhan(@PathVariable String maHocPhan) {
        try {
            List<KeHoachCoSinhVienResponse> list = keHoachCoSinhVienService.getKeHoachByHocPhan(maHocPhan);
            return new ApiResponse<>(true, "Lấy kế hoạch học tập theo học phần thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy kế hoạch học tập theo ID
     */
    @GetMapping("/{maKeHoach}/{maSinhVien}/{maHocPhan}")
    @PreAuthorize("hasRole('SINHVIEN') or hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<KeHoachCoSinhVienResponse> getKeHoachById(
            @PathVariable Integer maKeHoach,
            @PathVariable String maSinhVien,
            @PathVariable String maHocPhan) {
        try {
            KeHoachCoSinhVienResponse response = keHoachCoSinhVienService.getKeHoachById(maKeHoach, maSinhVien, maHocPhan);
            return new ApiResponse<>(true, "Lấy thông tin kế hoạch học tập thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy chi tiết kế hoạch học tập (thông tin đầy đủ)
     */
    @GetMapping("/chitiet/{maKeHoach}/{maSinhVien}/{maHocPhan}")
    @PreAuthorize("hasRole('SINHVIEN') or hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<KeHoachChiTietResponse> getKeHoachChiTiet(
            @PathVariable Integer maKeHoach,
            @PathVariable String maSinhVien,
            @PathVariable String maHocPhan) {
        try {
            KeHoachChiTietResponse response = keHoachCoSinhVienService.getKeHoachChiTiet(maKeHoach, maSinhVien, maHocPhan);
            return new ApiResponse<>(true, "Lấy chi tiết kế hoạch học tập thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy tất cả chi tiết kế hoạch học tập của sinh viên
     */
    @GetMapping("/chitiet/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('SINHVIEN') or hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<KeHoachChiTietResponse>> getAllKeHoachChiTietBySinhVien(@PathVariable String maSinhVien) {
        try {
            List<KeHoachChiTietResponse> list = keHoachCoSinhVienService.getAllKeHoachChiTietBySinhVien(maSinhVien);
            return new ApiResponse<>(true, "Lấy tất cả chi tiết kế hoạch học tập của sinh viên thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy kế hoạch học tập theo học kỳ và năm học
     */
    @GetMapping("/hocky-namhoc")
    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<KeHoachCoSinhVienResponse>> getKeHoachByHocKyAndNamHoc(
            @RequestParam Integer hocKy,
            @RequestParam String namHoc) {
        try {
            List<KeHoachCoSinhVienResponse> list = keHoachCoSinhVienService.getKeHoachByHocKyAndNamHoc(hocKy, namHoc);
            return new ApiResponse<>(true, "Lấy kế hoạch học tập theo học kỳ và năm học thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy kế hoạch học tập theo trạng thái
     */
    @GetMapping("/trangthai/{trangThai}")
    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<KeHoachCoSinhVienResponse>> getKeHoachByTrangThai(@PathVariable Integer trangThai) {
        try {
            List<KeHoachCoSinhVienResponse> list = keHoachCoSinhVienService.getKeHoachByTrangThai(trangThai);
            return new ApiResponse<>(true, "Lấy kế hoạch học tập theo trạng thái thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ==================== API TẠO MỚI ====================

    /**
     * Tạo kế hoạch học tập mới
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ApiResponse<KeHoachCoSinhVienResponse> createKeHoach(@Valid @RequestBody KeHoachCoSinhVienRequest request) {
        try {
            KeHoachCoSinhVienResponse response = keHoachCoSinhVienService.createKeHoach(request);
            return new ApiResponse<>(true, "Tạo kế hoạch học tập thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ==================== API CẬP NHẬT ====================

    /**
     * Cập nhật kế hoạch học tập
     */
    @PutMapping("/{maKeHoach}/{maSinhVien}/{maHocPhan}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ApiResponse<KeHoachCoSinhVienResponse> updateKeHoach(
            @PathVariable Integer maKeHoach,
            @PathVariable String maSinhVien,
            @PathVariable String maHocPhan,
            @Valid @RequestBody KeHoachCoSinhVienRequest request) {
        try {
            KeHoachCoSinhVienResponse response = keHoachCoSinhVienService.updateKeHoach(maKeHoach, maSinhVien, maHocPhan, request);
            return new ApiResponse<>(true, "Cập nhật kế hoạch học tập thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    // ==================== API XÓA ====================

    /**
     * Xóa kế hoạch học tập
     */
    @DeleteMapping("/{maKeHoach}/{maSinhVien}/{maHocPhan}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteKeHoach(
            @PathVariable Integer maKeHoach,
            @PathVariable String maSinhVien,
            @PathVariable String maHocPhan) {
        try {
            keHoachCoSinhVienService.deleteKeHoach(maKeHoach, maSinhVien, maHocPhan);
            return new ApiResponse<>(true, "Xóa kế hoạch học tập thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
} 