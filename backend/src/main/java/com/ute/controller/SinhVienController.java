package com.ute.controller;

import java.util.List;

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

import com.ute.entity.SinhVien;
import com.ute.service.SinhVienService;

@RestController
@RequestMapping("/api/sinhvien")
@CrossOrigin(origins = "*")
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SinhVien>> getAllSinhVien() {
        return ResponseEntity.ok(sinhVienService.getAllSinhVien());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<SinhVien> getSinhVienById(@PathVariable String id) {
        return ResponseEntity.ok(sinhVienService.getSinhVienById(id));
    }

    @GetMapping("/lop/{maLop}")
    public ResponseEntity<List<SinhVien>> getSinhVienByLop(@PathVariable String maLop) {
        return ResponseEntity.ok(sinhVienService.getSinhVienByLop(maLop));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SinhVien> createSinhVien(@RequestBody SinhVien sinhVien) {
        return ResponseEntity.ok(sinhVienService.createSinhVien(sinhVien));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<SinhVien> updateSinhVien(@PathVariable String id, @RequestBody SinhVien sinhVien) {
        return ResponseEntity.ok(sinhVienService.updateSinhVien(id, sinhVien));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSinhVien(@PathVariable String id) {
        sinhVienService.deleteSinhVien(id);
        return ResponseEntity.ok().build();
    }
} 