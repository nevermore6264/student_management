package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ute.entity.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
    List<GiangVien> findByKhoa_MaKhoa(String maKhoa);

    @Query("SELECT DISTINCT g FROM GiangVien g " +
           "LEFT JOIN FETCH g.khoa")
    List<GiangVien> findAllWithRelations();
} 