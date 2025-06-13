package com.ute.service;

import com.ute.dto.request.RegisterRequest;
import com.ute.entity.NguoiDung;

public interface AuthService {
    NguoiDung registerUser(RegisterRequest registerRequest);
    NguoiDung findByEmail(String email);
} 