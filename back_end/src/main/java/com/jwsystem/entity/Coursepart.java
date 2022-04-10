package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coursepart {
    private Integer relationId;         //courseId或者requestId
    private String courseName;  //课程名称
    private String courseNum;   //课程编号
    private String classHours;  //学时
    private String credits;     //学分
    private String courseInfo;  //课程介绍
    private String collegeName; //学院
    private String teacherNum;  //教师工号
    private String teacherName; //教师名称
    private String capacity;    //选课容量
}
