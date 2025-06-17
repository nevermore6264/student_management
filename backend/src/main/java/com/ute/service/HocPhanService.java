package com.ute.service;

import java.util.List;

import com.ute.dto.response.HocPhanResponse;
import com.ute.entity.HocPhan;

public interface HocPhanService {
    List<HocPhanResponse> getAllHocPhan();
    HocPhanResponse getHocPhanById(String id);
    List<HocPhanResponse> getHocPhanByKhoa(String maKhoa);
    HocPhan createHocPhan(HocPhan hocPhan);
    HocPhan updateHocPhan(String id, HocPhan hocPhan);
    void deleteHocPhan(String id);
} 