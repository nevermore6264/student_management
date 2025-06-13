package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.HocPhan;

@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, String> {
    List<HocPhan> findByKhoa_MaKhoa(String maKhoa);
} 