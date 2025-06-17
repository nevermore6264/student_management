package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.LopRequest;
import com.ute.dto.response.LopResponse;
import com.ute.entity.Khoa;
import com.ute.entity.Lop;
import com.ute.repository.KhoaRepository;
import com.ute.repository.LopRepository;
import com.ute.service.LopService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LopServiceImpl implements LopService {
    private final LopRepository lopRepository;
    private final KhoaRepository khoaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LopResponse> getAllLop() {
        return lopRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LopResponse getLopById(String id) {
        Lop lop = lopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp với mã: " + id));
        return mapToResponse(lop);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LopResponse> getLopByKhoa(String maKhoa) {
        return lopRepository.findByKhoa_MaKhoa(maKhoa).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LopResponse createLop(LopRequest request) {
        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));
        Lop lop = new Lop();
        lop.setMaLop(request.getMaLop());
        lop.setTenLop(request.getTenLop());
        lop.setKhoa(khoa);
        return mapToResponse(lopRepository.save(lop));
    }

    @Override
    @Transactional
    public LopResponse updateLop(String id, LopRequest request) {
        Lop lop = lopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp với mã: " + id));
        Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khoa với mã: " + request.getMaKhoa()));
        lop.setTenLop(request.getTenLop());
        lop.setKhoa(khoa);
        return mapToResponse(lopRepository.save(lop));
    }

    @Override
    @Transactional
    public void deleteLop(String id) {
        Lop lop = lopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp với mã: " + id));

        // Xử lý các sinh viên liên quan
        if (lop.getSinhViens() != null) {
            lop.getSinhViens().forEach(sv -> sv.setLop(null));
        }

        // Xóa lớp
        lopRepository.delete(lop);
    }

    private LopResponse mapToResponse(Lop lop) {
        LopResponse response = new LopResponse();
        response.setMaLop(lop.getMaLop());
        response.setTenLop(lop.getTenLop());
        if (lop.getKhoa() != null) {
            response.setMaKhoa(lop.getKhoa().getMaKhoa());
            response.setTenKhoa(lop.getKhoa().getTenKhoa());
        }
        return response;
    }
} 
