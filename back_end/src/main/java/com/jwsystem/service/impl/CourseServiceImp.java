package com.jwsystem.service.impl;

import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.TimepartDao;
import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Timepart;
import com.jwsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService {
    @Autowired
    CoursepartDao coursepartDao;

    @Autowired
    TimepartDao timepartDao;

    @Override
    public List<Coursepart> getAllCoursepart(){
        return coursepartDao.getCoursepartInfos();
    }

    @Override
    public List<Timepart> getAllTimepartByCourseId(Integer courseId){
        return timepartDao.getAllTimepart(courseId);
    }
}
