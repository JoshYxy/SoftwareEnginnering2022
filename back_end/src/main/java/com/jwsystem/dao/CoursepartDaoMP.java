package com.jwsystem.dao;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.entity.course.CoursepartPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Mapper
public interface CoursepartDaoMP extends BaseMapper<CoursepartPO> {
    //coursepartpo转成coursepartdto
    CoursepartDTO coursepartPO2DTO();

}
