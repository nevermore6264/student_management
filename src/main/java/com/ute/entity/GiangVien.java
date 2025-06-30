package com.ute.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "giangvien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiangVien {
    @Id
    @Column(name = "maGiangVien")
    private String maGiangVien;

    @Column(name = "tenGiangVien")
    private String tenGiangVien;

    @Column(name = "email")
    private String email;

    @Column(name = "soDienThoai")
    private String soDienThoai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKhoa")
    @JsonBackReference
    private Khoa khoa;
} 
