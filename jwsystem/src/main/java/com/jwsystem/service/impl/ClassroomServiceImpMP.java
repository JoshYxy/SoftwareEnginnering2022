package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.jwsystem.dao.ClassroomDaoMP;
import com.jwsystem.service.ClassroomServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jwsystem.entity.affair.ClassroomPO;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class ClassroomServiceImpMP extends ServiceImpl<ClassroomDaoMP, ClassroomPO> implements ClassroomServiceMP {


    @Autowired
    ClassroomDaoMP classroomDaoMP;

    @Override
    public List<ClassroomPO> selectListByBuildingId(int buildingId) {
        return classroomDaoMP.selectList(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId));
    }

    @Override
    public int deleteByBuildingId(int buildingId) {
        Map<String,Object> map = new HashMap<>();
        map.put("buildingId", buildingId);
        return classroomDaoMP.deleteByMap(map);
    }

    //building_id
    @Override
    public ClassroomPO selectByRoomNum(int buildingId, String roomNum) {
        return classroomDaoMP.selectOne(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getRoomNum,roomNum)
                .eq(ClassroomPO::getBuildingId,buildingId));
    }

    @Override
    public void deleteByBuildingAndRoomNum(int buildingId, String roomNum) {
        classroomDaoMP.delete(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId)
                .eq(ClassroomPO::getRoomNum,roomNum));
    }

//    @Override
//    public int deleteByRoomNum(String roomNum) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("roomNum", roomNum);
//        return classroomDaoMP.deleteByMap(map);
//    }

}
