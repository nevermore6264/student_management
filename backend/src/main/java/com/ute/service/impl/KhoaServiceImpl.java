package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.KhoaRequest;
import com.ute.dto.response.KhoaResponse;
import com.ute.entity.Khoa;
import com.ute.repository.KhoaRepository;
import com.ute.service.KhoaService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KhoaServiceImpl implements KhoaService {
    private final KhoaRepository khoaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> getAllKhoa() {
        return khoaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KhoaResponse getKhoaById(String id) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));
        return mapToResponse(khoa);
    }

    @Override
    @Transactional
    public KhoaResponse createKhoa(KhoaRequest request) {
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(request.getMaKhoa());
        khoa.setTenKhoa(request.getTenKhoa());
        return mapToResponse(khoaRepository.save(khoa));
    }

    @Override
    @Transactional
    public KhoaResponse updateKhoa(String id, KhoaRequest request) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));
        khoa.setTenKhoa(request.getTenKhoa());
        return mapToResponse(khoaRepository.save(khoa));
    }

    @Override
    @Transactional
    public void deleteKhoa(String id) {
        Khoa khoa = khoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + id));
        khoaRepository.delete(khoa);
    }

    private KhoaResponse mapToResponse(Khoa khoa) {
        KhoaResponse response = new KhoaResponse();
        response.setMaKhoa(khoa.getMaKhoa());
        response.setTenKhoa(khoa.getTenKhoa());
        return response;
    }
} 