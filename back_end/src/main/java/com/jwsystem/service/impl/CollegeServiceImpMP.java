package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.MajorDaoMP;
import com.jwsystem.dao.StudentDaoMP;
import com.jwsystem.dao.TeacherDaoMP;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.dto.MajorDataDTO;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.college.CollegePO;

import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.entity.college.MajorPO;
import com.jwsystem.service.CollegeServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.ClassroomVO;
import com.jwsystem.vo.CollegeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class CollegeServiceImpMP extends ServiceImpl<CollegeDaoMP, CollegePO> implements CollegeServiceMP {

    @Autowired
    CollegeDaoMP collegeDaoMP;
    @Autowired
    MajorDaoMP majorDaoMP;
    @Autowired
    TeacherDaoMP teacherDaoMP;
    @Autowired
    StudentDaoMP studentDaoMP;
    @Override
    public List<CollegeVO> selectAllCollegeAndMajorByList() {
        List<CollegePO> collegePOList = collegeDaoMP.selectList(Wrappers.emptyWrapper());
        List<CollegeVO> collegeVOList = collegePOList.stream().map(CollegeVO::new).collect(toList());
        if(collegeVOList.size()>0) {
            for(CollegeVO c:collegeVOList) {
                List<MajorPO> majorPOList = majorDaoMP.selectList(Wrappers.lambdaQuery(MajorPO.class)
                        .eq(MajorPO::getCollegeId,c.getCollegeVOId()));
                List<MajorDTO> majorDTOList = majorPOList.stream().map(MajorDTO::new).collect(toList());
                majorDTOList.forEach(e -> e.setCollegeName(c.getCollegeVOName()));
            }
        }
        return collegeVOList;
    }

    @Override
    public Boolean insertCollege(CollegePO collegePO) {
        return collegeDaoMP.insert(collegePO)!=0;
    }

    @Override
    public CollegePO selectCollegeByName(String collegeName) {
        return collegeDaoMP.selectOne(Wrappers.lambdaQuery(CollegePO.class)
                .eq(CollegePO::getName,collegeName));
    }

    @Override
    public boolean findOthersByCollege(CollegePO collegePO) {
        boolean res = false;

        //根据学院找专业
        if(majorDaoMP.findMajorByCollegeName(college.getName())!=0){
            res=true;
        }
        //根据学院找老师
        else if(!teacherDao.getTeacherByCollegeName(college.getName()).isEmpty()){
            res=true;
        }
        //根据学院找学生
        else if(studentDao.getStuByCollegeName(college.getName())!=0){
            res=true;
        }
        //根据学院找课程
        else if(!coursepartDao.getCoursepartByCollege(college.getName()).isEmpty()){
            res=true;
        }

        return res;
    }
}
