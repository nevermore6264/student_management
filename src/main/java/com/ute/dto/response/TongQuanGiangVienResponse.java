package com.ute.dto.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TongQuanGiangVienResponse {
    private int tongSoLop;
    private int tongSoSinhVien;
    private int tongSoHocPhan;
    private int soDiemDaNhap;
    private int soDiemChuaNhap;
    private double tyLeDat;
    private double tyLeKhongDat;
    private double diemTrungBinhTatCaLop;
    private Map<String, Integer> soLuongXepLoai;
    private List<BieuDoPhanBoDiem> bieuDoPhanBoDiem = new java.util.ArrayList<>();
    private List<LopInfo> cacLop = new java.util.ArrayList<>();
    private List<SinhVienChuaNhapDiem> sinhVienChuaNhapDiem = new java.util.ArrayList<>();

    @Data
    public static class BieuDoPhanBoDiem {
        private String khoangDiem;
        private int soLuong;
        public BieuDoPhanBoDiem() {}
        public BieuDoPhanBoDiem(String khoangDiem, int soLuong) {
            this.khoangDiem = khoangDiem;
            this.soLuong = soLuong;
        }
    }

    @Data
    public static class LopInfo {
        private String maLopHP;
        private String tenLopHP;
        private int soSinhVien;
        private String trangThaiNhapDiem;
    }

    @Data
    public static class SinhVienChuaNhapDiem {
        private String maSinhVien;
        private String tenSinhVien;
        private String maLopHP;
    }
} 