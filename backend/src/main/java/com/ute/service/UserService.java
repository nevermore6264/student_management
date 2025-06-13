package com.ute.service;

import java.util.List;

import com.ute.entity.NguoiDung;

public interface UserService {
    List<NguoiDung> getAllUsers();
    NguoiDung getUserById(String id);
    NguoiDung updateUser(String id, NguoiDung user);
    void deleteUser(String id);
    NguoiDung getUserByEmail(String email);
} 