package com.jwsystem.entity.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timepart {
    private Integer id;
    private Integer relationId; //courseId或requestId
    private String teacherNum;  //教师工号
    private String building;    //上课楼
    private String roomNum;     //教室号
    private Integer weekday;    //星期几
    private String section;     //节次
}
