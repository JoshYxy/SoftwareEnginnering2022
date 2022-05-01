//package com.jwsystem.service.impl;
//
//import com.jwsystem.dao.*;
//<<<<<<< Updated upstream
//import com.jwsystem.entity.college.College;
//import com.jwsystem.entity.course.Coursepart;
//import com.jwsystem.entity.user.Teacher;
//import com.jwsystem.vo.CollegeVO;
//import com.jwsystem.entity.college.Major;
//=======
//import com.jwsystem.dto.MajorDTO;
//import com.jwsystem.entity.college.College;
//import com.jwsystem.entity.user.Teacher;
//import com.jwsystem.vo.CollegeVO;
//>>>>>>> Stashed changes
//import com.jwsystem.service.EduService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class EduServiceImp implements EduService {
//    @Autowired
//    CollegeDao collegeDao;
//
//    @Autowired
//    MajorDao majorDao;
//
//    @Autowired
//    TeacherDao teacherDao;
//
//    @Autowired
//    StudentDao studentDao;
//
//    @Autowired
//    CoursepartDao coursepartDao;
//
//    @Override
//    public List<CollegeVO> getEduInfo() {
//        return collegeDao.findCollegeAndMajor();
//    }
//
//    @Override
//    public Boolean insertCollege(College college) {
//        if(collegeDao.findCollegeByName(college.getName()) != null){
//            return false;
//        }else{
//            return collegeDao.insertCollege(college.getName()) != 0;
//        }
//    }
//
//    @Override
//    public Boolean insertMajor(MajorDTO majorDTO) {
//        if(majorDao.findMajorByName(majorDTO.getName()) != null){
//            return false;
//        }else
//            return majorDao.insertMajor(majorDTO.getName(), majorDTO.getCollegeName()) != 0;
//    }
//
//    @Override
//    public Boolean findCollegeById(College college) {
//        return collegeDao.findCollegeById(college.getCollegeId()) != null;
//    }
//
//    @Override
//    public Boolean findCollegeByName(College college) {
//        return collegeDao.findCollegeByName(college.getName()) != null;
//    }
//
//    @Override
//    public College selectCollegeByName(College college) {
//        return collegeDao.findCollegeByName(college.getName());
//    }
//
//    @Override
//    public MajorDTO selectMajorByName(MajorDTO majorDTO) {
//        return majorDao.findMajorByName(majorDTO.getName());
//    }
//
//    @Override
//    public Boolean findMajorById(MajorDTO majorDTO) {
//        return majorDao.findMajorById(majorDTO.getMajorId()) != null;
//    }
//
//    @Override
//    public Boolean findMajorByName(MajorDTO majorDTO) {
//        return majorDao.findMajorByName(majorDTO.getName()) != null;
//    }
//
//    @Override
//    public Boolean findMajorByStringName(String majorName) {
//        return majorDao.findMajorByName(majorName) != null;
//    }
//
//    /////这上面三个函数咋一摸一样我晕
//
//    @Override
//    public Boolean judgeMajorAndCollege(String majorName,String collegeName) {
//        return majorDao.findCollegeByMajorName(majorName).equals(collegeName);
//    }
//
//    @Override
//    public void updateCollege(College college) {
//        collegeDao.updateCollegeNameById(college.getCollegeId(), college.getName());
//    }
//
//    @Override
//    public void updateMajor(MajorDTO majorDTO) {
//        majorDao.updateMajorNameById(majorDTO.getMajorId(), majorDTO.getName());
//    }
//
//    @Override
//    public void deleteCollege(College college) {
//        collegeDao.deleteCollege(college.getName());
//    }
//
//    @Override
//    public void deleteMajor(MajorDTO majorDTO) {
//        majorDao.deleteMajor(majorDTO.getName());
//    }
//
//    @Override
//    public boolean findOthersByCollege(College college) {
//        boolean res = false;
//        //根据学院找专业
//        if(majorDao.findMajorByCollegeName(college.getName())!=0){
//            res=true;
//        }
//        //根据学院找老师
//        else if(!teacherDao.getTeacherByCollegeName(college.getName()).isEmpty()){
//            res=true;
//        }
//        //根据学院找学生，返回学生数量
//        else if(studentDao.getStuByCollegeName(college.getName())!=0){
//            res=true;
//        }
//        //根据学院找课程
//        else if(!coursepartDao.getCoursepartByCollege(college.getName()).isEmpty()){
//            res=true;
//        }
//
//        return res;
//    }
//
//    @Override
//    public boolean findOthersByMajor(MajorDTO majorDTO) {
//        boolean res = false;
//
//        //根据专业找老师
//        if(teacherDao.getTeacherByMajor(majorDTO.getName()) != 0){
//            res=true;
//        }
//        //根据专业找学生，返回学生数量
//        else if(studentDao.getStuByMajor(majorDTO.getName())!=0){
//            res=true;
//        }
//
//        return res;
//    }
//
//    @Override
//    public boolean findOthersByCollege(College college) {
//        boolean res = false;
//        //根据学院找专业
//        if(majorDao.findMajorByCollegeName(college.getName())!=0){
//            res=true;
//        }
//        //根据学院找老师
//        else if(!teacherDao.getTeacherByCollegeName(college.getName()).isEmpty()){
//            res=true;
//        }
//        //根据学院找学生，返回学生数量
//        else if(studentDao.getStuByCollegeName(college.getName())!=0){
//            res=true;
//        }
//        //根据学院找课程
//        else if(!coursepartDao.getCoursepartByCollege(college.getName()).isEmpty()){
//            res=true;
//        }
//
//        return res;
//    }
//
//    @Override
//    public boolean findOthersByMajor(Major major) {
//        boolean res = false;
//
//        //根据专业找老师
//        if(teacherDao.getTeacherByMajor(major.getName()) != 0){
//            res=true;
//        }
//        //根据专业找学生，返回学生数量
//        else if(studentDao.getStuByMajor(major.getName())!=0){
//            res=true;
//        }
//
//        return res;
//    }
//}
