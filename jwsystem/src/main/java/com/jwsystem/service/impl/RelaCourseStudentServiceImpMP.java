package com.jwsystem.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.dao.RelaCourseStudentDaoMP;
import com.jwsystem.service.RelaCourseStudentServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class RelaCourseStudentServiceImpMP extends ServiceImpl<RelaCourseStudentDaoMP, RelaCourseStudentPO> implements RelaCourseStudentServiceMP {

    @Autowired
    RelaCourseStudentDaoMP relaCourseStudentDaoMP;

    @Override
    public int selectStuNumberSelectCourse(Integer courseId, String selected) {
        return relaCourseStudentDaoMP.selectCount(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                .eq(RelaCourseStudentPO::getCourseId,courseId)
                .eq(RelaCourseStudentPO::getStatus,selected));
    }

    @Override
    public List<RelaCourseStudentPO> selectByCourseId(Integer courseId) {
        return relaCourseStudentDaoMP.selectList(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                .eq(RelaCourseStudentPO::getCourseId,courseId));
    }

    @Override
    public List<RelaCourseStudentPO> selectRecordByCourseAndStu(Integer courseId, String studentNum) {
        return relaCourseStudentDaoMP.selectList(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                .eq(RelaCourseStudentPO::getCourseId,courseId)
                .eq(RelaCourseStudentPO::getStudentNum,studentNum));
    }

    @Override
    public List<RelaCourseStudentPO> selectCourseWithStatus(String number, String status) {
        return relaCourseStudentDaoMP.selectList(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                .eq(RelaCourseStudentPO::getStudentNum,number)
                .eq(RelaCourseStudentPO::getStatus,status));
    }

    @Override
    public void deleteByCourseIdAndStuNum(int courseId, String num) {
        relaCourseStudentDaoMP.delete(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                .eq(RelaCourseStudentPO::getCourseId,courseId)
                .eq(RelaCourseStudentPO::getStudentNum,num));
    }


}
