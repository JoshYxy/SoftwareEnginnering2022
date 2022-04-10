package com.jwsystem.dao;

import com.jwsystem.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassroomDao {
    void add(String building, @Param("room_num") String roomNum);

    void changeById(@Param("room_id")Integer roomId, String building,@Param("room_num") String roomNum);

    void deleteById(Integer roomId);

    //得到所有教室信息
    List<Classroom> getClassroomsByBuilding(String building);

    Classroom findByNumAndBuilding(@Param("room_num")String roomNum, String building);

    Classroom findById(Integer roomId);
}
