package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String courseName;
    private String courseId;
    private String college;//学院如何表示才能和学院表联系起来？
    private int classHours;
    private int credits;
    private String teacher;//教师如何表示才能和老师表联系起来？
    private String courseInfo;
    private String time;//星期几，第几节到第几节，如何表示？
    private String classroom;
    private int capacity;
}
