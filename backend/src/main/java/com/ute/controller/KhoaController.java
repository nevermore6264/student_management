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

import com.ute.dto.request.KhoaRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.KhoaResponse;
import com.ute.service.KhoaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/khoa")
@RequiredArgsConstructor
public class KhoaController {
    private final KhoaService khoaService;

    @GetMapping
    public ApiResponse<List<KhoaResponse>> getAllKhoa() {
        try {
            List<KhoaResponse> khoas = khoaService.getAllKhoa();
            return new ApiResponse<>(true, "Lấy danh sách khoa thành công", khoas);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<KhoaResponse> getKhoaById(@PathVariable String id) {
        try {
            KhoaResponse khoa = khoaService.getKhoaById(id);
            return new ApiResponse<>(true, "Lấy thông tin khoa thành công", khoa);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    public ApiResponse<KhoaResponse> createKhoa(@Valid @RequestBody KhoaRequest request) {
        try {
            KhoaResponse createdKhoa = khoaService.createKhoa(request);
            return new ApiResponse<>(true, "Thêm khoa thành công", createdKhoa);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<KhoaResponse> updateKhoa(@PathVariable String id, @Valid @RequestBody KhoaRequest request) {
        try {
            KhoaResponse updatedKhoa = khoaService.updateKhoa(id, request);
            return new ApiResponse<>(true, "Cập nhật khoa thành công", updatedKhoa);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteKhoa(@PathVariable String id) {
        try {
            khoaService.deleteKhoa(id);
            return new ApiResponse<>(true, "Xóa khoa thành công", null);
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
