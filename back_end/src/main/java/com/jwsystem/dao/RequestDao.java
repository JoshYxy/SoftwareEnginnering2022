package com.jwsystem.dao;

import com.jwsystem.entity.Request;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestDao {
    List<Request> getAllRequests();
    Request selectRequestById(int requestId);
    int insertRequest(String type, @Param("course_id") Integer courseId,@Param("teacher_num") String teacherNum, boolean examined, boolean passed);
    void examinedById(int requestId, boolean examined, boolean passed);
}
