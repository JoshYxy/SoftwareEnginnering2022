package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.dao.TimesDaoMP;
import com.jwsystem.entity.affair.TimesPO;
import com.jwsystem.service.TimesServiceMP;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TimesServiceImpMP extends ServiceImpl<TimesDaoMP, TimesPO> implements TimesServiceMP {

    @Autowired
    TimesDaoMP timesDaoMP;

    @Override
    public TimesPO selectTimesByName(String name) {
        return timesDaoMP.selectOne(Wrappers.lambdaQuery(TimesPO.class)
                .eq(TimesPO::getName,name));
    }

    @Override
    public void updateTimesByName(String name, String startTime, String endTime) {
        timesDaoMP.update(null,Wrappers.lambdaUpdate(TimesPO.class)
                .eq(TimesPO::getName,name)
                .set(TimesPO::getStartTime,startTime)
                .set(TimesPO::getEndTime,endTime));
    }

    @Override
    public void deleteTimesByName(String name) {
        timesDaoMP.delete(Wrappers.lambdaQuery(TimesPO.class)
                .eq(TimesPO::getName,name));
    }
}
