package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "maLop")
    private Lop lop;

    @OneToMany(mappedBy = "sinhVien")
    private List<Diem> diems;

    @OneToMany(mappedBy = "sinhVien")
    private List<KeHoachCoSinhVien> keHoachCoSinhViens;

    @OneToMany(mappedBy = "sinhVien")
    private List<PhienDangKy> phienDangKys;

    @OneToMany(mappedBy = "sinhVien")
    private List<LichSuDangKy> lichSuDangKys;
} 