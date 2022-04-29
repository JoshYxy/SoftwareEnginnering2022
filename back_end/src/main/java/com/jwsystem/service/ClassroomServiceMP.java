package com.jwsystem.service;

import com.jwsystem.entity.ClassroomPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface ClassroomServiceMP extends IService<ClassroomPO> {
    //根据buildingId查找教室
    List<ClassroomPO> selectListByBuildingId(int buildingId);
    //根据buildingId删除教室
    int deleteByBuildingId(int buildingId);
    //查找是否有该名教室存在
    ClassroomPO selectByRoomNum(int buildingId, String roomNum);
    //根据名字删教室
    int deleteByRoomNum(String roomNum);
}
