package com.jwsystem.service;

import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Timepart;

import java.util.List;

public interface CourseService {

    Coursepart getCoursepartByCourseId(Integer courseId);

    List<Coursepart> getAllCoursepart();

    List<Timepart> getAllTimepartByCourseId(Integer courseId);

    int insertCoursepart(Coursepart coursepart);

    boolean insertTimepart(Timepart timepart);

    int deleteCoursepartByCourseId(int courseId);

    List<Coursepart> getCoursepartByCollege(String collegeName);

    List<Coursepart> getAllCoursepartByTeacherNum(String teacherNum);

    void insertReqCoursepart(Coursepart coursepart);

    void insertReqTimepart(Timepart timepart);

    List<Timepart> getAllTimeByRoom(String building, String roomNum);
}
