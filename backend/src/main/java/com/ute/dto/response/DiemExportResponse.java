package com.ute.dto.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DiemExportResponse {
    @ExcelProperty("Mã SV")
    private String maSinhVien;

    @ExcelProperty("Họ tên")
    private String hoTenSinhVien;

    @ExcelProperty("Chuyên cần")
    private Float diemChuyenCan;

    @ExcelProperty("Giữa kỳ")
    private Float diemGiuaKy;

    @ExcelProperty("Cuối kỳ")
    private Float diemCuoiKy;

    @ExcelProperty("Tổng kết")
    private Float diemTongKet;

    @ExcelProperty("Ghi chú")
    private String ghiChu;
}
