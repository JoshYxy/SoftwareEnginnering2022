package com.jwsystem.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//CSV交互课程类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @CsvBindByName(column = "courseName")
    private String courseName;  //课程名称
    @CsvBindByName(column = "courseNum")
    private String courseNum;   //课程编号
    @CsvBindByName(column = "collegeName")
    private String collegeName; //学院名称
    @CsvBindByName(column = "classHours")
    private String classHours;  //学时
    @CsvBindByName(column = "credits")
    private String credits;     //学分
    @CsvBindByName(column = "teacherName")
    private String teacherName;  //教师姓名
    @CsvBindByName(column = "teacherNum")
    private String teacherNum;  //教师工号
    @CsvBindByName(column = "courseInfo")
    private String courseInfo;  //课程介绍
    @CsvBindByName(column = "building")
    private String building;   //上课楼
    @CsvBindByName(column = "roomNum")
    private String roomNum;     //教室号
    @CsvBindByName(column = "capacity")
    private String capacity;    //选课容量
    @CsvBindByName(column = "Mon")
    private String Mon;
    @CsvBindByName(column = "Tue")
    private String Tue;
    @CsvBindByName(column = "Wed")
    private String Wed;
    @CsvBindByName(column = "Thu")
    private String Thu;
    @CsvBindByName(column = "Fri")
    private String Fri;
    @CsvBindByName(column = "Sat")
    private String Sat;
    @CsvBindByName(column = "Sun")
    private String Sun;
    @CsvBindByName(column = "year")
    private String year;
    @CsvBindByName(column = "semester")
    private String semester;
    @CsvBindByName(column = "isGeneral")
    private String isGeneral;
    @CsvBindByName(column = "majors")
    private String majors;
}





