package com.ute.service;

import java.util.List;

import com.ute.dto.request.LopRequest;
import com.ute.dto.response.LopResponse;

public interface LopService {
    List<LopResponse> getAllLop();
    LopResponse getLopById(String id);
    LopResponse createLop(LopRequest request);
    LopResponse updateLop(String id, LopRequest request);
    void deleteLop(String id);
    List<LopResponse> getLopByKhoa(String maKhoa);
} 