package com.ute.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ute.dto.response.DiemFullInfoResponse;
import com.ute.entity.Diem;
import com.ute.entity.DiemId;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemId> {
    List<Diem> findBySinhVien_MaSinhVien(String maSinhVien);
    List<Diem> findByLopHocPhan_MaLopHP(String maLopHP);

    Optional<List<Diem>> findBySinhVienMaSinhVien(String maSinhVien);
    Optional<List<Diem>> findByLopHocPhanMaLopHP(String maLopHP);

    @Query("SELECT d FROM Diem d WHERE d.lopHocPhan.maLopHP IN :maLopHPList")
    List<Diem> findByLopHocPhan_MaLopHPIn(@Param("maLopHPList") List<String> maLopHPList);

    @Query("SELECT d FROM Diem d WHERE d.sinhVien.maSinhVien = :maSinhVien AND d.lopHocPhan.hocPhan.maHocPhan = :maHocPhan")
    List<Diem> findBySinhVienAndHocPhan(@Param("maSinhVien") String maSinhVien, @Param("maHocPhan") String maHocPhan);

    @Query("SELECT new com.ute.dto.response.DiemFullInfoResponse(s.maSinhVien, s.hoTenSinhVien, hp.maHocPhan, hp.tenHocPhan, lhp.maLopHP, d.diemGiuaKy, d.diemCuoiKy, d.diemTongKet, '', '') " +
            "FROM Diem d " +
            "JOIN d.sinhVien s " +
            "JOIN d.lopHocPhan lhp " +
            "JOIN lhp.hocPhan hp")
    List<DiemFullInfoResponse> findAllDiemFullInfo();
} 
