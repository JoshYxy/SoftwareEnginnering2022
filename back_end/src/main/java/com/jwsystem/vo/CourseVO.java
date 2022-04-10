package com.jwsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//前端交互课程类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    private Integer courseId;   //作为主键存储课程
    private String courseName;  //课程名称
    private String courseNum;   //课程编号
    private String collegeName; //学院
    private String classHours;  //学时
    private String credits;     //学分
    private String teacherNum;  //教师工号
    private String teacherName; //教师名称
    private String courseInfo;  //课程介绍
    private int[][] times; //上课时间
    private String building;   //上课楼
    private String roomNum;     //教室号
    private String capacity;    //选课容量
    private String courseTime;  //上课时间的字符串表示（前端处理）
}
