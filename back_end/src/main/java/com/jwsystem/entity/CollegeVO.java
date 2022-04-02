package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeVO extends College{
    //把id加上
    Integer collegeVOId;
    String collegeVOName;
    Map<Integer,String> majors;
}
