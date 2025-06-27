package com.ute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ute.entity.PhienDangKy;
import com.ute.entity.PhienDangKyId;

@Repository
public interface PhienDangKyRepository extends JpaRepository<PhienDangKy, PhienDangKyId> {
    // Có thể thêm các method tùy chỉnh nếu cần
} 