package com.jwsystem.dao;

import com.jwsystem.entity.Timepart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimepartDao {
    List<Timepart> getAllTimepart(Integer collegeId);
    List<Timepart> getAllReqTimepartByRequestId(Integer requestId);
    List<String> selectSectionByTea(String teacherNum,Integer weekday);
    List<String> selectSectionByRoom(String roomNum,Integer weekday);
    void insertTimepart(Timepart timepart);
    void insertReqTimepart(Timepart timepart);
    List<Timepart> getAllTimeByRoom(String building, String roomNum);
}
