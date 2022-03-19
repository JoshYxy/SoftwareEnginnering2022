package com.jwsystem.service.impl;

import com.jwsystem.dao.UserDao;
import com.jwsystem.entity.User;
import com.jwsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Boolean insertUser(User user) {
        return userDao.insertUser(user)>0;
    }

    @Override
    public User selectByNumber(Long number) {
        return userDao.selectByNumber(number);
    }

    @Override
    public User selectById(String id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> findAllUserInfo() {
        return userDao.findAllUserInfo();
    }

    @Override
    public Boolean updateUserPwdByNumber(Long number, String password) {
        return userDao.updateUserPwdByNumber(number,password)>0;
    }

    @Override
    public Boolean deleteUserInfoById(String id) {
        return userDao.deleteUserInfoById(id)>0;
    }
}
