package com.jwsystem.service.impl;

import com.jwsystem.dao.AdminDao;
import com.jwsystem.vo.UserVO;
import com.jwsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    public UserVO getUserByNumber(String number) {
        return adminDao.getUserByNumber(number);
    }
    @Override
    public boolean getCurr(){
        return adminDao.getCurr();
    }
    @Override
    public void setCurr(boolean choice) {
        adminDao.setCurr(choice);
    }
}
