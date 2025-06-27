package com.ute.service;

import java.util.List;

import com.ute.dto.request.KhoaRequest;
import com.ute.dto.response.KhoaResponse;

public interface KhoaService {
    List<KhoaResponse> getAllKhoa();
    KhoaResponse getKhoaById(String id);
    KhoaResponse createKhoa(KhoaRequest request);
    KhoaResponse updateKhoa(String id, KhoaRequest request);
    void deleteKhoa(String id);
} 