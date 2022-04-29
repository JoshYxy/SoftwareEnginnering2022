package com.jwsystem.service.impl;

<<<<<<< Updated upstream
import com.jwsystem.entity.StudentPO;
=======
import com.jwsystem.entity.user.StudentPO;
>>>>>>> Stashed changes
import com.jwsystem.dao.StudentDaoMP;
import com.jwsystem.service.StudentServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class StudentServiceImpMP extends ServiceImpl<StudentDaoMP, StudentPO> implements StudentServiceMP {

}
