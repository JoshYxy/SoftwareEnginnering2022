package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.ClassroomDaoMP;

import com.jwsystem.dao.BuildingDaoMP;

import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.service.BuildingServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.ClassroomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.dao.BuildingDaoMP;
import com.jwsystem.service.BuildingServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.*;


@Service
public class BuildingServiceImpMP extends ServiceImpl<BuildingDaoMP, BuildingPO> implements BuildingServiceMP {
    @Autowired
    BuildingDaoMP buildingDaoMP;
    @Autowired
    ClassroomDaoMP classroomDaoMP;
    @Override
    public List<BuildingVO> selectAllBuildingAndRoomByList() {
        List<BuildingPO> buildingPOList = buildingDaoMP.selectList(Wrappers.emptyWrapper());
        List<BuildingVO> buildingVOList = buildingPOList.stream().map(BuildingVO::new).collect(toList());
        if(buildingVOList.size()>0) {
            for(BuildingVO b:buildingVOList) {
                List<ClassroomPO> classroomPOList = classroomDaoMP.selectList(Wrappers.lambdaQuery(ClassroomPO.class)
                        .eq(ClassroomPO::getBuildingId,b.getId()));
                List<ClassroomVO> classroomVOList = classroomPOList.stream().map(ClassroomVO::new).collect(toList());
                classroomVOList.forEach(e -> e.setBuilding(b.getAbbrName()));
                b.setRoom(classroomVOList);
            }
        }
        return buildingVOList;
    }


    @Override
    public BuildingPO selectByFullName(String fullName) {
        return buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getFullName,fullName));
    }
    @Override
    public BuildingPO selectByAbbrName(String abbrName) {
        return buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,abbrName));
    }
    @Override
    public int deleteByName(String fullName) {
        Map<String,Object> map = new HashMap<>();
        map.put("fullName", fullName);
        return buildingDaoMP.deleteByMap(map);
    }

}
