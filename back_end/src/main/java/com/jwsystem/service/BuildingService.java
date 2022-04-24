package com.jwsystem.service;

import com.jwsystem.entity.affair.Building;
import com.jwsystem.vo.BuildingVO;

import java.util.List;

public interface BuildingService {

    List<BuildingVO> getAllRooms();

    Building findByName(String fullName);

    Building findById(Integer id);

    void changeById(Building building);

    void add(Building building);

    void deleteByName(String name);

    void deleteAllRoomByName(String fullName);
}
