package com.ute.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemId implements java.io.Serializable {
    private String maSinhVien;
    private String maLopHP;
}
