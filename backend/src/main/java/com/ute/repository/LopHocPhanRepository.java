package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.LopHocPhan;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, String> {
    List<LopHocPhan> findByHocPhan_MaHocPhan(String maHocPhan);
    List<LopHocPhan> findByGiangVien(String giangVien);
} 