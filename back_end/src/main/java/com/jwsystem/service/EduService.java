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
    Boolean findCollege(College college);

    Boolean findCollegeById(College college);

    //查询某专业
    Boolean findMajor(Major major);

    Boolean findMajorById(Major major);

    //修改学院信息
    Boolean updateCollege(College college);
    //修改专业信息
    Boolean updateMajor(Major major);
    //删除某学院
    Boolean deleteCollege(College college);
    //删除某专业
    Boolean deleteMajor(Major major);
}
