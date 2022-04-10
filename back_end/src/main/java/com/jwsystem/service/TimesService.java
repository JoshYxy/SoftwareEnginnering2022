package com.jwsystem.service;

import com.jwsystem.entity.Times;

import java.util.List;

public interface TimesService {
    //得到所有上课时间信息
    List<Times> getAllTimes();
}
