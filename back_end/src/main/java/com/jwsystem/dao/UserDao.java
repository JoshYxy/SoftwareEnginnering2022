package com.jwsystem.dao;

import com.jwsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 添加user信息
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据主键查询用户信息
     *
     * @param number
     * @return
     */
    User selectByNumber(Long number);

    /**
     * 根据身份证查询
     *
     * @param id
     * @return
     */
    User selectById(String id);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<User> findAllUserInfo();

    /**
     * 修改用户密码
     * @param number
     * @return
     */
    int updateUserPwdByNumber(Long number,String password);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    int deleteUserInfoById(String id);
}

