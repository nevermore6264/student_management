package com.travelhubvn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private int statusCode;
    private boolean success;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(message, data, 200, true);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(message, null, 200, true);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(message, null, statusCode, false);
    }
}
