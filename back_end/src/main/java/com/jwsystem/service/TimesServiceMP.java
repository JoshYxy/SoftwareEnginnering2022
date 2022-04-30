package com.jwsystem.service;


import com.jwsystem.entity.affair.TimesPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface TimesServiceMP extends IService<TimesPO> {
    TimesPO selectTimesByName(String name);
    void updateTimesByName(String name,String startTime,String endTime);
    void deleteTimesByName(String name);
}
