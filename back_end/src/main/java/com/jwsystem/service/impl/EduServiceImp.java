package com.jwsystem.service.impl;

import com.jwsystem.dao.CollegeDao;
import com.jwsystem.dao.MajorDao;
import com.jwsystem.entity.College;
import com.jwsystem.entity.CollegeVO;
import com.jwsystem.entity.Major;
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
        return collegeDao.selectCollegeByName(college);
    }

    @Override
    public Major selectMajorByName(Major major) {
        return majorDao.selectMajorByName(major);
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
    public Boolean judgeMajorAndCollege(String majorName,String collegeName) {
        return majorDao.findCollegeByMajorName(majorName).equals(collegeName);
    }

    @Override
    public void updateCollege(College college) {
        collegeDao.updateCollegeNameById(college.getCollegeId(), college.getName());
    }

    @Override
    public void updateMajor(Major major) {
        majorDao.updateMajorNameById(major.getMajorId(), major.getName(), major.getCollegeName());
    }

    @Override
    public void deleteCollege(College college) {
        collegeDao.deleteCollege(college.getName());
    }

    @Override
    public void deleteMajor(Major major) {
        majorDao.deleteMajor(major.getName());
    }
}
