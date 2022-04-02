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
        //根据college获得colleges map
        Map<Integer,String> colleges = collegeDao.findAllCollegeInfos();
        //建立List<CollegeVO>容器
        List<CollegeVO> collegeVOs = new ArrayList<CollegeVO>();
        for(Map.Entry<Integer, String> entry : colleges.entrySet()){
            //根据collegename得到majorlist
            Map<Integer,String> majors = new HashMap<>();
            majors = majorDao.findMajorByCollegeName(entry.getValue());
            //将这两者放入一个collegevo对象 加入list容器
            CollegeVO collegeVO = new CollegeVO(entry.getKey(),entry.getValue(),majors);
            collegeVOs.add(collegeVO);
        }
        return collegeVOs;
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
    public Boolean findCollege(College college) {
        return collegeDao.findCollegeByName(college.getName()) != null;
    }

    @Override
    public Boolean findCollegeById(College college) {
        return collegeDao.findCollegeById(college.getCollegeId()) != null;
    }

    @Override
    public Boolean findMajor(Major major) {
        return majorDao.findMajorByName(major.getName()) != null;
    }

    @Override
    public Boolean findMajorById(Major major) {
        return majorDao.findMajorById(major.getMajorId()) != null;
    }

    @Override
    public Boolean updateCollege(College college) {
        return collegeDao.updateCollegeNameById(college.getCollegeId(), college.getName()) != null;
    }

    @Override
    public Boolean updateMajor(Major major) {
        return majorDao.updateMajorNameById(major.getMajorId(),major.getName(),major.getCollegeName()) != null;
    }

    @Override
    public Boolean deleteCollege(College college) {
        return collegeDao.deleteCollege(college.getName()) != 0;
    }

    @Override
    public Boolean deleteMajor(Major major) {
        return majorDao.deleteMajor(major.getName()) != 0;
    }
}
