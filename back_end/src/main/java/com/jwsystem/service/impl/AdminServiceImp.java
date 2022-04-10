package com.jwsystem.service.impl;

import com.jwsystem.dao.AdminDao;
import com.jwsystem.dto.User;
import com.jwsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    public User getUserByNumber(String number) {
        return adminDao.getUserByNumber(number);
    }
}
