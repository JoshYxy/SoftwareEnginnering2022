package com.jwsystem.dao;

import com.jwsystem.entity.Building;
import com.jwsystem.entity.Times;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingDao {
    //得到所有楼宇信息
    List<Building> getAllBuildings();
    //根据楼的名字得到该楼的全部信息
    Building findByName(String fullName);
    //加楼
    void add(String fullName, String abbrName);
    //根据id改楼名
    void changeById(Integer id,String fullName,String abbrName);
    //删楼
    void deleteByName(String fullName);
}
