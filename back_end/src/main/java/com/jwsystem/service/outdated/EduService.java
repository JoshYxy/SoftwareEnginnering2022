//package com.jwsystem.service;
//
//<<<<<<< Updated upstream
//import com.jwsystem.entity.college.College;
//import com.jwsystem.vo.CollegeVO;
//import com.jwsystem.entity.college.Major;
//=======
//import com.jwsystem.dto.MajorDTO;
//import com.jwsystem.entity.college.College;
//import com.jwsystem.vo.CollegeVO;
//>>>>>>> Stashed changes
//
//import java.util.List;
//
//public interface EduService {
//    //管理员取得学院和专业信息
//    List<CollegeVO> getEduInfo();
//    //管理员增加新的学院
//    Boolean insertCollege(College college);
//    //管理员增加新的专业
//    Boolean insertMajor(MajorDTO majorDTO);
//    //查询某学院
//    Boolean findCollegeById(College college);
//
//    Boolean findCollegeByName(College college);
//
//    College selectCollegeByName(College college);
//
//    MajorDTO selectMajorByName(MajorDTO majorDTO);
//
//    //查询某专业
//    Boolean findMajorById(MajorDTO majorDTO);
//
//    Boolean findMajorByName(MajorDTO majorDTO);
//
//    Boolean findMajorByStringName(String majorName);
//
//    //判断学院和专业是否对应
//    Boolean judgeMajorAndCollege(String majorName,String CollegeName);
//
//    //修改学院信息
//    void updateCollege(College college);
//    //修改专业信息
//    void updateMajor(MajorDTO majorDTO);
//    //删除某学院
//    void deleteCollege(College college);
//    //删除某专业
//<<<<<<< Updated upstream
//    void deleteMajor(Major major);
//    //根据学院查找对应的专业、课程、学生、老师信息
//    boolean findOthersByCollege(College college);
//
//    boolean findOthersByMajor(Major major);
//=======
//    void deleteMajor(MajorDTO majorDTO);
//    //根据学院查找对应的专业、课程、学生、老师信息
//    boolean findOthersByCollege(College college);
//
//    boolean findOthersByMajor(MajorDTO majorDTO);
//>>>>>>> Stashed changes
//}
