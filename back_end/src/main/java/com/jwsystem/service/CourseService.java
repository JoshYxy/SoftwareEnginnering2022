package com.jwsystem.service;

import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.entity.course.Timepart;

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

    List<Timepart> getAllTimeByTea(String number);

    //根据楼在timepart和req_timepart里对应的所有的course_id和request_id
    List<Integer> getCourseIdByBuilding(String building);

    List<Integer> getRequestIdByBuilding(String building);

    List<Integer> getCourseIdByRoom(String building, String roomNum);

    List<Integer> getRequestIdByRoom(String building, String roomNum);

    void deleteReqByRequestId(Integer c);
}
