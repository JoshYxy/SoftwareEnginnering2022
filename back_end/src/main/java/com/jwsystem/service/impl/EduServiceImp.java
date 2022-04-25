package com.jwsystem.service.impl;

import com.jwsystem.dao.*;
import com.jwsystem.entity.college.College;
import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.entity.user.Teacher;
import com.jwsystem.vo.CollegeVO;
import com.jwsystem.entity.college.Major;
import com.jwsystem.service.EduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EduServiceImp implements EduService {
    @Autowired
    CollegeDao collegeDao;

    @Autowired
    MajorDao majorDao;

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CoursepartDao coursepartDao;

    @Override
    public List<CollegeVO> getEduInfo() {
        return collegeDao.findCollegeAndMajor();
    }

    @Override
    public Boolean insertCollege(College college) {
        if(collegeDao.findCollegeByName(college.getName()) != null){
            return false;
        }else{
            return collegeDao.insertCollege(college.getName()) != 0;
        }
    }

    @Override
    public Boolean insertMajor(Major major) {
        if(majorDao.findMajorByName(major.getName()) != null){
            return false;
        }else
            return majorDao.insertMajor(major.getName(),major.getCollegeName()) != 0;
    }

    @Override
    public Boolean findCollegeById(College college) {
        return collegeDao.findCollegeById(college.getCollegeId()) != null;
    }

    @Override
    public Boolean findCollegeByName(College college) {
        return collegeDao.findCollegeByName(college.getName()) != null;
    }

    @Override
    public College selectCollegeByName(College college) {
        return collegeDao.findCollegeByName(college.getName());
    }

    @Override
    public Major selectMajorByName(Major major) {
        return majorDao.findMajorByName(major.getName());
    }

    @Override
    public Boolean findMajorById(Major major) {
        return majorDao.findMajorById(major.getMajorId()) != null;
    }

    @Override
    public Boolean findMajorByName(Major major) {
        return majorDao.findMajorByName(major.getName()) != null;
    }

    @Override
    public Boolean findMajorByStringName(String majorName) {
        return majorDao.findMajorByName(majorName) != null;
    }

    @Override
    public Boolean judgeMajorAndCollege(String majorName,String collegeName) {
        return majorDao.findCollegeByMajorName(majorName).equals(collegeName);
    }

    @Override
    public void updateCollege(College college) {
        collegeDao.updateCollegeNameById(college.getCollegeId(), college.getName());
    }

    @Override
    public void updateMajor(Major major) {
        majorDao.updateMajorNameById(major.getMajorId(), major.getName());
    }

    @Override
    public void deleteCollege(College college) {
        collegeDao.deleteCollege(college.getName());
    }

    @Override
    public void deleteMajor(Major major) {
        majorDao.deleteMajor(major.getName());
    }

    @Override
    public boolean findOthersByCollege(College college) {
        boolean res = false;
        //根据学院找专业
        if(majorDao.findMajorByCollegeName(college.getName())!=0){
            res=true;
        }
        //根据学院找老师
        else if(!teacherDao.getTeacherByCollegeName(college.getName()).isEmpty()){
            res=true;
        }
        //根据学院找学生，返回学生数量
        else if(studentDao.getStuByCollegeName(college.getName())!=0){
            res=true;
        }
        //根据学院找课程
        else if(!coursepartDao.getCoursepartByCollege(college.getName()).isEmpty()){
            res=true;
        }

        return res;
    }

    @Override
    public boolean findOthersByMajor(Major major) {
        boolean res = false;

        //根据专业找老师
        if(teacherDao.getTeacherByMajor(major.getName()) != 0){
            res=true;
        }
        //根据专业找学生，返回学生数量
        else if(studentDao.getStuByMajor(major.getName())!=0){
            res=true;
        }

        return res;
    }
}
