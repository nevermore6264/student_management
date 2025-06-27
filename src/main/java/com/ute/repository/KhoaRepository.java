package com.ute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.Khoa;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, String> {
} 