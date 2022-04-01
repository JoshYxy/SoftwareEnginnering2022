package com.jwsystem.dao;

import com.jwsystem.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MajorDao {
    // 新增专业
    int insertMajor(@Param("name") String name, @Param("collegeName") String collegeName);
    // 根据学院name查询该学院下的所有专业
    List<String> findMajorByCollegeName(String collegeName);
    //根据id查询某专业是否存在
    int findMajorById(Integer majorId);
    //根据id修改某专业名
    int updateMajorNameById(Integer majorId);
    // 删除专业
    int deleteMajor(String name);
}
