package com.ute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.VaiTro;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, String> {
    Optional<VaiTro> findByMaVaiTro(String maVaiTro);
} 