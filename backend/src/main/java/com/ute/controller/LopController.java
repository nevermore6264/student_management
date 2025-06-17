package com.ute.controller;

import com.ute.dto.request.LopRequest;
import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.LopResponse;
import com.ute.service.LopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lop")
@RequiredArgsConstructor
public class LopController {
    private final LopService lopService;

    @GetMapping
    public ApiResponse<List<LopResponse>> getAllLop() {
        try {
            List<LopResponse> lops = lopService.getAllLop();
            return new ApiResponse<>(true, "Lấy danh sách lớp thành công", lops);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<LopResponse> getLopById(@PathVariable String id) {
        try {
            LopResponse lop = lopService.getLopById(id);
            return new ApiResponse<>(true, "Lấy thông tin lớp thành công", lop);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @GetMapping("/khoa/{maKhoa}")
    public ApiResponse<List<LopResponse>> getLopByKhoa(@PathVariable String maKhoa) {
        try {
            List<LopResponse> lops = lopService.getLopByKhoa(maKhoa);
            return new ApiResponse<>(true, "Lấy danh sách lớp theo khoa thành công", lops);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PostMapping
    public ApiResponse<LopResponse> createLop(@Valid @RequestBody LopRequest request) {
        try {
            LopResponse createdLop = lopService.createLop(request);
            return new ApiResponse<>(true, "Thêm lớp thành công", createdLop);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<LopResponse> updateLop(@PathVariable String id, @Valid @RequestBody LopRequest request) {
        try {
            LopResponse updatedLop = lopService.updateLop(id, request);
            return new ApiResponse<>(true, "Cập nhật lớp thành công", updatedLop);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLop(@PathVariable String id) {
        try {
            lopService.deleteLop(id);
            return new ApiResponse<>(true, "Xóa lớp thành công", null);
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
