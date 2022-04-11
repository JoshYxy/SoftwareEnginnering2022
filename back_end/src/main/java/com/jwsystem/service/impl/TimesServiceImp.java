package com.jwsystem.service.impl;

import com.jwsystem.dao.TimesDao;
import com.jwsystem.entity.Times;
import com.jwsystem.service.TimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesServiceImp implements TimesService {
    @Autowired
    TimesDao timesDao;

    //得到所有上课时间信息
    @Override
    public List<Times> getAllTimes(){
        return timesDao.getAllTimes();
    }

    @Override
    public Times findTimesByName(String name){
        return timesDao.findTimesByName(name);
    }

    //修改某节课开始结束时间
    @Override
    public void changeTimesByName(String name, String startTime, String endTime){
        timesDao.changeTimesByName(name,startTime,endTime);
    }

    //增加节数
    @Override
    public void addTimes(Times times){
        timesDao.addTimes(times.getName(),times.getStartTime(),times.getEndTime());
    }

    //根据名字删除节数
    @Override
    public void deleteTimesByName(String name){
        timesDao.deleteTimesByName(name);
    }

    @Override
    public void deleteAll() {
        timesDao.deleteAll();
    }
}
