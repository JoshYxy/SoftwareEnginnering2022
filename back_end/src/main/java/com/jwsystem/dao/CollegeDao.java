package com.jwsystem.dao;

import com.jwsystem.entity.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollegeDao {
    // 新增学院
    Integer insertCollege(String name);
    // 查询所有学院信息
    List<String> findAllCollegeInfos();
    //根据id查询某学院是否存在
    College findCollegeById(Integer collegeId);
    //根据name查询某学院是否存在
    College findCollegeByName(String name);
    //根据id修改某学院名
    College updateCollegeNameById(@Param("college_id") Integer collegeId, @Param("name") String name);
    // 删除学院
    Integer deleteCollege(String name);
}
