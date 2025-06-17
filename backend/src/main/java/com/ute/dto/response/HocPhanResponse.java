package com.ute.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class HocPhanResponse {
    private String maHocPhan;
    private String tenHocPhan;
    private Integer soTinChi;
    private String maKhoa;
    private String tenKhoa;
    private List<LopHocPhanResponse> lopHocPhans;
    private List<KeHoachCoSinhVienResponse> keHoachCoSinhViens;
    private HeSoDiemResponse heSoDiem;
} 