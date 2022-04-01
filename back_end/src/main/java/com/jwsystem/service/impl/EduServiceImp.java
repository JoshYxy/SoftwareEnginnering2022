package com.jwsystem.service.impl;

import com.jwsystem.dao.CollegeDao;
import com.jwsystem.dao.MajorDao;
import com.jwsystem.entity.College;
import com.jwsystem.entity.CollegeVO;
import com.jwsystem.entity.Major;
import com.jwsystem.service.EduService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EduServiceImp implements EduService {
    CollegeDao collegeDao;
    MajorDao majorDao;
    @Override
    public List<CollegeVO> getEduInfo() {
        //根据college获得colleges list
        List<String> colleges = collegeDao.findAllCollegeInfos();
        //建立List<CollegeVO>容器
        List<CollegeVO> collegeVOs = new ArrayList<CollegeVO>();
        //根据collegename得到majorlist
        for(String collegeName : colleges){
            List<String> majors = majorDao.findMajorByCollegeName(collegeName);
            //将这两者放入一个collegevo对象 加入list容器中
            CollegeVO collegeVO = new CollegeVO(collegeName,majors);
            collegeVOs.add(collegeVO);
        }
        return collegeVOs;
        //这里用map取代list性能可能会更高？  数据库返回类型：Object List Map
    }

    @Override
    public Boolean insertCollege(College college) {
        return collegeDao.insertCollege(college.getName()) != 0;
    }

    @Override
    public Boolean insertMajor(Major major) {
        return majorDao.insertMajor(major.getName(),major.getCollegeName()) != 0;
    }

    @Override
    public Boolean findCollege(College college) {
        return collegeDao.findCollegeById(college.getCollegeId()) != 0;
    }

    @Override
    public Boolean findMajor(Major major) {
        return majorDao.findMajorById(major.getMajorId()) != 0;
    }

    @Override
    public Boolean updateCollege(College college) {
        return collegeDao.updateCollegeNameById(college.getCollegeId()) != 0;
    }

    @Override
    public Boolean updateMajor(Major major) {
        return majorDao.updateMajorNameById(major.getMajorId()) != 0;
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
