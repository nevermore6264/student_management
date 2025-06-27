package com.ute.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DIEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diem {
    @EmbeddedId
    private DiemId id;

    @ManyToOne
    @MapsId("maSinhVien")
    @JoinColumn(name = "maSinhVien")
    private SinhVien sinhVien;

    @ManyToOne
    @MapsId("maLopHP")
    @JoinColumn(name = "maLopHP")
    private LopHocPhan lopHocPhan;

    @Column(name = "diemChuyenCan")
    private Float diemChuyenCan;

    @Column(name = "diemGiuaKy")
    private Float diemGiuaKy;

    @Column(name = "diemCuoiKy")
    private Float diemCuoiKy;

    @Column(name = "diemTongKet")
    private Float diemTongKet;

    @Column(name = "ghiChu", length = 200)
    private String ghiChu;
}
