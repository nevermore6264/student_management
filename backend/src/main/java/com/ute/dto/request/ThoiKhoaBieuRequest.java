package com.ute.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ThoiKhoaBieuRequest {
    @NotNull
    private String maLopHP;
    @NotNull
    private Integer thu;
    @NotNull
    private Integer tietBatDau;
    @NotNull
    private Integer soTiet;
    @NotNull
    private String phongHoc;
} 