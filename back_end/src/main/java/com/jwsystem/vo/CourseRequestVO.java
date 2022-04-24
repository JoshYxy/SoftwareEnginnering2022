package com.jwsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestVO {
    private Integer requestId;   //作为键的id
    private String type;    //请求类型
    private CourseVO courseVO;  //请求对应的课程
    private boolean examined = false;   //是否被审核过
    private boolean passed = false; //请求是否通过

    public static String ADD = "add";
    public static String DELETE = "delete";
    public static String CHANGE = "change";
}
