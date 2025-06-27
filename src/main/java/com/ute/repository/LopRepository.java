package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {
    List<Lop> findByKhoa_MaKhoa(String maKhoa);
} 