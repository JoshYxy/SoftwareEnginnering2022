package com.jwsystem.service;

import com.jwsystem.entity.user.AdminPO;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.vo.UserVO;

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
    UserVO selectAdminUserByNumber(String number);
    //得到当前的选课状态
    String getCur();
    //设置当前的选课状态
    void setCurr(String curricularVariable);
}
