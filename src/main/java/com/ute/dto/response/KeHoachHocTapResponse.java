package com.ute.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class KeHoachHocTapResponse {
    private String maSinhVien;
    private String hoTenSinhVien;
    private int tongSoHocPhan;
    private int tongSoTinChi;
    private List<HocPhanKeHoach> keHoachTheoHocKy;
    
    @Data
    public static class HocPhanKeHoach {
        private String maHocPhan;
        private String tenHocPhan;
        private int soTinChi;
        private int hocKyDuKien;
        private String namHocDuKien;
        private String trangThai; // "Đã học", "Đang học", "Chưa học"
        private String trangThaiChiTiet; // "Hoàn thành", "Đang học", "Chưa đăng ký"
        private Float diemTongKet; // Điểm nếu đã học
        private String xepLoai; // Xếp loại nếu đã học
    }
} 