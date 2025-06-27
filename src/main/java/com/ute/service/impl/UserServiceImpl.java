package com.ute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ute.entity.NguoiDung;
import com.ute.repository.NguoiDungRepository;
import com.ute.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public List<NguoiDung> getAllUsers() {
        return nguoiDungRepository.findAll();
    }

    @Override
    public NguoiDung getUserById(String id) {
        return nguoiDungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public NguoiDung updateUser(String id, NguoiDung user) {
        NguoiDung existingUser = getUserById(id);
        
        existingUser.setTenNguoiDung(user.getTenNguoiDung());
        existingUser.setEmail(user.getEmail());
        existingUser.setSoDienThoai(user.getSoDienThoai());
        existingUser.setDiaChi(user.getDiaChi());
        
        return nguoiDungRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        NguoiDung user = getUserById(id);
        nguoiDungRepository.delete(user);
    }

    @Override
    public NguoiDung getUserByEmail(String email) {
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
} 