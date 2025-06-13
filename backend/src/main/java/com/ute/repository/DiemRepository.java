package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.Diem;

@Repository
public interface DiemRepository extends JpaRepository<Diem, String> {
    List<Diem> findBySinhVien_MaSinhVien(String maSinhVien);
    List<Diem> findByLopHocPhan_MaLopHP(String maLopHP);
} 