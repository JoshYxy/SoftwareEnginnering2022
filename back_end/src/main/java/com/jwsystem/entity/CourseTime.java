package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTime {
    private String weekday; //星期几
    private Integer startWeek;  //起始周
    private Integer endWeek;    //结束周
    private Integer startCourse;//起始节数
    private Integer endCourse;  //结束节数
    private boolean odd;    //是否仅单周上课
    private boolean even;   //是否仅双周上课
}
