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

import com.ute.entity.Diem;
import com.ute.service.DiemService;

@RestController
@RequestMapping("/api/diem")
@CrossOrigin(origins = "*")
public class DiemController {

    @Autowired
    private DiemService diemService;

    @GetMapping
    public ResponseEntity<List<Diem>> getAllDiem() {
        return ResponseEntity.ok(diemService.getAllDiem());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diem> getDiemById(@PathVariable String id) {
        return ResponseEntity.ok(diemService.getDiemById(id));
    }

    @GetMapping("/sinhvien/{maSinhVien}")
    public ResponseEntity<List<Diem>> getDiemBySinhVien(@PathVariable String maSinhVien) {
        return ResponseEntity.ok(diemService.getDiemBySinhVien(maSinhVien));
    }

    @GetMapping("/lophocphan/{maLopHP}")
    public ResponseEntity<List<Diem>> getDiemByLopHocPhan(@PathVariable String maLopHP) {
        return ResponseEntity.ok(diemService.getDiemByLopHocPhan(maLopHP));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ResponseEntity<Diem> createDiem(@RequestBody Diem diem) {
        return ResponseEntity.ok(diemService.createDiem(diem));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GIANGVIEN')")
    public ResponseEntity<Diem> updateDiem(@PathVariable String id, @RequestBody Diem diem) {
        return ResponseEntity.ok(diemService.updateDiem(id, diem));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDiem(@PathVariable String id) {
        diemService.deleteDiem(id);
        return ResponseEntity.ok().build();
    }
} 