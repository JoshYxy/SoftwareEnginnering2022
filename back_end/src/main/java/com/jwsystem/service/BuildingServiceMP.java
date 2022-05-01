package com.jwsystem.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.vo.BuildingVO;

import java.util.List;

import com.jwsystem.entity.affair.BuildingPO;
import org.springframework.stereotype.Repository;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Repository
public interface BuildingServiceMP extends IService<BuildingPO> {

    //得到BuildingVOList
    List<BuildingVO> selectAllBuildingAndRoomByList();
    //根据全名找到building
    BuildingPO selectByFullName(String fullName);
    //根据简称找到building
    BuildingPO selectByAbbrName(String abbrName);
    //根据全名删除building
    int deleteByName(String fullName);

}
