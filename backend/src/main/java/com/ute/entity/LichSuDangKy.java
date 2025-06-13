package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "LICHSUDANGKY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuDangKy {
    @EmbeddedId
    private LichSuDangKyId id;

    @ManyToOne
    @MapsId("maSinhVien")
    @JoinColumn(name = "maSinhVien")
    private SinhVien sinhVien;

    @ManyToOne
    @MapsId("maLopHP")
    @JoinColumn(name = "maLopHP")
    private LopHocPhan lopHocPhan;

    @Column(name = "ngayGioDangKy")
    private LocalDateTime ngayGioDangKy;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @Column(name = "ghiChu", length = 200)
    private String ghiChu;
}
