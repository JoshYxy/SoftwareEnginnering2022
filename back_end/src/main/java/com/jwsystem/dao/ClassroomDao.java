package com.jwsystem.dao;

import com.jwsystem.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassroomDao {
    void add(String building, String roomNum);

    void changeById(Integer roomId, String building, String roomNum);

    void deleteById(Integer roomId);

    //得到所有教室信息
    List<Classroom> getClassroomsByBuilding(String building);

    Classroom findByNumAndBuilding(String roomNum, String building);

    Classroom findById(Integer roomId);
}
