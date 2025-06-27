package com.ute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiemFullInfoResponse {
    private String maSinhVien;
    private String hoTenSinhVien;
    private String maHocPhan;
    private String tenHocPhan;
    private String maLopHP;
    private Float diemGiuaKy;
    private Float diemCuoiKy;
    private Float diemTongKet;
    private String diemChu;
    private String trangThai;
} 