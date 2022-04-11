package com.jwsystem.dao;

import com.jwsystem.entity.Timepart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimepartDao {
    List<Timepart> getAllTimepart(Integer courseId);
    List<Timepart> getAllReqTimepartByRequestId(Integer requestId);
    List<String> selectSectionByTea(@Param("teacher_num") String teacherNum, Integer weekday);
    List<String> selectSectionByRoom(@Param("room_num")String roomNum,Integer weekday);
    void insertTimepart(Timepart timepart);
    void insertReqTimepart(Timepart timepart);
    List<Timepart> getAllTimeByRoom(String building, @Param("room_num") String roomNum);
    List<Timepart> getTeaClassTimeByTeaNum(Integer teacherNum);
}
