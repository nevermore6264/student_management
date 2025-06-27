package com.ute.service;

import java.util.List;

import com.ute.dto.request.HocPhanRequest;
import com.ute.dto.response.HocPhanResponse;

public interface HocPhanService {
    List<HocPhanResponse> getAllHocPhan();
    HocPhanResponse getHocPhanById(String id);
    List<HocPhanResponse> getHocPhanByKhoa(String maKhoa);
    HocPhanResponse createHocPhan(HocPhanRequest request);
    HocPhanResponse updateHocPhan(String id, HocPhanRequest request);
    void deleteHocPhan(String id);
} 