package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.dao.*;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.dto.MajorDataDTO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.service.CollegeServiceMP;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.college.MajorPO;
import com.jwsystem.vo.CollegeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    CoursepartDaoMP coursepartDaoMP;
    @Override
    public List<CollegeVO> selectAllCollegeAndMajorByList() {
        List<CollegePO> collegePOList = collegeDaoMP.selectList(Wrappers.emptyWrapper());
        List<CollegeVO> collegeVOList = collegePOList.stream().map(CollegeVO::new).collect(toList());
        if(collegeVOList.size()>0) {
            for(CollegeVO c:collegeVOList) {
                List<MajorPO> majorPOList = majorDaoMP.selectList(Wrappers.lambdaQuery(MajorPO.class)
                        .eq(MajorPO::getCollegeId,c.getCollegeVOId()));
                List<MajorDataDTO> majorDTOList = majorPOList.stream().map(MajorDataDTO::new).collect(toList());
                c.setMajors(majorDTOList);
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
        if(majorDaoMP.selectList(Wrappers.lambdaQuery(MajorPO.class).eq(MajorPO::getCollegeId,collegePO.getCollegeId())).size()!=0){
            res=true;
        }
        //根据学院找老师
        else if(teacherDaoMP.selectList(Wrappers.lambdaQuery(TeacherPO.class).eq(TeacherPO::getCollegeId,collegePO.getCollegeId())).size()!=0){
            res=true;
        }
        //根据学院找学生
        else if(studentDaoMP.selectList(Wrappers.lambdaQuery(StudentPO.class).eq(StudentPO::getCollegeId,collegePO.getCollegeId())).size()!=0){
            res=true;
        }
        //根据学院找课程
        else if((coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class).eq(CoursepartPO::getCollegeId,collegePO.getCollegeId())).size()!=0)){
            res=true;
        }

        return res;
    }
}
