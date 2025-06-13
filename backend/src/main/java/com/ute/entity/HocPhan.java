package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "HOCPHAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocPhan {
    @Id
    @Column(name = "maHocPhan", length = 20)
    private String maHocPhan;

    @Column(name = "tenHocPhan", length = 100)
    private String tenHocPhan;

    @Column(name = "soTinChi")
    private Integer soTinChi;

    @ManyToOne
    @JoinColumn(name = "maKhoa")
    private Khoa khoa;

    @OneToMany(mappedBy = "hocPhan")
    private List<LopHocPhan> lopHocPhans;

    @OneToMany(mappedBy = "hocPhan")
    private List<KeHoachCoSinhVien> keHoachCoSinhViens;

    @OneToOne(mappedBy = "hocPhan")
    private HeSoDiem heSoDiem;
} 