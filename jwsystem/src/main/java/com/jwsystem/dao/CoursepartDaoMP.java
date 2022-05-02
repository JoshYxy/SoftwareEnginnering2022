package com.jwsystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwsystem.entity.course.CoursepartPO;
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

}
