package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.dao.StudentDaoMP;
import com.jwsystem.dao.TeacherDaoMP;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.college.MajorPO;
import com.jwsystem.dao.MajorDaoMP;
import com.jwsystem.service.MajorServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class MajorServiceImpMP extends ServiceImpl<MajorDaoMP, MajorPO> implements MajorServiceMP {

    @Autowired
    MajorDaoMP majorDaoMP;
    @Autowired
    CollegeDaoMP collegeDaoMP;
    @Autowired
    TeacherDaoMP teacherDaoMP;
    @Autowired
    StudentDaoMP studentDaoMP;
    @Autowired
    TransUtil transUtil;

    @Override
    public Boolean insertMajor(MajorDTO majorDTO) {
        MajorPO majorPO = transUtil.MajorDTOtoPO(majorDTO);
        return majorDaoMP.insert(majorPO)!=0;
    }

    @Override
    public MajorDTO selectMajorByName(String majorName) {
        MajorPO majorPO = majorDaoMP.selectOne(Wrappers.lambdaQuery(MajorPO.class).eq(MajorPO::getName,majorName));
        MajorDTO majorDTO = transUtil.MajorPOtoDTO(majorPO);
        return majorDTO;
    }

    @Override
    public Boolean judgeMajorAndCollege(String majorName, String collegeName) {
        MajorPO m = majorDaoMP.selectOne(Wrappers.lambdaQuery(MajorPO.class).eq(MajorPO::getName, majorName));
        String name = collegeDaoMP.selectOne(Wrappers.lambdaQuery(CollegePO.class).eq(CollegePO::getCollegeId,m.getCollegeId())).getName();
        return (name.equals(collegeName));
    }

    @Override
    public void updateMajor(MajorDTO majorDTO) {
        MajorPO majorPO = transUtil.MajorDTOtoPO(majorDTO);
        majorDaoMP.updateById(majorPO);
    }

    @Override
    public void deleteMajor(MajorDTO majorDTO) {
        MajorPO majorPO = transUtil.MajorDTOtoPO(majorDTO);
        majorDaoMP.deleteById(majorPO);
    }

    @Override
    public boolean findOthersByMajor(MajorDTO majorDTO) {
        boolean res = false;

        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("major_id",majorDTO.getMajorId());
        //根据专业找老师
        if(teacherDaoMP.selectByMap(columnMap)!=null){
            res=true;
        }
        //根据专业找学生，返回学生数量

        else if(studentDaoMP.selectByMap(columnMap)!=null){
            res=true;
        }

        return res;
    }
}
