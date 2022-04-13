package com.jwsystem.vo;

import com.jwsystem.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherData {
    private String collegeName;
    private List<Teacher> teachers;
}
