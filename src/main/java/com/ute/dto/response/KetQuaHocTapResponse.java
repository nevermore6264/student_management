package com.ute.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class KetQuaHocTapResponse {
    // Thông tin sinh viên
    private String maSinhVien;
    private String hoTenSinhVien;
    
    // Tổng quan
    private double diemTrungBinh;
    private String xepLoai;
    private int tongSoTinChi;
    private int tinChiDat;
    private double tyLeDat;
    
    // Tiến độ học tập
    private int tinChiDaDat;
    private double tyLeHoanThanh;
    
    // Chi tiết điểm từng môn học
    private List<ChiTietDiemResponse> chiTietDiem;
    
    @Data
    public static class ChiTietDiemResponse {
        private String maHocPhan;
        private String tenHocPhan;
        private int soTinChi;
        private Float diemQuaTrinh; // Điểm quá trình (chuyên cần + giữa kỳ)
        private Float diemThi; // Điểm thi cuối kỳ
        private Float diemTongKet;
        private String diemChu;
        private String trangThai; // Đạt/Không đạt
    }
} 