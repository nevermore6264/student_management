package com.ute.controller;

import java.util.List;

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

import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.HocPhanResponse;
import com.ute.entity.HocPhan;
import com.ute.service.HocPhanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hocphan")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class HocPhanController {

    private final HocPhanService hocPhanService;

    @GetMapping
    public ApiResponse<List<HocPhanResponse>> getAllHocPhan() {
        try {
            List<HocPhanResponse> hocPhans = hocPhanService.getAllHocPhan();
            return new ApiResponse<>(true, "Lấy danh sách học phần thành công", hocPhans);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<HocPhanResponse> getHocPhanById(@PathVariable String id) {
        try {
            HocPhanResponse hocPhan = hocPhanService.getHocPhanById(id);
            return new ApiResponse<>(true, "Lấy thông tin học phần thành công", hocPhan);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/khoa/{maKhoa}")
    public ApiResponse<List<HocPhanResponse>> getHocPhanByKhoa(@PathVariable String maKhoa) {
        try {
            List<HocPhanResponse> hocPhans = hocPhanService.getHocPhanByKhoa(maKhoa);
            return new ApiResponse<>(true, "Lấy danh sách học phần theo khoa thành công", hocPhans);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<HocPhanResponse> createHocPhan(@RequestBody HocPhan hocPhan) {
        try {
            HocPhan createdHocPhan = hocPhanService.createHocPhan(hocPhan);
            HocPhanResponse response = hocPhanService.getHocPhanById(createdHocPhan.getMaHocPhan());
            return new ApiResponse<>(true, "Tạo học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<HocPhanResponse> updateHocPhan(@PathVariable String id, @RequestBody HocPhan hocPhan) {
        try {
            HocPhan updatedHocPhan = hocPhanService.updateHocPhan(id, hocPhan);
            HocPhanResponse response = hocPhanService.getHocPhanById(updatedHocPhan.getMaHocPhan());
            return new ApiResponse<>(true, "Cập nhật học phần thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteHocPhan(@PathVariable String id) {
        try {
            hocPhanService.deleteHocPhan(id);
            return new ApiResponse<>(true, "Xóa học phần thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
} 
