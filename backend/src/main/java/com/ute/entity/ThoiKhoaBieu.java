package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "THOIKHOABIEU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThoiKhoaBieu {
    @Id
    @Column(name = "maTKB")
    private Integer maTKB;

    @ManyToOne
    @JoinColumn(name = "maLopHP")
    private LopHocPhan lopHocPhan;

    @Column(name = "thu")
    private Integer thu;

    @Column(name = "tietBatDau")
    private Integer tietBatDau;

    @Column(name = "soTiet")
    private Integer soTiet;

    @Column(name = "phongHoc", length = 20)
    private String phongHoc;
} 