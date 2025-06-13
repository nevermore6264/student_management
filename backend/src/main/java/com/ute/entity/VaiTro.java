package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VAITRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaiTro {
    @Id
    @Column(name = "maVaiTro", length = 10)
    private String maVaiTro;

    @Column(name = "tenVaiTro", length = 50)
    private String tenVaiTro;

    @Column(name = "moTa", length = 100)
    private String moTa;
} 