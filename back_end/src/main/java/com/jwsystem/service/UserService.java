package com.jwsystem.service;


import com.jwsystem.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 添加user信息
     */
    Boolean insertUser(User user);

    /**
     * 根据主键查询用户信息
     */
    User selectByNumber(Long number);

    /**
     * 根据身份证查询
     */
    User selectById(String id);

    /**
     * 查询所有用户信息
     */
    List<User> findAllUserInfo();

    /**
     * 修改用户密码
     */

    Boolean updateUserPwdByNumber(Long number, String password);

    /**
     * 删除用户信息
     */
    Boolean deleteUserInfoById(String id);
}

