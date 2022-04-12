package com.jwsystem.service.impl;

import com.jwsystem.dao.ClassroomDao;
import com.jwsystem.entity.Classroom;
import com.jwsystem.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImp implements ClassroomService {
    @Autowired
    ClassroomDao classroomDao;

    @Override
    public Classroom findByBuildingAndNum(String building, String roomNum) {
        return classroomDao.findByNumAndBuilding(roomNum,building);
    }

    @Override
    public void add(Classroom classRoom) {
        classroomDao.add(classRoom.getBuilding(),classRoom.getRoomNum());
    }

    @Override
    public Classroom findById(Integer roomId) {
        return classroomDao.findById(roomId);
    }


    @Override
    public void deleteByBuildingAndRoomNum(String building,String roomNum) {
        Classroom c = classroomDao.findByNumAndBuilding(roomNum,building);
        classroomDao.deleteByRoomId(c.getRoomId());
    }

    @Override
    public void changeById(Classroom classRoom) {
        classroomDao.changeById(classRoom.getRoomId(),classRoom.getBuilding(),classRoom.getRoomNum());
    }

}
