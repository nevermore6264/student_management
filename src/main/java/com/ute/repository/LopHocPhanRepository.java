package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.entity.LopHocPhan;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, String> {
    List<LopHocPhan> findByHocPhan_MaHocPhan(String maHocPhan);
    List<LopHocPhan> findByGiangVien(String giangVien);
    
    @Query("SELECT lhp FROM LopHocPhan lhp WHERE lhp.hocPhan.maHocPhan IN :maHocPhanList")
    List<LopHocPhan> findByHocPhan_MaHocPhanIn(@Param("maHocPhanList") List<String> maHocPhanList);
    
    @Query("SELECT DISTINCT lhp FROM LopHocPhan lhp " +
           "JOIN lhp.dangKyHocPhans dkhp " +
           "JOIN dkhp.phienDangKy pdk " +
           "WHERE pdk.dotDangKy.maDotDK = :maDotDK")
    List<LopHocPhan> findByDotDangKy_MaDotDK(@Param("maDotDK") String maDotDK);
} 