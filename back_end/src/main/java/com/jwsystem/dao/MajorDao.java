package com.jwsystem.dao;

import com.jwsystem.entity.college.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MajorDao {
    //新增专业
    int insertMajor(String name, @Param("college_name")String collegeName);
    //根据id查询某专业是否存在
    Major findMajorById(Integer majorId);
    //根据name查询某专业是否存在
    Major findMajorByName(String name);
    //根据id修改某专业名
    int updateMajorNameById(@Param("major_id") Integer majorId,String name);
    // 删除专业
    int deleteMajor(String name);
    //判断学院专业对应
    String findCollegeByMajorName(String name);
    //根据学院name查询该学院下的专业个数，返回int
    int findMajorByCollegeName(String collegeName);
}
