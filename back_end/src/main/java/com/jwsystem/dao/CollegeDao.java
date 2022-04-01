package com.jwsystem.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollegeDao {
    // 新增学院
    int insertCollege(String name);
    // 查询所有学院信息
    List<String> findAllCollegeInfos();
    //根据id查询某学院是否存在
    int findCollegeById(Integer collegeId);
    //根据id修改某学院名
    int updateCollegeNameById(Integer collegeId);
    // 删除学院
    int deleteCollege(String name);
}
