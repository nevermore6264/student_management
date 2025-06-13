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

import com.ute.entity.GiangVien;
import com.ute.service.GiangVienService;

@RestController
@RequestMapping("/api/giangvien")
@CrossOrigin(origins = "*")
public class GiangVienController {

    @Autowired
    private GiangVienService giangVienService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GiangVien>> getAllGiangVien() {
        return ResponseEntity.ok(giangVienService.getAllGiangVien());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<GiangVien> getGiangVienById(@PathVariable String id) {
        return ResponseEntity.ok(giangVienService.getGiangVienById(id));
    }

    @GetMapping("/khoa/{maKhoa}")
    public ResponseEntity<List<GiangVien>> getGiangVienByKhoa(@PathVariable String maKhoa) {
        return ResponseEntity.ok(giangVienService.getGiangVienByKhoa(maKhoa));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GiangVien> createGiangVien(@RequestBody GiangVien giangVien) {
        return ResponseEntity.ok(giangVienService.createGiangVien(giangVien));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<GiangVien> updateGiangVien(@PathVariable String id, @RequestBody GiangVien giangVien) {
        return ResponseEntity.ok(giangVienService.updateGiangVien(id, giangVien));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGiangVien(@PathVariable String id) {
        giangVienService.deleteGiangVien(id);
        return ResponseEntity.ok().build();
    }
} 