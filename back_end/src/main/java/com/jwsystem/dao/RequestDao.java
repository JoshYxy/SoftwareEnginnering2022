package com.jwsystem.dao;

import com.jwsystem.entity.Request;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RequestDao {
    List<Request> getAllRequests();
    Request selectRequestById(int requestId);
    int insertRequest(String type,Integer courseId,String teacherNum,boolean examined,boolean passed);
    void examinedById(int requestId, boolean examined, boolean passed);
}
