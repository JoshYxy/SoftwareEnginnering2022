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


}
