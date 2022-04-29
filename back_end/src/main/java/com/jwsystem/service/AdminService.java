package com.jwsystem.service;

import com.jwsystem.vo.UserVO;

public interface AdminService {
    //查找是否存在该管理员，返回User类型
    UserVO getUserByNumber(String number);

    boolean getCurr();

    void setCurr(boolean choice);
}
