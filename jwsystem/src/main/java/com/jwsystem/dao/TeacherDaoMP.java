package com.jwsystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwsystem.entity.user.TeacherPO;
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
public interface TeacherDaoMP extends BaseMapper<TeacherPO> {

    //List<UserVO> selectAllUserInfos();
}
