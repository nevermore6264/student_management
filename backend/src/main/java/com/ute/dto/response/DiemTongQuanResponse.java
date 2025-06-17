package com.ute.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class DiemTongQuanResponse {
    private int tongSoTinChi;
    private double diemTrungBinh;
    private String xepLoai;
    private int soHocPhan;
    private List<TongKetHocKyResponse> tongKetTheoHocKy;
} 