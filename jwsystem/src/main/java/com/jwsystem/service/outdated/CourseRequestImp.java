//package com.jwsystem.service.impl;
//
//import com.jwsystem.dao.CoursepartDao;
//import com.jwsystem.dao.RequestDao;
//import com.jwsystem.dao.TimepartDao;
//<<<<<<< Updated upstream
//import com.jwsystem.entity.course.Coursepart;
//import com.jwsystem.entity.request.Request;
//import com.jwsystem.entity.course.Timepart;
//=======
//import com.jwsystem.dto.CoursepartDTO;
//import com.jwsystem.dto.TimepartDTO;
//import com.jwsystem.dto.RequestDTO;
//>>>>>>> Stashed changes
//import com.jwsystem.service.RequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CourseRequestImp implements RequestService {
//    @Autowired
//    RequestDao requestDao;
//
//    @Autowired
//    CoursepartDao coursepartDao;
//
//    @Autowired
//    TimepartDao timepartDao;
//
//    @Override
//    public List<RequestDTO> getAllRequests(){
//        return requestDao.getAllRequests();
//    }
//
//    @Override
//    public CoursepartDTO getReqCoursepartByRequestId(int requestId){
//        return coursepartDao.getReqCoursepartByRequestId(requestId);
//    }
//
//    @Override
//    public List<TimepartDTO> getAllReqTimepartByRequestId(int requestId){
//        return timepartDao.getAllReqTimepartByRequestId(requestId);
//    }
//
//    @Override
//    public RequestDTO selectRequestById(int requestId){
//        return requestDao.selectRequestById(requestId);
//    }
//
//    @Override
//    public void examinedById(int requestId, boolean examined, boolean passed){
//        requestDao.examinedById(requestId,examined,passed);
//    }
//
////    public int insertRequest(Integer requestId,
////                             String type,
////                             Integer courseId,
////                             String teacherNum,
////                             boolean examined,
////                             boolean passed) {
//
//    public int insertRequest(RequestDTO requestDTO){
//        return requestDao.insertRequest(requestDTO);
//    }
//}
