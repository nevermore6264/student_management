package com.ute.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT new com.ute.dto.response.DiemFullInfoResponse(s.maSinhVien, s.hoTenSinhVien, hp.maHocPhan, hp.tenHocPhan, lhp.maLopHP, d.diemGiuaKy, d.diemCuoiKy, d.diemTongKet, '', '') " +
            "FROM Diem d " +
            "JOIN d.sinhVien s " +
            "JOIN d.lopHocPhan lhp " +
            "JOIN lhp.hocPhan hp")
    List<DiemFullInfoResponse> findAllDiemFullInfo();
} 
