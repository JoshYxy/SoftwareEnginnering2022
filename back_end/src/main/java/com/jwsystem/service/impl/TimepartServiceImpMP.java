package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.*;
import com.jwsystem.dto.TimepartDTO;

import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.course.TimepartPO;

import com.jwsystem.entity.request.ReqTimepartPO;
import com.jwsystem.service.TimepartServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class TimepartServiceImpMP extends ServiceImpl<TimepartDaoMP, TimepartPO> implements TimepartServiceMP {

    @Autowired
    TimepartDaoMP timepartDaoMP;

    @Autowired
    ReqTimepartDaoMP reqTimepartDaoMP;

    @Autowired
    ClassroomDaoMP classroomDaoMP;

    @Autowired
    BuildingDaoMP buildingDaoMP;

    @Autowired
    TransUtil transUtil;

    @Override
    public List<TimepartDTO> selectAllTimepartByCourseId(int courseId) {
        List<TimepartPO> timepartPOList = timepartDaoMP.selectList(Wrappers.lambdaQuery(TimepartPO.class)
                .eq(TimepartPO::getCourseId,courseId));
        List<TimepartDTO> timepartDTOList = new ArrayList<>();
        for(TimepartPO t:timepartPOList){
            timepartDTOList.add(transUtil.TpPOtoTpDTO(t));
        }
        return timepartDTOList;
    }



    @Override
    public boolean insertTimepart(TimepartDTO timepartDTO) {
        //在插入的时候根据老师和教室判断有没有时间冲突
        //section '3 4 5' 得到数组 ['3'，'4'，'5']
        String[] sectionArray = timepartDTO.getSection().split("\\s+");
        //老师时间冲突
        //根据teachernum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByTea = timepartDaoMP.selectSectionByTea(timepartDTO.getTeacherNum(), timepartDTO.getWeekday());
        for(String sections : sectionListByTea) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }
        //教室时间冲突
        //根据roomnum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByRoom = timepartDaoMP.selectSectionByRoom(timepartDTO.getRoomNum(), timepartDTO.getWeekday(), timepartDTO.getBuilding());
        for(String sections : sectionListByRoom) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }

        return timepartDaoMP.insert(timepartPO) != 0;
    }


    @Override
    public List<TimepartDTO> selectAllTimepartByRoom(String building, String roomNum) {
        int buildingId = buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,building)).getId();
        int roomId = classroomDaoMP.selectOne(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId)
                .eq(ClassroomPO::getRoomNum,roomNum)).getRoomId();
        return timepartDaoMP.selectList(Wrappers.lambdaQuery(TimepartPO.class)
                .eq(TimepartPO::getRoomId,roomId)).transfer2DTOList;
    }

    @Override
    public List<TimepartDTO> selectAllTimepartByTea(String number) {
        return timepartDaoMP.selectList(Wrappers.lambdaQuery(TimepartPO.class)
                .eq(TimepartPO::getTeacherNum,number)).transfer2DTOList;
    }

    @Override
    public List<Integer> selectCourseIdByBuilding(String building) {
        int buildingId = buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,building)).getId();
        List<ClassroomPO> classroomPOList = classroomDaoMP.selectList(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId));
        List<Integer> roomIds = classroomPOList.stream().map(ClassroomPO::getRoomId).collect(Collectors.toList());
        List<Integer> courseIds = new ArrayList<>();
        for (Integer r:roomIds) {
            List<TimepartPO> timepartPOList = timepartDaoMP.selectList(Wrappers.lambdaQuery(TimepartPO.class)
                    .eq(TimepartPO::getRoomId,r));
            courseIds.addAll(timepartPOList.stream().map(TimepartPO::getCourseId).collect(Collectors.toList()));
        }
        return courseIds;
    }



    @Override
    public List<Integer> selectCourseIdByRoom(String building, String roomNum) {
        Integer buildingId = buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,building)).getId();
        Integer roomId = classroomDaoMP.selectOne(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId)
                .eq(ClassroomPO::getRoomNum,roomNum)).getRoomId();
        List<Integer> courseIds = new ArrayList<>();
        List<TimepartPO> reqTimepartPOList = timepartDaoMP.selectList(Wrappers.lambdaQuery(TimepartPO.class)
                .eq(TimepartPO::getRoomId,roomId));
        courseIds.addAll(reqTimepartPOList.stream().map(TimepartPO::getCourseId).collect(Collectors.toList()));
        return courseIds;
    }



    @Override
    public boolean examineTimes(String[] s) {
        List<String> allSections = timepartDaoMP.selectAllSections();
        for(String section:s){
            for(String allSection:allSections) {
                if(allSection.contains(section)){
                    return true;
                }
            }
        }
        return false;
    }
}
