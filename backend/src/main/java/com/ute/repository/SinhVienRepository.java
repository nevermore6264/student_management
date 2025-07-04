package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ute.entity.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    List<SinhVien> findByLop_MaLop(String maLop);

    @Query("SELECT DISTINCT s FROM SinhVien s " +
           "LEFT JOIN FETCH s.lop l " +
           "LEFT JOIN FETCH l.khoa")
    List<SinhVien> findAllWithRelations();
} 