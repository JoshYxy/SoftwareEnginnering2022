package com.jwsystem.service;


import com.jwsystem.entity.college.CollegePO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.vo.CollegeVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface CollegeServiceMP extends IService<CollegePO> {
    //得到所有的学院和专业
    List<CollegeVO> selectAllCollegeAndMajorByList();
    //插入学院 判断该学院名不存在
    Boolean insertCollege(CollegePO collegePO);
    //根据学院名查找学院
    CollegePO selectCollegeByName(String collegeName);
    boolean findOthersByCollege(CollegePO collegePO);
}