package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "KHOA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Khoa {
    @Id
    @Column(name = "maKhoa", length = 20)
    private String maKhoa;

    @Column(name = "tenKhoa", length = 100)
    private String tenKhoa;

    @Column(name = "soDienThoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "diaChi", length = 200)
    private String diaChi;

    @Column(name = "maTruong", length = 15)
    private String maTruong;

    @Column(name = "trangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "khoa")
    private List<Lop> lops;

    @OneToMany(mappedBy = "khoa")
    private List<HocPhan> hocPhans;

    @OneToMany(mappedBy = "khoa")
    private List<GiangVien> giangViens;

    @OneToMany(mappedBy = "khoa")
    private List<DotDangKy> dotDangKys;
} 