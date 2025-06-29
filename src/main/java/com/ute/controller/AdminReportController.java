package com.ute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.response.AdminReportResponse;
import com.ute.dto.response.ApiResponse;
import com.ute.service.AdminReportService;

@RestController
@RequestMapping("/api/admin/reports")
@CrossOrigin(origins = "*")
// @PreAuthorize("hasRole('ADMIN')") // Uncomment khi cần bảo mật
public class AdminReportController {

    @Autowired
    private AdminReportService adminReportService;

    /**
     * Lấy báo cáo tổng hợp cho admin
     */
    @GetMapping("/tonghop")
    // @PreAuthorize("hasRole('ADMIN')") // Uncomment khi cần bảo mật
    public ApiResponse<AdminReportResponse> getTongHopBaoCao() {
        try {
            AdminReportResponse response = adminReportService.getTongHopBaoCao();
            return new ApiResponse<>(true, "Lấy báo cáo tổng hợp thành công", response);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }

} 
