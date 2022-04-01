//package com.jwsystem.entity;
//
//import com.opencsv.bean.CsvBindByName;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Course {
//    @CsvBindByName(column = "courseId")
//    private Integer courseId;   //作为主键存储课程？
//    @CsvBindByName(column = "courseName")
//    private String courseName;
//<<<<<<< HEAD
//    private String courseId;    /*courseNumber*/
//    private String college;//学院如何表示才能和学院表联系起来？
//    private Integer classHours;
//    private Integer credits;
//    private String teacher;//教师如何表示才能和老师表联系起来？
//=======
//    @CsvBindByName(column = "courseNum")
//    private String courseNum;
//    @CsvBindByName(column = "collegeId")
//    private String collegeId;//课程里学院应该没必要和学院表关联吧，感觉没有意义
//    @CsvBindByName(column = "collegeName")
//    private String collegeName;
//    @CsvBindByName(column = "classHours")
//    private Integer classHours;
//    @CsvBindByName(column = "credits")
//    private Integer credits;
//    @CsvBindByName(column = "teacherNum")
//    private String teacherNum;//教师如何表示才能和老师表联系起来？用教师工号？
//    @CsvBindByName(column = "teacherName")
//    private String teacherName;
//    @CsvBindByName(column = "courseInfo")
//>>>>>>> Andy
//    private String courseInfo;
//    @CsvBindByName(column = "time")
//    private String time;//星期几，第几节到第几节
//    @CsvBindByName(column = "classroom")
//    private String classroom;
//<<<<<<< HEAD
//    private Integer capacity;
//}
//=======
//    @CsvBindByName(column = "capacity")
//    private Integer capacity;
//}
//>>>>>>> Andy
