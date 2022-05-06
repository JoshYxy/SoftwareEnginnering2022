package com.jwsystem.service;


import com.jwsystem.entity.course.RelaCourseStudentPO;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface RelaCourseStudentServiceMP extends IService<RelaCourseStudentPO> {

    int selectStuNumberSelectCourse(Integer courseId, String selected);

    List<RelaCourseStudentPO> selectByCourseId(Integer courseId);

    List<RelaCourseStudentPO> selectRecordByCourseAndStu(Integer courseId, String studentNum);

    List<RelaCourseStudentPO> selectCourseWithStatus(String number, String status);

    void deleteByCourseIdAndStuNum(int courseId, String num);
}
