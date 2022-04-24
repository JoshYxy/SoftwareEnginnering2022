package com.jwsystem.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private Integer requestId;   //作为键的id
    private String type;        //请求类型
    private Integer courseId;
    private String teacherNum;
    private String building;
    private String roomNum;
    private boolean examined; //= false;   //是否被审核过 默认为0
    private boolean passed; //= false; //请求是否通过
}
