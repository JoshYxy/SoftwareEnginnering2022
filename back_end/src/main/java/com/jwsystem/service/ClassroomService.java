package com.jwsystem.service;

import com.jwsystem.entity.Classroom;

import java.util.List;

public interface ClassroomService {

    Classroom findByBuildingAndNum(String building, String roomNum);

    void add(Classroom classRoom);

    Classroom findById(Integer roomId);

    void deleteByBuildingAndRoomNum(String building, String roomNum);

    void changeById(Classroom classRoom);

}
