package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LOPHOCPHAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhan {
    @Id
    @Column(name = "maLopHP", length = 20)
    private String maLopHP;

    @ManyToOne
    @JoinColumn(name = "maHocPhan")
    private HocPhan hocPhan;

    @Column(name = "tenLopHP", length = 100)
    private String tenLopHP;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "giangVien", length = 100)
    private String giangVien;

    @Column(name = "thoiGianBatDau")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "thoiGianKetThuc")
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "phongHoc", length = 20)
    private String phongHoc;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @OneToMany(mappedBy = "lopHocPhan")
    private List<Diem> diems;

    @OneToMany(mappedBy = "lopHocPhan")
    private List<DangKyHocPhan> dangKyHocPhans;

    @OneToMany(mappedBy = "lopHocPhan")
    private List<ThoiKhoaBieu> thoiKhoaBieus;

    @OneToMany(mappedBy = "lopHocPhan")
    private List<LichSuDangKy> lichSuDangKys;
} 