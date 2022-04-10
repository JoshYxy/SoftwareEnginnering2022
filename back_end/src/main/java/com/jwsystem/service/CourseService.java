package com.jwsystem.service;

import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Timepart;

import java.util.List;

public interface CourseService {

    List<Coursepart> getAllCoursepart();

    List<Timepart> getAllTimepartByCourseId(Integer courseId);
}
