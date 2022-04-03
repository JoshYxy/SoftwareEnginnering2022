package com.jwsystem.service;

import com.jwsystem.entity.College;
import com.jwsystem.entity.CollegeVO;
import com.jwsystem.entity.Major;

import java.util.List;

public interface EduService {
    //管理员取得学院和专业信息
    List<CollegeVO> getEduInfo();
    //管理员增加新的学院
    Boolean insertCollege(College college);
    //管理员增加新的专业
    Boolean insertMajor(Major major);
    //查询某学院
    Boolean findCollegeById(College college);

    Boolean findCollegeByName(College college);

    //查询某专业
    Boolean findMajorById(Major major);

    Boolean findMajorByName(Major major);

    //修改学院信息
    void updateCollege(College college);
    //修改专业信息
    void updateMajor(Major major);
    //删除某学院
    void deleteCollege(College college);
    //删除某专业
    void deleteMajor(Major major);
}
