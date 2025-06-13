package com.ute.controller;

import com.ute.entity.Khoa;
import com.ute.service.KhoaService;
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
@RequestMapping("/api/khoa")
@CrossOrigin(origins = "*")
public class KhoaController {

    @Autowired
    private KhoaService khoaService;

    @GetMapping
    public ResponseEntity<List<Khoa>> getAllKhoa() {
        return ResponseEntity.ok(khoaService.getAllKhoa());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Khoa> getKhoaById(@PathVariable String id) {
        return ResponseEntity.ok(khoaService.getKhoaById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Khoa> createKhoa(@RequestBody Khoa khoa) {
        return ResponseEntity.ok(khoaService.createKhoa(khoa));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Khoa> updateKhoa(@PathVariable String id, @RequestBody Khoa khoa) {
        return ResponseEntity.ok(khoaService.updateKhoa(id, khoa));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteKhoa(@PathVariable String id) {
        khoaService.deleteKhoa(id);
        return ResponseEntity.ok().build();
    }
} 
