package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class College {
    private Integer collegeId;
    private String name;
    //private List<MajorVO> majors;
}