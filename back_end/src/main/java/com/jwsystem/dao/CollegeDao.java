package com.jwsystem.dao;

import com.jwsystem.entity.College;
import com.jwsystem.vo.CollegeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollegeDao {
    // 新增学院
    int insertCollege(String name);
    // 查询所有学院信息
//    @MapKey("college_id")
//    Map<Integer,String> findAllCollegeInfos();
    //查询学院和对应专业
    List<CollegeVO> findCollegeAndMajor();
    //根据id查询某学院是否存在
    College findCollegeById(Integer collegeId);
    //根据name查询某学院是否存在
    College findCollegeByName(String name);
    //根据id修改某学院名
    College updateCollegeNameById(@Param("college_id") Integer collegeId,String name);
    // 删除学院
    int deleteCollege(String name);

    College selectCollegeByName(College college);
    //得到所有学院名字
    List<String> getAllCollegeName();
}
