
package com.jwsystem.vo;


import com.jwsystem.entity.affair.ClassroomPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomVO {
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
