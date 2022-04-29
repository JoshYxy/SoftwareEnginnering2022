package com.jwsystem.dao;

<<<<<<< Updated upstream
import com.jwsystem.entity.course.Coursepart;
=======
import com.jwsystem.dto.CoursepartDTO;
>>>>>>> Stashed changes
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursepartDao {
    //插入课程主体信息部分并返回自增主键
//    @SelectKey(statement="call identity()", keyProperty="relationId", before=false, resultType=int.class)
//    @Options(keyProperty = "relationId", useGeneratedKeys = true)
    int insertCoursepart(CoursepartDTO coursepartDTO);

    //插入申请的课程主体信息部分
    void insertReqCoursepart(CoursepartDTO coursepartDTO);
    //查询所有课程信息
    List<CoursepartDTO> getCoursepartInfos();
    //根据学院名查询课程信息
    List<CoursepartDTO> getCoursepartByCollege(String collegeName);

    List<CoursepartDTO> getAllCoursepartByTeacherNum(String collegeName);

    CoursepartDTO getReqCoursepartByRequestId(int requestId);

    CoursepartDTO getCoursepartByCourseId(int courseId);

    //删除 Coursepart
    int deleteCoursepartByCourseId(int courseId);

    int deleteReqCourseByRequestId(int requestId);
}
