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

import com.ute.entity.LopHocPhan;
import com.ute.service.LopHocPhanService;

@RestController
@RequestMapping("/api/lophocphan")
@CrossOrigin(origins = "*")
public class LopHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @GetMapping
    public ResponseEntity<List<LopHocPhan>> getAllLopHocPhan() {
        return ResponseEntity.ok(lopHocPhanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LopHocPhan> getLopHocPhanById(@PathVariable String id) {
        return ResponseEntity.ok(lopHocPhanService.findById(id));
    }

    @GetMapping("/hocphan/{maHocPhan}")
    public ResponseEntity<List<LopHocPhan>> getLopHocPhanByHocPhan(@PathVariable String maHocPhan) {
        return ResponseEntity.ok(lopHocPhanService.findByHocPhan_MaHocPhan(maHocPhan));
    }

    @GetMapping("/giangvien/{maGiangVien}")
    public ResponseEntity<List<LopHocPhan>> getLopHocPhanByGiangVien(@PathVariable String maGiangVien) {
        return ResponseEntity.ok(lopHocPhanService.findByGiangVien(maGiangVien));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LopHocPhan> createLopHocPhan(@RequestBody LopHocPhan lopHocPhan) {
        return ResponseEntity.ok(lopHocPhanService.createLopHocPhan(lopHocPhan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LopHocPhan> updateLopHocPhan(@PathVariable String id, @RequestBody LopHocPhan lopHocPhan) {
        return ResponseEntity.ok(lopHocPhanService.updateLopHocPhan(id, lopHocPhan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLopHocPhan(@PathVariable String id) {
        lopHocPhanService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 