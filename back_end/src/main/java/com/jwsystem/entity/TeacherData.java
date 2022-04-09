package com.jwsystem.entity;

import lombok.Data;

import java.util.List;

@Data
public class TeacherData {
    private String collegeName;
    private List<Teacher> teachers;
}
