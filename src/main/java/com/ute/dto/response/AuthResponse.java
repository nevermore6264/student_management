package com.ute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String maNguoiDung;
    private String tenNguoiDung;
    private String email;
    private String[] vaiTros;
} 