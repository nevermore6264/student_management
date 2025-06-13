package com.ute.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "nguoidung")
public class NguoiDung {
    @Id
    private String maNguoiDung;

    private String tenNguoiDung;

    @Column(name = "email")
    private String email;

    private String matKhau;

    private String soDienThoai;

    private String diaChi;

    private Boolean trangThai;

    private LocalDateTime ngayTao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "nguoidungvaitro",
        joinColumns = @JoinColumn(name = "maNguoiDung"),
        inverseJoinColumns = @JoinColumn(name = "maVaiTro")
    )
    private Set<VaiTro> vaiTros = new HashSet<>();
} 
