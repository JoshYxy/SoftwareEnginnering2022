package com.jwsystem.dao;


import com.jwsystem.entity.course.TimepartPO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Mapper
public interface TimepartDaoMP extends BaseMapper<TimepartPO> {
    List<String> selectAllSections();
    List<String> selectSectionByTea(@Param("teacher_num") String teacherNum, Integer weekday);
    List<String> selectSectionByRoom(@Param("room_num")String roomNum,Integer weekday,String building);
}
