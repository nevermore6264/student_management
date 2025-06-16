package com.ute.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonBackReference
    private Khoa khoa;

    @Column(name = "khoaHoc", length = 20)
    private String khoaHoc;

    @Column(name = "heDaoTao", length = 20)
    private String heDaoTao;

    @OneToMany(mappedBy = "lop")
    @JsonManagedReference
    private List<SinhVien> sinhViens;
} 