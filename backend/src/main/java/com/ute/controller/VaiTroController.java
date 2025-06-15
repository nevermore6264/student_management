package com.ute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ute.dto.response.ApiResponse;
import com.ute.entity.VaiTro;
import com.ute.service.VaiTroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vai-tro")
@CrossOrigin(origins = "*")
public class VaiTroController {

    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<VaiTro>> getAllVaiTro() {
        List<VaiTro> vaiTros = vaiTroService.getAllVaiTro();
        return ApiResponse.success("Lấy danh sách vai trò thành công", vaiTros);
    }

    @PutMapping("/{maVaiTro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<VaiTro> updateVaiTro(
            @PathVariable String maVaiTro,
            @Valid @RequestBody VaiTro vaiTro) {
        vaiTro.setMaVaiTro(maVaiTro);
        VaiTro updatedVaiTro = vaiTroService.updateVaiTro(vaiTro);
        return ApiResponse.success("Cập nhật vai trò thành công", updatedVaiTro);
    }
} 