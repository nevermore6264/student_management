package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GIANGVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiangVien {
    @Id
    @Column(name = "maGiangVien", length = 20)
    private String maGiangVien;

    @Column(name = "tenGiangVien", length = 100)
    private String tenGiangVien;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "soDienThoai", length = 20)
    private String soDienThoai;

    @ManyToOne
    @JoinColumn(name = "maKhoa")
    private Khoa khoa;
} 