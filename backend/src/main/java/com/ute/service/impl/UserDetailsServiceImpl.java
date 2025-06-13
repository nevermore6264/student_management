package com.ute.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ute.entity.NguoiDung;
import com.ute.repository.NguoiDungRepository;
import com.ute.security.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return UserPrincipal.create(nguoiDung);
    }
} 