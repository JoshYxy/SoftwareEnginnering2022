package com.jwsystem.service.impl;

import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.RequestDao;
import com.jwsystem.dao.TimepartDao;
import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Request;
import com.jwsystem.entity.Timepart;
import com.jwsystem.service.RequestService;
import com.jwsystem.vo.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRequestImp implements RequestService {
    @Autowired
    RequestDao requestDao;

    @Autowired
    CoursepartDao coursepartDao;

    @Autowired
    TimepartDao timepartDao;

    @Override
    public List<Request> getAllRequests(){
        return requestDao.getAllRequests();
    }

    @Override
    public Coursepart getReqCoursepartByRequestId(int requestId){
        return coursepartDao.getReqCoursepartByRequestId(requestId);
    }

    @Override
    public List<Timepart> getAllReqTimepartByRequestId(int requestId){
        return timepartDao.getAllReqTimepartByRequestId(requestId);
    }

    @Override
    public Request selectRequestById(int requestId){
        return requestDao.selectRequestById(requestId);
    }

    @Override
    public void examinedById(int requestId, boolean examined, boolean passed){
        requestDao.examinedById(requestId,examined,passed);
    }

//    public int insertRequest(Integer requestId,
//                             String type,
//                             Integer courseId,
//                             String teacherNum,
//                             boolean examined,
//                             boolean passed) {

    public int insertRequest(Request request){
        return requestDao.insertRequest(request);
    }
}
