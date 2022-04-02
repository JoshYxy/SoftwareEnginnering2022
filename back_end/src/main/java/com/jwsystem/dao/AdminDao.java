package com.jwsystem.dao;

import com.jwsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao {
    User getUserByNumber(String number);
}
