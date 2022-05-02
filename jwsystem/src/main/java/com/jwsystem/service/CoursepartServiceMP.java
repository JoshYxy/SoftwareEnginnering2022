package com.jwsystem.service;


import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.entity.course.CoursepartPO;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */

public interface CoursepartServiceMP extends IService<CoursepartPO> {
    //得到已存在的课程部分
    CoursepartDTO selectCoursepartByCourseId(int courseId);
    //得到所有已存在的课程
    List<CoursepartDTO> getAllCoursepart();
    //插入课程
    int insertCoursepart(CoursepartDTO coursepartDTO);

    //根据Id删除 直接用service方法
    //根据学院名得到coursepartDTO
    List<CoursepartDTO> selectCoursepartByCollege(String collegeName);
    //根据教师得到coursepartDTO
    List<CoursepartDTO> selectAllCoursepartByTeacherNum(String teacherNum);
    //根据requestId删除requesttea 直接用service方法
}
