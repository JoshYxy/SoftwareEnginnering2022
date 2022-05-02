package com.jwsystem.vo;


import com.jwsystem.entity.affair.BuildingPO;
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

    public BuildingVO(BuildingPO b) {
        id = b.getId();
        this.fullName = b.getFullName();
        this.abbrName = b.getAbbrName();
    }

    private List<ClassroomVO> room;
}
