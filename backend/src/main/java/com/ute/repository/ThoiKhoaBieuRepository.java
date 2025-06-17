package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.ThoiKhoaBieu;

@Repository
public interface ThoiKhoaBieuRepository extends JpaRepository<ThoiKhoaBieu, Integer> {
    List<ThoiKhoaBieu> findByLopHocPhan_MaLopHP(String maLopHP);
} 