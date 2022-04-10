package com.jwsystem.service;

import com.jwsystem.entity.Times;

import java.util.List;

public interface TimesService {
    //得到所有上课时间信息
    List<Times> getAllTimes();

    Times findTimesByName(String name);

    //修改某节课开始结束时间
    void changeTimesByName(String name, String startTime, String endTime);

    //增加节数
    void addTimes(Times times);

    //根据名字删除节数
    void deleteTimesByName(String name);
}
