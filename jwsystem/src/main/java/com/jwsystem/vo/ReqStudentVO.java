package com.jwsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqStudentVO {
    private int requestId;      //请求id
    private String applyReason; //请求理由
    private CourseVO courseVO;  //请求对应的课程
    private UserVO student;     //申请的学生
    private boolean dealt;      //是否被审核
    private boolean approved;   //是否通过
}
