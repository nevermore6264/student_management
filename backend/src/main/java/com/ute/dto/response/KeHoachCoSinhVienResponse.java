package com.ute.dto.response;

import lombok.Data;

@Data
public class KeHoachCoSinhVienResponse {
    private String maSinhVien;
    private String hoTenSinhVien;
    private Integer hocKyDuKien;
    private String namHocDuKien;
    private Integer trangThai;
} 