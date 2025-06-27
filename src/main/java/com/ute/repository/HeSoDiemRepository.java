package com.ute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.HeSoDiem;

@Repository
public interface HeSoDiemRepository extends JpaRepository<HeSoDiem, String> {
} 