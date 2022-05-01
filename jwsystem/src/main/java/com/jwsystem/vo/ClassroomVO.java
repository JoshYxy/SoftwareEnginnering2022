
package com.jwsystem.vo;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.college.MajorPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomVO /*extends ClassroomPO*/ {
    private Integer roomId;
    private String building;
    private String roomNum;
    private String capacity;

    public ClassroomVO(ClassroomPO c) {
        this.roomId = c.getRoomId();
        this.roomNum = c.getRoomNum();
        this.capacity = c.getCapacity();
    }

}
