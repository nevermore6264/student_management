package com.ute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.NguoiDung;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    Optional<NguoiDung> findByEmail(String email);
    Boolean existsByEmail(String email);
} 