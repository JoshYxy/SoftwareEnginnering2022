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
    private String courseName;  //课程名称
    @CsvBindByName(column = "courseNum")
    private String courseNum;   //课程序号
    @CsvBindByName(column = "collegeId")
    private String collegeId;   //学院id
    @CsvBindByName(column = "collegeName")
    private String collegeName;   //学院
    @CsvBindByName(column = "classHours")
    private Integer classHours;     //学时
    @CsvBindByName(column = "credits")
    private Double credits;    //学分
    @CsvBindByName(column = "teacherNum")
    private String teacherNum;          //教师工号
  //  @CsvBindByName(column = "teacherName")
  //  private String teacherName;
    @CsvBindByName(column = "courseInfo")
    private String courseInfo;
    @CsvBindByName(column = "weekday")
    private Integer weekday;                //星期几（多天的怎么办？）————建立多条数据 1 2 3 4 5 6 7
    private String section;            //节次（如何表示？如何在csv存储？）————1-2 3-4 5-5以这种形式存储
    @CsvBindByName(column = "classroom")
    private String classroom;               //教室
    @CsvBindByName(column = "capacity")
    private Integer capacity;
}





