package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DOTDANGKY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DotDangKy {
    @Id
    @Column(name = "maDotDK", length = 20)
    private String maDotDK;

    @Column(name = "tenDotDK", length = 100)
    private String tenDotDK;

    @Column(name = "ngayGioBatDau")
    private LocalDateTime ngayGioBatDau;

    @Column(name = "ngayGioKetThuc")
    private LocalDateTime ngayGioKetThuc;

    @Column(name = "thoiGian")
    private Integer thoiGian;

    @ManyToOne
    @JoinColumn(name = "maKhoa")
    private Khoa khoa;

    @Column(name = "moTa", length = 500)
    private String moTa;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @OneToMany(mappedBy = "dotDangKy")
    private List<PhienDangKy> phienDangKys;
} 
