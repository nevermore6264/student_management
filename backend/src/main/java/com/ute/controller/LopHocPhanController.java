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
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.request.LopHocPhanRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.LopHocPhanResponse;
import com.ute.service.LopHocPhanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lophocphan")
@CrossOrigin(origins = "*")
public class LopHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @GetMapping
    public ApiResponse<List<LopHocPhanResponse>> getAllLopHocPhan() {
        try {
            List<LopHocPhanResponse> list = lopHocPhanService.findAll();
            return new ApiResponse<>(true, "Lấy danh sách lớp học phần thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<LopHocPhanResponse> getLopHocPhanById(@PathVariable String id) {
        try {
            LopHocPhanResponse response = lopHocPhanService.findById(id);
            return new ApiResponse<>(true, "Lấy thông tin lớp học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/hocphan/{maHocPhan}")
    public ApiResponse<List<LopHocPhanResponse>> getLopHocPhanByHocPhan(@PathVariable String maHocPhan) {
        try {
            List<LopHocPhanResponse> list = lopHocPhanService.findByHocPhan_MaHocPhan(maHocPhan);
            return new ApiResponse<>(true, "Lấy danh sách lớp học phần theo học phần thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/giangvien/{maGiangVien}")
    public ApiResponse<List<LopHocPhanResponse>> getLopHocPhanByGiangVien(@PathVariable String maGiangVien) {
        try {
            List<LopHocPhanResponse> list = lopHocPhanService.findByGiangVien(maGiangVien);
            return new ApiResponse<>(true, "Lấy danh sách lớp học phần theo giảng viên thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LopHocPhanResponse> createLopHocPhan(@Valid @RequestBody LopHocPhanRequest request) {
        try {
            LopHocPhanResponse response = lopHocPhanService.createLopHocPhan(request);
            return new ApiResponse<>(true, "Tạo lớp học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LopHocPhanResponse> updateLopHocPhan(@PathVariable String id, @Valid @RequestBody LopHocPhanRequest request) {
        try {
            LopHocPhanResponse response = lopHocPhanService.updateLopHocPhan(id, request);
            return new ApiResponse<>(true, "Cập nhật lớp học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteLopHocPhan(@PathVariable String id) {
        try {
            lopHocPhanService.deleteById(id);
            return new ApiResponse<>(true, "Xóa lớp học phần thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
} 
