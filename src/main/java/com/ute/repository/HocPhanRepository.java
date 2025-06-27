package com.ute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ute.entity.HocPhan;

@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, String> {
    List<HocPhan> findByKhoa_MaKhoa(String maKhoa);

    @Query("SELECT DISTINCT h FROM HocPhan h " +
           "LEFT JOIN FETCH h.khoa " +
           "LEFT JOIN FETCH h.heSoDiem")
    List<HocPhan> findAllWithRelations();

    @Query("SELECT DISTINCT h FROM HocPhan h " +
           "LEFT JOIN FETCH h.lopHocPhans " +
           "WHERE h.maHocPhan = :maHocPhan")
    HocPhan findByIdWithLopHocPhans(String maHocPhan);

    @Query("SELECT DISTINCT h FROM HocPhan h " +
           "LEFT JOIN FETCH h.keHoachCoSinhViens " +
           "WHERE h.maHocPhan = :maHocPhan")
    HocPhan findByIdWithKeHoachCoSinhViens(String maHocPhan);
} 