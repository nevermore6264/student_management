package com.ute.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonManagedReference
    private List<Lop> lops;

    @OneToMany(mappedBy = "khoa")
    private List<HocPhan> hocPhans;

    @OneToMany(mappedBy = "khoa")
    @JsonManagedReference
    private List<GiangVien> giangViens;

    @OneToMany(mappedBy = "khoa")
    private List<DotDangKy> dotDangKys;
} 