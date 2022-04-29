package com.jwsystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.ClassroomDaoMP;
import com.jwsystem.entity.BuildingPO;
import com.jwsystem.dao.BuildingDaoMP;
import com.jwsystem.entity.ClassroomPO;
import com.jwsystem.service.BuildingServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.vo.BuildingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */

@Service
public class BuildingServiceImpMP extends ServiceImpl<BuildingDaoMP, BuildingPO> implements BuildingServiceMP {
    @Autowired
    BuildingDaoMP buildingDaoMP;
    @Autowired
    ClassroomDaoMP classroomDaoMP;
    @Override
    public List<BuildingVO> selectAllBuildingAndRoomByList() {
        List<BuildingPO> buildingPOList = buildingDaoMP.selectList(Wrappers.emptyWrapper());
        List<BuildingVO> buildingVOList = buildingPOList.stream().map(BuildingVO::new).collect(Collectors.toList());
        if(buildingVOList.size()>0) {
            addClassroomInfo(buildingVOList);
        }
        return buildingVOList;
    }
    private void addClassroomInfo(List<BuildingVO> buildingVOList){
        Set<Integer> buildingIds = buildingVOList.stream().map(BuildingPO::getId).collect(Collectors.toSet());
        List<ClassroomPO> classroomPOList = classroomDaoMP.selectList(Wrappers.lambdaQuery(ClassroomPO.class)
                .in(ClassroomPO::getBuildingId,buildingIds));
        Map<Integer,List<ClassroomPO>> hashMap = classroomPOList.stream().collect(groupingBy(ClassroomPO::getBuildingId));
        buildingVOList.forEach(e->e.setRoom(hashMap.get(e.getId())));
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
