package com.ute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.response.ApiResponse;
import com.ute.dto.response.KeHoachHocTapResponse;
import com.ute.service.KeHoachHocTapService;

@RestController
@RequestMapping("/api/kehoachhoctap")
public class KeHoachHocTapController {

    @Autowired
    private KeHoachHocTapService keHoachHocTapService;

    @GetMapping("/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('SINHVIEN') or hasRole('GIANGVIEN') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KeHoachHocTapResponse>> getKeHoachHocTap(@PathVariable String maSinhVien) {
        try {
            KeHoachHocTapResponse keHoachHocTap = keHoachHocTapService.getKeHoachHocTap(maSinhVien);
            
            if (keHoachHocTap.getKeHoachTheoHocKy().isEmpty()) {
                return ResponseEntity.ok(ApiResponse.error("Không tìm thấy kế hoạch học tập cho sinh viên: " + maSinhVien));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Lấy kế hoạch học tập thành công", keHoachHocTap));
                    
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi lấy kế hoạch học tập: " + e.getMessage()));
        }
    }
} 