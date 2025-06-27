package com.ute.service;

import java.util.List;

import com.ute.dto.request.ThoiKhoaBieuRequest;
import com.ute.dto.response.ThoiKhoaBieuResponse;

public interface ThoiKhoaBieuService {
    List<ThoiKhoaBieuResponse> getAll();
    ThoiKhoaBieuResponse getById(Integer id);
    List<ThoiKhoaBieuResponse> getByLopHocPhan(String maLopHP);
    ThoiKhoaBieuResponse create(ThoiKhoaBieuRequest request);
    ThoiKhoaBieuResponse update(Integer id, ThoiKhoaBieuRequest request);
    void delete(Integer id);
} 