package com.jwsystem.vo;

import com.jwsystem.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingVO {
    private Integer id;
    private String fullName;
    private String abbrName;
    private List<Classroom> room;
}
