package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.DangKyHocPhan;

@Repository
public interface DangKyHocPhanRepository extends JpaRepository<DangKyHocPhan, String> {
    List<DangKyHocPhan> findByPhienDangKy_SinhVien_MaSinhVien(String maSinhVien);
    List<DangKyHocPhan> findByLopHocPhan_MaLopHP(String maLopHP);
    List<DangKyHocPhan> findByLopHocPhan_HocPhan_MaHocPhan(String maHocPhan);
} 