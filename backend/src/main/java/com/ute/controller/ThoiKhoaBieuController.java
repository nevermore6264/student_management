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

import com.ute.dto.request.ThoiKhoaBieuRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.ThoiKhoaBieuResponse;
import com.ute.service.ThoiKhoaBieuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/thoikhoabieu")
@CrossOrigin(origins = "*")
public class ThoiKhoaBieuController {

    @Autowired
    private ThoiKhoaBieuService thoiKhoaBieuService;

    @GetMapping
    public ApiResponse<List<ThoiKhoaBieuResponse>> getAll() {
        return new ApiResponse<>(true, "Lấy danh sách TKB thành công", thoiKhoaBieuService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ThoiKhoaBieuResponse> getById(@PathVariable Integer id) {
        return new ApiResponse<>(true, "Lấy chi tiết TKB thành công", thoiKhoaBieuService.getById(id));
    }

    @GetMapping("/lophocphan/{maLopHP}")
    public ApiResponse<List<ThoiKhoaBieuResponse>> getByLopHocPhan(@PathVariable String maLopHP) {
        return new ApiResponse<>(true, "Lấy TKB theo lớp học phần thành công", thoiKhoaBieuService.getByLopHocPhan(maLopHP));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ThoiKhoaBieuResponse> create(@Valid @RequestBody ThoiKhoaBieuRequest request) {
        return new ApiResponse<>(true, "Tạo TKB thành công", thoiKhoaBieuService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ThoiKhoaBieuResponse> update(@PathVariable Integer id, @Valid @RequestBody ThoiKhoaBieuRequest request) {
        return new ApiResponse<>(true, "Cập nhật TKB thành công", thoiKhoaBieuService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        thoiKhoaBieuService.delete(id);
        return new ApiResponse<>(true, "Xóa TKB thành công", null);
    }
} 
