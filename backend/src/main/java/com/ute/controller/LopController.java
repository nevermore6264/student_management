package com.ute.controller;

import com.ute.entity.Lop;
import com.ute.service.LopService;
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
@RequestMapping("/api/lop")
@CrossOrigin(origins = "*")
public class LopController {

    @Autowired
    private LopService lopService;

    @GetMapping
    public ResponseEntity<List<Lop>> getAllLop() {
        return ResponseEntity.ok(lopService.getAllLop());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lop> getLopById(@PathVariable String id) {
        return ResponseEntity.ok(lopService.getLopById(id));
    }

    @GetMapping("/khoa/{maKhoa}")
    public ResponseEntity<List<Lop>> getLopByKhoa(@PathVariable String maKhoa) {
        return ResponseEntity.ok(lopService.getLopByKhoa(maKhoa));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lop> createLop(@RequestBody Lop lop) {
        return ResponseEntity.ok(lopService.createLop(lop));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lop> updateLop(@PathVariable String id, @RequestBody Lop lop) {
        return ResponseEntity.ok(lopService.updateLop(id, lop));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLop(@PathVariable String id) {
        lopService.deleteLop(id);
        return ResponseEntity.ok().build();
    }
} 
