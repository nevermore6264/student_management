package com.ute.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class DiemTongQuanLopResponse {
    private String maLopHP;
    private String tenLopHP;
    private String tenHocPhan;
    private Integer soTinChi;
    private Integer tongSoSinhVien;
    private Integer soSinhVienCoDiem;
    private Double diemTrungBinhLop;
    private Double diemCaoNhat;
    private Double diemThapNhat;
    private Integer soSinhVienDat;
    private Integer soSinhVienKhongDat;
    private List<ThongKeDiemResponse> thongKeDiem;
    
    @Data
    public static class ThongKeDiemResponse {
        private String khoangDiem;
        private Integer soLuong;
        private Double tyLe;
    }
} 