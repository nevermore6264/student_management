package com.ute.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachCoSinhVienId implements java.io.Serializable {
    private Integer maKeHoach;
    private String maSinhVien;
    private String maHocPhan;
} 