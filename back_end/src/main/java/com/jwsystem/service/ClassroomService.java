package com.jwsystem.service;

import com.jwsystem.entity.Classroom;

public interface ClassroomService {

    Classroom findByNumAndBuilding(String roomNum, String building);

    void add(Classroom classRoom);

    Classroom findById(Integer roomId);

    void deleteById(Integer roomId);

    void changeById(Classroom classRoom);
}
