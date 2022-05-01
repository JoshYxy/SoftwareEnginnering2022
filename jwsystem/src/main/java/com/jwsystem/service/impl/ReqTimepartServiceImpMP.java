package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.BuildingDaoMP;
import com.jwsystem.dao.ClassroomDaoMP;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.course.TimepartPO;
import com.jwsystem.entity.request.ReqTeacherPO;
import com.jwsystem.entity.request.ReqTimepartPO;

import com.jwsystem.dao.ReqTimepartDaoMP;
import com.jwsystem.service.ReqTimepartServiceMP;
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
public class ReqTimepartServiceImpMP extends ServiceImpl<ReqTimepartDaoMP, ReqTimepartPO> implements ReqTimepartServiceMP {

    @Autowired
    ReqTimepartDaoMP reqTimepartDaoMP;

    @Autowired
    BuildingDaoMP buildingDaoMP;

    @Autowired
    ClassroomDaoMP classroomDaoMP;

    @Autowired
    TransUtil transUtil;

    @Override
    public List<TimepartDTO> selectAllReqTimepartByRequestId(int requestId) {
        List<ReqTimepartPO> reqTimepartPOList = reqTimepartDaoMP.selectList(Wrappers.lambdaQuery(ReqTimepartPO.class)
                .eq(ReqTimepartPO::getRequestId,requestId));
        List<TimepartDTO> timepartDTOList = new ArrayList<>();
        for (ReqTimepartPO t: reqTimepartPOList) {
            timepartDTOList.add(transUtil.ReqTpPOtoTpDTO(t));
        }
        return timepartDTOList;
    }

    @Override
    public void insertReqTimepart(TimepartDTO timepartDTO) {
        ReqTimepartPO reqtimepartPO = transUtil.TpDTOtoReqTpPO(timepartDTO);
        reqTimepartDaoMP.insert(reqtimepartPO);
    }


    @Override
    public List<Integer> selectRequestIdByBuilding(String building) {
        int buildingId = buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,building)).getId();
        List<ClassroomPO> classroomPOList = classroomDaoMP.selectList(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId));
        List<Integer> roomIds = classroomPOList.stream().map(ClassroomPO::getRoomId).collect(Collectors.toList());
        List<Integer> requestIds = new ArrayList<>();
        for (Integer r:roomIds) {
            List<ReqTimepartPO> reqTimepartPOList = reqTimepartDaoMP.selectList(Wrappers.lambdaQuery(ReqTimepartPO.class)
                    .eq(ReqTimepartPO::getRoomId,r));
            requestIds.addAll(reqTimepartPOList.stream().map(ReqTimepartPO::getRequestId).collect(Collectors.toList()));
        }
        return requestIds;
    }

    @Override
    public List<Integer> selectRequestIdByRoom(String building, String roomNum) {
        Integer buildingId = buildingDaoMP.selectOne(Wrappers.lambdaQuery(BuildingPO.class)
                .eq(BuildingPO::getAbbrName,building)).getId();
        Integer roomId = classroomDaoMP.selectOne(Wrappers.lambdaQuery(ClassroomPO.class)
                .eq(ClassroomPO::getBuildingId,buildingId)
                .eq(ClassroomPO::getRoomNum,roomNum)).getRoomId();
        List<Integer> requestIds = new ArrayList<>();
        List<ReqTimepartPO> reqTimepartPOList = reqTimepartDaoMP.selectList(Wrappers.lambdaQuery(ReqTimepartPO.class)
                .eq(ReqTimepartPO::getRoomId,roomId));
        requestIds.addAll(reqTimepartPOList.stream().map(ReqTimepartPO::getRequestId).collect(Collectors.toList()));
        return requestIds;
    }

    @Override
    public boolean examineTimes(String[] s) {
        List<String> allSections = reqTimepartDaoMP.selectAllSections();
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
