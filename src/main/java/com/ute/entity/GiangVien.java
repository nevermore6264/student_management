package com.ute.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

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
