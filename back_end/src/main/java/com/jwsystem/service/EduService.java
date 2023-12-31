package com.jwsystem.service;

import com.jwsystem.entity.College;
import com.jwsystem.vo.CollegeVO;
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

    College selectCollegeByName(College college);

    Major selectMajorByName(Major major);

    //查询某专业
    Boolean findMajorById(Major major);

    Boolean findMajorByName(Major major);

    Boolean findMajorByStringName(String majorName);

    //判断学院和专业是否对应
    Boolean judgeMajorAndCollege(String majorName,String CollegeName);

    //修改学院信息
    void updateCollege(College college);
    //修改专业信息
    void updateMajor(Major major);
    //删除某学院
    void deleteCollege(College college);
    //删除某专业
    void deleteMajor(Major major);
}
