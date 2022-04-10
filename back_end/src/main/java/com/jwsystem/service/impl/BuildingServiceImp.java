package com.jwsystem.service.impl;

import com.jwsystem.dao.BuildingDao;
import com.jwsystem.dao.ClassroomDao;
import com.jwsystem.entity.Building;
import com.jwsystem.entity.Classroom;
import com.jwsystem.service.BuildingService;
import com.jwsystem.vo.BuildingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImp implements BuildingService {
    @Autowired
    BuildingDao buildingDao;

    @Autowired
    ClassroomDao classroomDao;

    @Override
    public List<BuildingVO> getAllRooms(){
//        private Integer id;
//        private String fullName;
//        private String abbrName;
//        private List<Classroom> room;
        //先得到所有的building,再根据buildingid得到所有的classroom放进一个list 创建buildingvo对象
        List<BuildingVO> buildingVOList = new ArrayList<>();
        List<Building> buildings = buildingDao.getAllBuildings();
        for(Building b:buildings){
            List<Classroom> classrooms = classroomDao.getAllClassrooms();
            BuildingVO buildingVO = new BuildingVO(b.getId(),b.getFullName(),b.getAbbrName(),classrooms);
            buildingVOList.add(buildingVO);
        }
        return buildingVOList;
    }
}
