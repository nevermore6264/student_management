package com.ute.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Mã xác thực không được để trống")
    private String code;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Size(min = 3, message = "Mật khẩu phải có ít nhất 3 ký tự")
    private String newPassword;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
} 
