package com.jwsystem.service.impl;

import com.jwsystem.entity.AdminPO;
import com.jwsystem.dao.AdminDaoMP;
import com.jwsystem.service.AdminServiceMP;
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
public class AdminServiceImpMP extends ServiceImpl<AdminDaoMP, AdminPO> implements AdminServiceMP {

}
