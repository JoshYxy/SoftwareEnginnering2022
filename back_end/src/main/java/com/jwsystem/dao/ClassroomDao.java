package com.jwsystem.dao;

import com.jwsystem.entity.Building;
import com.jwsystem.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassroomDao {
    //得到所有楼宇信息
    List<Classroom> getAllClassrooms();
}
