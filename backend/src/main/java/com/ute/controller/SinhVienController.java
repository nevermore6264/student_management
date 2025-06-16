package com.ute.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.request.SinhVienRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.SinhVienResponse;
import com.ute.service.SinhVienService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sinhvien")
@RequiredArgsConstructor
public class SinhVienController {
    private final SinhVienService sinhVienService;

    @GetMapping
    public ApiResponse<List<SinhVienResponse>> getAllSinhVien() {
        try {
            List<SinhVienResponse> sinhViens = sinhVienService.getAllSinhVien();
            return new ApiResponse<>(true, "Lấy danh sách sinh viên thành công", sinhViens);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<SinhVienResponse> getSinhVienById(@PathVariable String id) {
        try {
            SinhVienResponse sinhVien = sinhVienService.getSinhVienById(id);
            return new ApiResponse<>(true, "Lấy thông tin sinh viên thành công", sinhVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    public ApiResponse<SinhVienResponse> createSinhVien(@RequestBody SinhVienRequest request) {
        try {
            SinhVienResponse createdSinhVien = sinhVienService.createSinhVien(request);
            return new ApiResponse<>(true, "Thêm sinh viên thành công", createdSinhVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<SinhVienResponse> updateSinhVien(@PathVariable String id, @RequestBody SinhVienRequest request) {
        try {
            SinhVienResponse updatedSinhVien = sinhVienService.updateSinhVien(id, request);
            return new ApiResponse<>(true, "Cập nhật sinh viên thành công", updatedSinhVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSinhVien(@PathVariable String id) {
        try {
            sinhVienService.deleteSinhVien(id);
            return new ApiResponse<>(true, "Xóa sinh viên thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/lop/{maLop}")
    public ApiResponse<List<SinhVienResponse>> getSinhVienByLop(@PathVariable String maLop) {
        try {
            List<SinhVienResponse> sinhViens = sinhVienService.getSinhVienByLop(maLop);
            return new ApiResponse<>(true, "Lấy danh sách sinh viên theo lớp thành công", sinhViens);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
}
