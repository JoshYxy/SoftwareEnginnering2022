package com.jwsystem.service;

import com.jwsystem.dto.User;

public interface AdminService {
    //查找是否存在该管理员，返回User类型
    User getUserByNumber(String number);

}
