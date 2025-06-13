package com.ute.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHIENDANGKY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhienDangKy {

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "maPhienDK", column = @Column(name = "maPhienDK")),
        @AttributeOverride(name = "maSinhVien", column = @Column(name = "maSinhVien"))
    })
    private PhienDangKyId id;

    @ManyToOne
    @MapsId("maSinhVien")
    @JoinColumn(name = "maSinhVien", insertable = false, updatable = false)
    private SinhVien sinhVien;

    @Column(name = "ngayGioBatDau")
    private LocalDateTime ngayGioBatDau;

    @Column(name = "thoiGian")
    private Integer thoiGian;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "maDotDK")
    private DotDangKy dotDangKy;

    @OneToMany(mappedBy = "phienDangKy")
    private List<DangKyHocPhan> dangKyHocPhans;
}
