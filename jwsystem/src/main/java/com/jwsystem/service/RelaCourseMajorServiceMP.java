package com.jwsystem.service;


import com.jwsystem.entity.course.RelaCourseMajorPO;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface RelaCourseMajorServiceMP extends IService<RelaCourseMajorPO> {

    List<RelaCourseMajorPO> selectByCourseId(Integer i);

    void deleteAllByCourseId(int courseId);
}