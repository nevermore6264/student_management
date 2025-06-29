package com.ute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ute.entity.DotDangKy;

@Repository
public interface DotDangKyRepository extends JpaRepository<DotDangKy, String> {
    @Query("SELECT d FROM DotDangKy d WHERE d.trangThai = true ORDER BY d.ngayGioBatDau DESC")
    java.util.List<DotDangKy> findCurrentDotDangKy();
    
    @Query("SELECT d FROM DotDangKy d WHERE d.trangThai = true ORDER BY d.ngayGioBatDau DESC")
    java.util.Optional<DotDangKy> findFirstCurrentDotDangKy();
    
    DotDangKy findByMaDotDK(String maDotDK);
} 