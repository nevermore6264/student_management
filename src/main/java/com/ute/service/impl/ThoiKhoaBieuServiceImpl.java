package com.ute.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.dto.request.ThoiKhoaBieuRequest;
import com.ute.dto.response.ThoiKhoaBieuResponse;
import com.ute.entity.LopHocPhan;
import com.ute.entity.ThoiKhoaBieu;
import com.ute.repository.LopHocPhanRepository;
import com.ute.repository.ThoiKhoaBieuRepository;
import com.ute.service.ThoiKhoaBieuService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThoiKhoaBieuServiceImpl implements ThoiKhoaBieuService {

    private final ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    private final LopHocPhanRepository lopHocPhanRepository;

    @Override
    public List<ThoiKhoaBieuResponse> getAll() {
        return thoiKhoaBieuRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ThoiKhoaBieuResponse getById(Integer id) {
        ThoiKhoaBieu tkb = thoiKhoaBieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy TKB với mã: " + id));
        return mapToResponse(tkb);
    }

    @Override
    public List<ThoiKhoaBieuResponse> getByLopHocPhan(String maLopHP) {
        return thoiKhoaBieuRepository.findByLopHocPhan_MaLopHP(maLopHP).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ThoiKhoaBieuResponse create(ThoiKhoaBieuRequest request) {
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(request.getMaLopHP())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp học phần với mã: " + request.getMaLopHP()));
        ThoiKhoaBieu tkb = new ThoiKhoaBieu();
        tkb.setLopHocPhan(lopHocPhan);
        tkb.setThu(request.getThu());
        tkb.setTietBatDau(request.getTietBatDau());
        tkb.setSoTiet(request.getSoTiet());
        tkb.setPhongHoc(request.getPhongHoc());
        return mapToResponse(thoiKhoaBieuRepository.save(tkb));
    }

    @Override
    @Transactional
    public ThoiKhoaBieuResponse update(Integer id, ThoiKhoaBieuRequest request) {
        ThoiKhoaBieu tkb = thoiKhoaBieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy TKB với mã: " + id));
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(request.getMaLopHP())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp học phần với mã: " + request.getMaLopHP()));
        tkb.setLopHocPhan(lopHocPhan);
        tkb.setThu(request.getThu());
        tkb.setTietBatDau(request.getTietBatDau());
        tkb.setSoTiet(request.getSoTiet());
        tkb.setPhongHoc(request.getPhongHoc());
        return mapToResponse(thoiKhoaBieuRepository.save(tkb));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ThoiKhoaBieu tkb = thoiKhoaBieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy TKB với mã: " + id));
        thoiKhoaBieuRepository.delete(tkb);
    }

    private ThoiKhoaBieuResponse mapToResponse(ThoiKhoaBieu tkb) {
        ThoiKhoaBieuResponse response = new ThoiKhoaBieuResponse();
        response.setMaTKB(tkb.getMaTKB());
        response.setMaLopHP(tkb.getLopHocPhan() != null ? tkb.getLopHocPhan().getMaLopHP() : null);
        response.setThu(tkb.getThu());
        response.setTietBatDau(tkb.getTietBatDau());
        response.setSoTiet(tkb.getSoTiet());
        response.setPhongHoc(tkb.getPhongHoc());
        return response;
    }
} 