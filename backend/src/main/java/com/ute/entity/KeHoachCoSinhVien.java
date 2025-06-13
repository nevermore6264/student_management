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
@Table(name = "KEHOACHCOSINHVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachCoSinhVien {
    @EmbeddedId
    private KeHoachCoSinhVienId id;

    @ManyToOne
    @MapsId("maSinhVien")
    @JoinColumn(name = "maSinhVien")
    private SinhVien sinhVien;

    @ManyToOne
    @MapsId("maHocPhan")
    @JoinColumn(name = "maHocPhan")
    private HocPhan hocPhan;

    @Column(name = "hocKyDuKien")
    private Integer hocKyDuKien;

    @Column(name = "namHocDuKien", length = 20)
    private String namHocDuKien;

    @Column(name = "trangThai")
    private Integer trangThai;
}