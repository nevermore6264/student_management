package com.ute.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.request.GiangVienRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.GiangVienResponse;
import com.ute.service.GiangVienService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/giangvien")
@RequiredArgsConstructor
public class GiangVienController {
    private final GiangVienService giangVienService;

    @GetMapping
    public ApiResponse<List<GiangVienResponse>> getAllGiangVien() {
        try {
            List<GiangVienResponse> giangViens = giangVienService.getAllGiangVien();
            return new ApiResponse<>(true, "Lấy danh sách giảng viên thành công", giangViens);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<GiangVienResponse> getGiangVienById(@PathVariable String id) {
        try {
            GiangVienResponse giangVien = giangVienService.getGiangVienById(id);
            return new ApiResponse<>(true, "Lấy thông tin giảng viên thành công", giangVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    public ApiResponse<GiangVienResponse> createGiangVien(@Valid @RequestBody GiangVienRequest request) {
        try {
            GiangVienResponse createdGiangVien = giangVienService.createGiangVien(request);
            return new ApiResponse<>(true, "Thêm giảng viên thành công", createdGiangVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<GiangVienResponse> updateGiangVien(@PathVariable String id, @Valid @RequestBody GiangVienRequest request) {
        try {
            GiangVienResponse updatedGiangVien = giangVienService.updateGiangVien(id, request);
            return new ApiResponse<>(true, "Cập nhật giảng viên thành công", updatedGiangVien);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGiangVien(@PathVariable String id) {
        try {
            giangVienService.deleteGiangVien(id);
            return new ApiResponse<>(true, "Xóa giảng viên thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/khoa/{maKhoa}")
    public ApiResponse<List<GiangVienResponse>> getGiangVienByKhoa(@PathVariable String maKhoa) {
        try {
            List<GiangVienResponse> giangViens = giangVienService.getGiangVienByKhoa(maKhoa);
            return new ApiResponse<>(true, "Lấy danh sách giảng viên theo khoa thành công", giangViens);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ApiResponse<>(false, "Dữ liệu không hợp lệ", errors);
    }
} 