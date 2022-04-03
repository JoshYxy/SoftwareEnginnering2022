package com.jwsystem.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @CsvBindByName(column = "courseId")
    private Integer courseId;   //作为主键存储课程？
    @CsvBindByName(column = "courseName")
    private String courseName;
    @CsvBindByName(column = "courseNum")
    private String courseNum;
    @CsvBindByName(column = "collegeName")
    private String collegeName;
    @CsvBindByName(column = "classHours")
    private Integer classHours;
    @CsvBindByName(column = "credits")
    private Integer credits;
    @CsvBindByName(column = "teacherNum")
    private String teacherNum;          //教师工号
    @CsvBindByName(column = "teacherName")
    private String teacherName;
    @CsvBindByName(column = "courseInfo")
    private String courseInfo;
    @CsvBindByName(column = "weekday")
    private String weekday;                //星期几（多天的怎么办？）
    private int[] numOfSections;            //节次（如何表示？如何在csv存储？）
    @CsvBindByName(column = "classroom")
    private String classroom;               //教室
    @CsvBindByName(column = "capacity")
    private Integer capacity;
}
