package com.jwsystem.service;

import com.jwsystem.entity.User;

public interface AdminService {
    //查找是否存在该管理员，返回User类型
    User getUserByNumber(String number);

}
