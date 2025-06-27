package com.ute.entity;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DANGKYHOCPHAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyHocPhan {
    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "maPhienDK", column = @Column(name = "maPhienDK")),
        @AttributeOverride(name = "maSinhVien", column = @Column(name = "maSinhVien")),
        @AttributeOverride(name = "maLopHP", column = @Column(name = "maLopHP"))
    })
    private DangKyHocPhanId id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "maPhienDK", referencedColumnName = "maPhienDK", insertable = false, updatable = false),
        @JoinColumn(name = "maSinhVien", referencedColumnName = "maSinhVien", insertable = false, updatable = false)
    })
    private PhienDangKy phienDangKy;

    @ManyToOne
    @JoinColumn(name = "maLopHP", referencedColumnName = "maLopHP", insertable = false, updatable = false)
    private LopHocPhan lopHocPhan;

    @Column(name = "thoiGianDangKy")
    private LocalDateTime thoiGianDangKy;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @Column(name = "ketQuaDangKy")
    private Integer ketQuaDangKy;
}
