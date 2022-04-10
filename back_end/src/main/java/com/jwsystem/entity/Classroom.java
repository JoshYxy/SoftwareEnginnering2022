package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//room类，表示教室
public class Classroom {
    private Integer roomId;
    private String building;
    private String roomNum;
}
