package com.jwsystem.service;

import com.jwsystem.dto.User;
import com.jwsystem.entity.AdminPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface AdminServiceMP extends IService<AdminPO> {
    //通过工号查找AdminUser
    User selectAdminUserByNumber(String number);
    //得到当前的选课状态
    String getCur();
    //设置当前的选课状态
    void setCurr(String curricularVariable);
}
