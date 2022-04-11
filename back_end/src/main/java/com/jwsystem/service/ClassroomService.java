package com.jwsystem.service;

import com.jwsystem.entity.Classroom;

public interface ClassroomService {

    Classroom findByNumAndBuilding(String roomNum, String building);

    void add(Classroom classRoom);

    Classroom findById(Integer roomId);

    void deleteByBuildingAndRoomNum(String building, String roomNum);

    void changeById(Classroom classRoom);
}
