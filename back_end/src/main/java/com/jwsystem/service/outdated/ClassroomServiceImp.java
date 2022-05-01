//package com.jwsystem.service.impl;
//
//import com.jwsystem.dao.ClassroomDao;
//<<<<<<< Updated upstream
//import com.jwsystem.entity.affair.Classroom;
//=======
//import com.jwsystem.entity.affair.ClassroomVO;
//>>>>>>> Stashed changes
//import com.jwsystem.service.ClassroomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ClassroomServiceImp implements ClassroomService {
//    @Autowired
//    ClassroomDao classroomDao;
//
//    //查找同名同楼教室
//    @Override
//    public ClassroomVO findByBuildingAndNum(String building, String roomNum) {
//        return classroomDao.findByNumAndBuilding(roomNum,building);
//    }
//
//    @Override
//    public void add(ClassroomVO classRoom) {
//        classroomDao.add(classRoom.getBuilding(),classRoom.getRoomNum());
//    }
//
//    @Override
//    public ClassroomVO findById(Integer roomId) {
//        return classroomDao.findById(roomId);
//    }
//
//
//    @Override
//    public void deleteByBuildingAndRoomNum(String building,String roomNum) {
//        ClassroomVO c = classroomDao.findByNumAndBuilding(roomNum,building);
//        classroomDao.deleteByRoomId(c.getRoomId());
//    }
//
//    @Override
//    public void changeById(ClassroomVO classRoom) {
//        classroomDao.changeById(classRoom.getRoomId(),classRoom.getBuilding(),classRoom.getRoomNum());
//    }
//
//    @Override
//<<<<<<< Updated upstream
//    public List<Classroom> getClassroomsByBuilding(String building) {
//=======
//    public List<ClassroomVO> getClassroomsByBuilding(String building) {
//>>>>>>> Stashed changes
//        return classroomDao.getClassroomsByBuilding(building);
//    }
//
//}
