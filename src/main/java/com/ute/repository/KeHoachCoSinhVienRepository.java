package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.KeHoachCoSinhVien;
import com.ute.entity.KeHoachCoSinhVienId;

@Repository
public interface KeHoachCoSinhVienRepository extends JpaRepository<KeHoachCoSinhVien, KeHoachCoSinhVienId> {
    List<KeHoachCoSinhVien> findByHocPhan_MaHocPhan(String maHocPhan);
} 