package com.jwsystem.service;

<<<<<<< Updated upstream
import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.entity.course.Timepart;
=======
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.TimepartDTO;
>>>>>>> Stashed changes

import java.util.List;

public interface CourseService {

    CoursepartDTO getCoursepartByCourseId(Integer courseId);

    List<CoursepartDTO> getAllCoursepart();

    List<TimepartDTO> getAllTimepartByCourseId(Integer courseId);

    int insertCoursepart(CoursepartDTO coursepartDTO);

    boolean insertTimepart(TimepartDTO timepartDTO);

    int deleteCoursepartByCourseId(int courseId);

    List<CoursepartDTO> getCoursepartByCollege(String collegeName);

    List<CoursepartDTO> getAllCoursepartByTeacherNum(String teacherNum);

    void insertReqCoursepart(CoursepartDTO coursepartDTO);

    void insertReqTimepart(TimepartDTO timepartDTO);

    List<TimepartDTO> getAllTimeByRoom(String building, String roomNum);

    List<TimepartDTO> getAllTimeByTea(String number);

    //根据楼在timepart和req_timepart里对应的所有的course_id和request_id
    List<Integer> getCourseIdByBuilding(String building);

    List<Integer> getRequestIdByBuilding(String building);

    List<Integer> getCourseIdByRoom(String building, String roomNum);

    List<Integer> getRequestIdByRoom(String building, String roomNum);

    void deleteReqByRequestId(Integer c);
}
