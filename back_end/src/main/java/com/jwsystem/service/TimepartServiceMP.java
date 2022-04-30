package com.jwsystem.service;


import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.course.TimepartPO;

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
public interface TimepartServiceMP extends IService<TimepartPO> {
    //得到课程时间部分
    List<TimepartDTO> selectAllTimepartByCourseId(int courseId);
    //得到申请课程时间部分
    List<TimepartDTO> selectAllReqTimepartByRequestId(int requestId);
    //插入Timepart
    boolean insertTimepart(TimepartDTO timepartDTO);
    //插入申请的Timepart
    void insertReqTimepart(TimepartDTO timepartDTO);
    //根据教室得到时间部分
    List<TimepartDTO> selectAllTimepartByRoom(String building, String roomNum);
    //根据老师工号得到时间部分
    List<TimepartDTO> selectAllTimepartByTea(String number);
    //根据楼的名字得到courseId
    List<Integer> selectCourseIdByBuilding(String building);
    //根据楼的名字得到requestId
    List<Integer> selectRequestIdByBuilding(String building);
    //根据房间的名字得到courseId
    List<Integer> selectCourseIdByRoom(String building, String roomNum);
    //根据房间的名字得到requestId
    List<Integer> selectRequestIdByRoom(String building, String roomNum);
    //得到timepart 和 req_timepart中所有的section 检验section是否重合
    boolean examineTimes(String[] s);
}
