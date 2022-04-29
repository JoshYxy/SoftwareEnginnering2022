package com.jwsystem.service.impl;

<<<<<<< Updated upstream
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.AdminDao;
import com.jwsystem.dto.User;
import com.jwsystem.entity.AdminPO;
import com.jwsystem.dao.AdminDaoMP;
import com.jwsystem.entity.user.Admin;
import com.jwsystem.service.AdminServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

=======
import com.jwsystem.entity.user.AdminPO;
import com.jwsystem.dao.AdminDaoMP;
import com.jwsystem.service.AdminServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    @Autowired
    AdminDaoMP adminDaoMP;

    @Override
    public User selectAdminUserByNumber(String number) {
        LambdaQueryWrapper<AdminPO> wrapper = Wrappers.lambdaQuery(AdminPO.class)
                .eq(AdminPO::getNumber,number);
        AdminPO adminPO = adminDaoMP.selectOne(wrapper);
        User user = Optional.ofNullable(adminPO).map(User::new).orElse(null);
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
        LambdaQueryWrapper<AdminPO> wrapper = Wrappers.lambdaQuery(AdminPO.class)
                .select(AdminPO::getCurricularVariable);
        adminDaoMP.selectOne(wrapper).setCurricularVariable(curricularVariable);
    }
=======
>>>>>>> Stashed changes
}
