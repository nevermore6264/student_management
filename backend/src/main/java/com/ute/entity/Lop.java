package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "LOP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lop {
    @Id
    @Column(name = "maLop", length = 20)
    private String maLop;

    @Column(name = "tenLop", length = 100)
    private String tenLop;

    @ManyToOne
    @JoinColumn(name = "maKhoa")
    private Khoa khoa;

    @Column(name = "khoaHoc", length = 20)
    private String khoaHoc;

    @Column(name = "heDaoTao", length = 20)
    private String heDaoTao;

    @OneToMany(mappedBy = "lop")
    private List<SinhVien> sinhViens;
} 