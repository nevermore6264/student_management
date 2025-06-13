package com.ute.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhienDangKyId implements java.io.Serializable {
    private Integer maPhienDK;
    private String maSinhVien;
}
