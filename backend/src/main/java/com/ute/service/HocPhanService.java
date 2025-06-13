package com.ute.service;

import java.util.List;

import com.ute.entity.HocPhan;

public interface HocPhanService {
    List<HocPhan> getAllHocPhan();
    HocPhan getHocPhanById(String id);
    List<HocPhan> getHocPhanByKhoa(String maKhoa);
    HocPhan createHocPhan(HocPhan hocPhan);
    HocPhan updateHocPhan(String id, HocPhan hocPhan);
    void deleteHocPhan(String id);
} 