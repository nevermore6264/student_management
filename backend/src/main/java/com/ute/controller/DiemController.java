package com.ute.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import com.ute.dto.request.DiemRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.DiemChiTietAllSinhVienResponse;
import com.ute.dto.response.DiemChiTietResponse;
import com.ute.dto.response.DiemFullInfoResponse;
import com.ute.dto.response.DiemResponse;
import com.ute.dto.response.DiemTongQuanAllSinhVienResponse;
import com.ute.dto.response.DiemTongQuanResponse;
import com.ute.service.DiemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diem")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DiemController {

    private final DiemService diemService;

    @GetMapping
    public ResponseEntity<List<DiemFullInfoResponse>> getAllDiem() {
        return ResponseEntity.ok(diemService.getAllDiem());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiemResponse> getDiemById(@PathVariable String id) {
        return ResponseEntity.ok(diemService.getDiemById(id));
    }

    @GetMapping("/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN') or #maSinhVien == authentication.principal.maNguoiDung")
    public ApiResponse<List<DiemResponse>> getDiemBySinhVien(@PathVariable String maSinhVien) {
        return new ApiResponse<>(true, "Lấy điểm theo sinh viên thành công", diemService.getDiemBySinhVien(maSinhVien));
    }

    @GetMapping("/lophocphan/{maLopHP}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ApiResponse<List<DiemResponse>> getDiemByLopHocPhan(@PathVariable String maLopHP) {
        return new ApiResponse<>(true, "Lấy điểm theo lớp học phần thành công", diemService.getDiemByLopHocPhan(maLopHP));
    }

    // Tổng quan điểm cho sinh viên (dùng cho màn hình tổng quan)
    @GetMapping("/sinhvien/{maSinhVien}/tongquan")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN') or #maSinhVien == authentication.principal.maNguoiDung")
    public ApiResponse<DiemTongQuanResponse> getTongQuanDiem(@PathVariable String maSinhVien) {
        return new ApiResponse<>(true, "Lấy tổng quan điểm thành công", diemService.getTongQuanDiem(maSinhVien));
    }

    // Chi tiết điểm từng môn cho sinh viên (dùng cho màn hình chi tiết)
    @GetMapping("/sinhvien/{maSinhVien}/chitiet")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN') or #maSinhVien == authentication.principal.maNguoiDung")
    public ApiResponse<List<DiemChiTietResponse>> getChiTietDiem(@PathVariable String maSinhVien) {
        return new ApiResponse<>(true, "Lấy chi tiết điểm thành công", diemService.getChiTietDiem(maSinhVien));
    }

    // Tổng quan điểm của toàn bộ sinh viên (admin)
    @GetMapping("/tongquan")
    public ApiResponse<List<DiemTongQuanAllSinhVienResponse>> getTongQuanTatCaSinhVien() {
        return new ApiResponse<>(true, "Lấy tổng quan điểm tất cả sinh viên thành công", diemService.getTongQuanTatCaSinhVien());
    }

    // Chi tiết điểm từng môn của toàn bộ sinh viên (admin)
    @GetMapping("/chitiet")
    public ApiResponse<List<DiemChiTietAllSinhVienResponse>> getChiTietTatCaSinhVien() {
        return new ApiResponse<>(true, "Lấy chi tiết điểm tất cả sinh viên thành công", diemService.getChiTietTatCaSinhVien());
    }

    // Chỉ giáo viên được thêm/sửa/xóa điểm
    @PostMapping
    @PreAuthorize("hasRole('GIANGVIEN')")
    public ApiResponse<DiemResponse> createDiem(@RequestBody DiemResponse diem) {
        return new ApiResponse<>(true, "Thêm điểm thành công", diemService.createDiem(diem));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GIANGVIEN')")
    public ApiResponse<DiemResponse> updateDiem(@PathVariable String id, @RequestBody DiemResponse diem) {
        return new ApiResponse<>(true, "Cập nhật điểm thành công", diemService.updateDiem(id, diem));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GIANGVIEN')")
    public ApiResponse<Void> deleteDiem(@PathVariable String id) {
        diemService.deleteDiem(id);
        return new ApiResponse<>(true, "Xóa điểm thành công", null);
    }

    // ==================== API CHO GIẢNG VIÊN ====================

    /**
     * Nhập điểm cho sinh viên
     */
    @PostMapping("/nhap")
    @PreAuthorize("hasRole('GIANGVIEN')")
    public ApiResponse<DiemResponse> nhapDiem(@Valid @RequestBody DiemRequest request) {
        try {
            DiemResponse response = diemService.nhapDiem(request);
            return new ApiResponse<>(true, "Nhập điểm thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Cập nhật điểm cho sinh viên (dùng composite key)
     */
    @PutMapping("/capnhat")
    @PreAuthorize("hasRole('GIANGVIEN')")
    public ApiResponse<DiemResponse> capNhatDiem(@RequestParam String maSinhVien, @RequestParam String maLopHP, @Valid @RequestBody DiemRequest request) {
        try {
            DiemResponse response = diemService.capNhatDiem(maSinhVien, maLopHP, request);
            return new ApiResponse<>(true, "Cập nhật điểm thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    /**
     * Lấy điểm của sinh viên trong lớp học phần
     */
    @GetMapping("/lophocphan/{maLopHP}/diem")
    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ApiResponse<List<DiemResponse>> getDiemTrongLop(@PathVariable String maLopHP) {
        try {
            List<DiemResponse> list = diemService.getDiemByLopHocPhan(maLopHP);
            return new ApiResponse<>(true, "Lấy điểm trong lớp thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
} 
