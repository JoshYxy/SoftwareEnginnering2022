package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.jwsystem.dao.AdminDaoMP;

import com.jwsystem.service.AdminServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


import com.jwsystem.entity.user.AdminPO;

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


    @Autowired
    AdminDaoMP adminDaoMP;

    @Override
    public UserVO selectAdminUserByNumber(String number) {
        LambdaQueryWrapper<AdminPO> wrapper = Wrappers.lambdaQuery(AdminPO.class)
                .eq(AdminPO::getNumber,number);
        AdminPO adminPO = adminDaoMP.selectOne(wrapper);
        UserVO user = Optional.ofNullable(adminPO).map(UserVO::new).orElse(null);
        return user;
    }

    @Override
    public String getCur() {
        LambdaQueryWrapper<AdminPO> wrapper = Wrappers.lambdaQuery(AdminPO.class)
                .select(AdminPO::getCurricularVariable);
        return adminDaoMP.selectOne(wrapper).getCurricularVariable();
    }

    @Override
    public void setCurr(String curricularVariable) {
        LambdaUpdateWrapper<AdminPO> wrapper = Wrappers.lambdaUpdate(AdminPO.class)
                .set(AdminPO::getCurricularVariable,curricularVariable);
        adminDaoMP.update(null,wrapper);
    }

}
