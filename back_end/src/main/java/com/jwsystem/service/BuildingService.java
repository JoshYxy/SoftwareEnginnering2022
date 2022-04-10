package com.jwsystem.service;

import com.jwsystem.entity.Building;
import com.jwsystem.entity.Times;
import com.jwsystem.vo.BuildingVO;

import java.util.List;

public interface BuildingService {

    List<BuildingVO> getAllRooms();

    Building findByName(String fullName);

    void changeById(Building building);

    void add(Building building);

    void deleteByName(String name);
}
