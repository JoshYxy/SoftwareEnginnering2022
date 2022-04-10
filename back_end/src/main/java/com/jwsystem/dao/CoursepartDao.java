package com.jwsystem.dao;

import com.jwsystem.entity.Coursepart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursepartDao {
    //插入课程主体信息部分并返回自增主键
    int insertCoursepart(Coursepart coursepart);
    //插入申请的课程主体信息部分
    void insertReqCoursepart(Coursepart coursepart);
    //查询所有课程信息
    List<Coursepart> getCoursepartInfos();
    //根据学院名查询课程信息
    List<Coursepart> getCoursepartByCollege(String collegeName);

    List<Coursepart> getAllCoursepartByTeacherNum(String collegeName);

    Coursepart getReqCoursepartByRequestId(int requestId);
    //删除 Coursepart
    int deleteCoursepartByCourseId(int courseId);

}
