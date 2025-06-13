package com.ute.controller;

import com.ute.entity.HocPhan;
import com.ute.service.HocPhanService;
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
@RequestMapping("/api/hocphan")
@CrossOrigin(origins = "*")
public class HocPhanController {

    @Autowired
    private HocPhanService hocPhanService;

    @GetMapping
    public ResponseEntity<List<HocPhan>> getAllHocPhan() {
        return ResponseEntity.ok(hocPhanService.getAllHocPhan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HocPhan> getHocPhanById(@PathVariable String id) {
        return ResponseEntity.ok(hocPhanService.getHocPhanById(id));
    }

    @GetMapping("/khoa/{maKhoa}")
    public ResponseEntity<List<HocPhan>> getHocPhanByKhoa(@PathVariable String maKhoa) {
        return ResponseEntity.ok(hocPhanService.getHocPhanByKhoa(maKhoa));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HocPhan> createHocPhan(@RequestBody HocPhan hocPhan) {
        return ResponseEntity.ok(hocPhanService.createHocPhan(hocPhan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HocPhan> updateHocPhan(@PathVariable String id, @RequestBody HocPhan hocPhan) {
        return ResponseEntity.ok(hocPhanService.updateHocPhan(id, hocPhan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHocPhan(@PathVariable String id) {
        hocPhanService.deleteHocPhan(id);
        return ResponseEntity.ok().build();
    }
} 
