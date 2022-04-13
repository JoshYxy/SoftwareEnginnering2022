package com.jwsystem.dao;

import com.jwsystem.entity.Times;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimesDao {
    //得到所有时间信息
    List<Times> getAllTimes();

    Times findTimesByName(String name);
    //修改某节课开始结束时间
    void changeTimesByName(String name, @Param("start_time") String startTime, @Param("end_time")String endTime);
    //增加节数
    void addTimes(String name,@Param("start_time")String startTime,@Param("end_time")String endTime);
    //根据名字删除节数
    void deleteTimesByName(String name);

    void deleteAll();

}
