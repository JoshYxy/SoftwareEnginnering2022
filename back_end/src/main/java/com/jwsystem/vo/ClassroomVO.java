
package com.jwsystem.vo;


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
}
