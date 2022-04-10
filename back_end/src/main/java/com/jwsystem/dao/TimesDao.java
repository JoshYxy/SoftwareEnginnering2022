package com.jwsystem.dao;

import com.jwsystem.entity.Times;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimesDao {
    //得到所有时间信息
    List<Times> getAllTimes();
}
