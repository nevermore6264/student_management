package com.ute.dto.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AdminReportResponse {
    
    // Thống kê tổng quan
    private ThongKeTongQuan thongKeTongQuan;
    
    // Thống kê theo khoa
    private List<ThongKeTheoKhoa> thongKeTheoKhoa;
    
    // Thống kê điểm theo phân bố
    private PhanBoDiem phanBoDiem;
    
    // Top giảng viên có nhiều lớp nhất
    private List<TopGiangVien> topGiangVien;
    
    // Top sinh viên có điểm cao nhất
    private List<TopSinhVien> topSinhVien;
    
    // Thống kê đăng ký học phần
    private ThongKeDangKy thongKeDangKy;
    
    // Thống kê theo học kỳ
    private List<ThongKeTheoHocKy> thongKeTheoHocKy;

    @Data
    public static class ThongKeTongQuan {
        private int tongSoSinhVien;
        private int tongSoGiangVien;
        private int tongSoLopHocPhan;
        private int tongSoHocPhan;
        private int tongSoKhoa;
        private int tongSoLop;
        private double tyLeDangKyTrungBinh;
        private double diemTrungBinhToanTruong;
    }

    @Data
    public static class ThongKeTheoKhoa {
        private String maKhoa;
        private String tenKhoa;
        private int soSinhVien;
        private int soGiangVien;
        private int soLopHocPhan;
        private int soHocPhan;
        private double diemTrungBinh;
        private double tyLeDangKy;
    }

    @Data
    public static class PhanBoDiem {
        private int soSinhVienDiemDuoi4;
        private int soSinhVienDiem4Den55;
        private int soSinhVienDiem55Den7;
        private int soSinhVienDiem7Den85;
        private int soSinhVienDiem85Den10;
        private Map<String, Integer> xepLoai;
    }

    @Data
    public static class TopGiangVien {
        private String maGiangVien;
        private String tenGiangVien;
        private String tenKhoa;
        private int soLopPhuTrach;
        private int tongSoSinhVien;
        private double diemTrungBinh;
    }

    @Data
    public static class TopSinhVien {
        private String maSinhVien;
        private String hoTenSinhVien;
        private String tenLop;
        private String tenKhoa;
        private double diemTrungBinh;
        private int soTinChiHoanThanh;
    }

    @Data
    public static class ThongKeDangKy {
        private int tongSoDangKy;
        private int soDangKyThanhCong;
        private int soDangKyThatBai;
        private double tyLeThanhCong;
        private Map<String, Integer> dangKyTheoHocPhan;
    }

    @Data
    public static class ThongKeTheoHocKy {
        private String hocKy;
        private String namHoc;
        private int soLopHocPhan;
        private int soSinhVienDangKy;
        private double diemTrungBinh;
        private double tyLeDangKy;
    }
}