package com.ute.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SINHVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVien {
    @Id
    @Column(name = "maSinhVien", length = 20)
    private String maSinhVien;

    @Column(name = "hoTenSinhVien", length = 100)
    private String hoTenSinhVien;

    @Column(name = "ngaySinh")
    private LocalDateTime ngaySinh;

    @Column(name = "gioiTinh")
    private Boolean gioiTinh;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "soDienThoai", length = 20)
    private String soDienThoai;

    @Column(name = "diaChi", length = 200)
    private String diaChi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLop")
    @JsonBackReference
    private Lop lop;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Diem> diems = new HashSet<>();

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<KeHoachCoSinhVien> keHoachCoSinhViens = new HashSet<>();

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PhienDangKy> phienDangKys = new HashSet<>();

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<LichSuDangKy> lichSuDangKys = new HashSet<>();
} 