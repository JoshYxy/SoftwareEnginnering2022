package com.jwsystem.dao;

<<<<<<< Updated upstream
import com.jwsystem.entity.StudentPO;
=======
import com.jwsystem.entity.user.StudentPO;
>>>>>>> Stashed changes
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
public interface StudentDaoMP extends BaseMapper<StudentPO> {

}
