package com.jwsystem.dao;

import com.jwsystem.entity.Building;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingDao {
    //得到所有楼宇信息
    List<Building> getAllBuildings();

}
