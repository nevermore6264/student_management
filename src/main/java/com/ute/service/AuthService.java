package com.ute.service;

import com.ute.dto.request.ForgotPasswordRequest;
import com.ute.dto.request.RegisterRequest;
import com.ute.dto.request.ResetPasswordRequest;
import com.ute.entity.NguoiDung;

public interface AuthService {
    NguoiDung registerUser(RegisterRequest registerRequest);
    NguoiDung findByEmail(String email);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
} 