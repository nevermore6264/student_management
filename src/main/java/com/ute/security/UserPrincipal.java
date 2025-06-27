package com.ute.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ute.entity.NguoiDung;

public class UserPrincipal implements UserDetails {
    private String maNguoiDung;
    private String tenNguoiDung;
    private String email;

    @JsonIgnore
    private String matKhau;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String maNguoiDung, String tenNguoiDung, String email, String matKhau, Collection<? extends GrantedAuthority> authorities) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.matKhau = matKhau;
        this.authorities = authorities;
    }

    public static UserPrincipal create(NguoiDung nguoiDung) {
        List<GrantedAuthority> authorities = nguoiDung.getVaiTros().stream()
                .map(vaiTro -> new SimpleGrantedAuthority(vaiTro.getMaVaiTro()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                nguoiDung.getMaNguoiDung(),
                nguoiDung.getTenNguoiDung(),
                nguoiDung.getEmail(),
                nguoiDung.getMatKhau(),
                authorities
        );
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return matKhau;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(maNguoiDung, that.maNguoiDung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNguoiDung);
    }
} 