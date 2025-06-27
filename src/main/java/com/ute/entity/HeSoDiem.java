package com.ute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HESODIEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeSoDiem {
    @Id
    @Column(name = "maHocPhan", length = 20)
    private String maHocPhan;

    @OneToOne
    @MapsId
    @JoinColumn(name = "maHocPhan")
    private HocPhan hocPhan;

    @Column(name = "heSoChuyenCan")
    private Float heSoChuyenCan;

    @Column(name = "heSoGiuaKy")
    private Float heSoGiuaKy;

    @Column(name = "heSoCuoiKy")
    private Float heSoCuoiKy;
} 