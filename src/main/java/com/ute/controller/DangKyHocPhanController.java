package com.ute.controller;

import com.ute.entity.DangKyHocPhan;
import com.ute.service.DangKyHocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dangky")
@CrossOrigin(origins = "*")
public class DangKyHocPhanController {

    @Autowired
    private DangKyHocPhanService dangKyHocPhanService;

    @GetMapping("/sinhvien/{maSinhVien}")
    @PreAuthorize("hasRole('ADMIN') or #maSinhVien == authentication.principal.maNguoiDung")
    public ResponseEntity<List<DangKyHocPhan>> getDangKyBySinhVien(@PathVariable String maSinhVien) {
        return ResponseEntity.ok(dangKyHocPhanService.getDangKyBySinhVien(maSinhVien));
    }

    @GetMapping("/lophp/{maLopHP}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DangKyHocPhan>> getDangKyByLopHP(@PathVariable String maLopHP) {
        return ResponseEntity.ok(dangKyHocPhanService.getDangKyByLopHP(maLopHP));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #dangKyHocPhan.sinhVien.maSinhVien == authentication.principal.maNguoiDung")
    public ResponseEntity<DangKyHocPhan> createDangKy(@RequestBody DangKyHocPhan dangKyHocPhan) {
        return ResponseEntity.ok(dangKyHocPhanService.createDangKy(dangKyHocPhan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DangKyHocPhan> updateDangKy(@PathVariable String id, @RequestBody DangKyHocPhan dangKyHocPhan) {
        return ResponseEntity.ok(dangKyHocPhanService.updateDangKy(id, dangKyHocPhan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDangKy(@PathVariable String id) {
        dangKyHocPhanService.deleteDangKy(id);
        return ResponseEntity.ok().build();
    }
} 
