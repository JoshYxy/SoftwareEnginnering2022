package com.jwsystem.dao;


import com.jwsystem.entity.user.StudentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwsystem.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

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
public interface StudentDaoMP extends BaseMapper<StudentPO> {
    //查看所有学生信息
    //List<UserVO> selectAllUserInfos();
}
