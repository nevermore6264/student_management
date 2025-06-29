package com.ute.service;

import java.util.List;

import com.ute.dto.request.KeHoachCoSinhVienRequest;
import com.ute.dto.response.KeHoachCoSinhVienResponse;

public interface KeHoachCoSinhVienService {
    
    // Lấy tất cả kế hoạch học tập
    List<KeHoachCoSinhVienResponse> getAllKeHoach();
    
    // Lấy kế hoạch học tập theo mã sinh viên
    List<KeHoachCoSinhVienResponse> getKeHoachBySinhVien(String maSinhVien);
    
    // Lấy kế hoạch học tập theo mã học phần
    List<KeHoachCoSinhVienResponse> getKeHoachByHocPhan(String maHocPhan);
    
    // Lấy kế hoạch học tập theo ID
    KeHoachCoSinhVienResponse getKeHoachById(Integer maKeHoach, String maSinhVien, String maHocPhan);
    
    // Tạo kế hoạch học tập mới
    KeHoachCoSinhVienResponse createKeHoach(KeHoachCoSinhVienRequest request);
    
    // Cập nhật kế hoạch học tập
    KeHoachCoSinhVienResponse updateKeHoach(Integer maKeHoach, String maSinhVien, String maHocPhan, KeHoachCoSinhVienRequest request);
    
    // Xóa kế hoạch học tập
    void deleteKeHoach(Integer maKeHoach, String maSinhVien, String maHocPhan);
    
    // Lấy kế hoạch học tập theo học kỳ và năm học
    List<KeHoachCoSinhVienResponse> getKeHoachByHocKyAndNamHoc(Integer hocKy, String namHoc);
    
    // Lấy kế hoạch học tập theo trạng thái
    List<KeHoachCoSinhVienResponse> getKeHoachByTrangThai(Integer trangThai);
} 