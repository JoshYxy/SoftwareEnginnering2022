package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//building类应该包含room类的对象的list
public class Building {
    private Integer id;
    private String fullName;
    private String abbrName;
    private List<ClassRoom> room;
}
