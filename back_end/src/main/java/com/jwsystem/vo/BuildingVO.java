package com.jwsystem.vo;

import com.jwsystem.entity.BuildingPO;
import com.jwsystem.entity.ClassroomPO;
import com.jwsystem.entity.affair.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingVO extends BuildingPO {
    private Integer id;
    private String fullName;
    private String abbrName;
    private List<ClassroomPO> room;

    public BuildingVO(BuildingPO b) {
        this.id = b.getId();
        this.fullName = b.getFullName();
        this.abbrName = b.getAbbrName();
    }

//    public BuildingVO(BuildingPO b) {
//        id = b.getId();
//        this.fullName = b.getFullName();
//        this.abbrName = b.getAbbrName();
//    }
}
