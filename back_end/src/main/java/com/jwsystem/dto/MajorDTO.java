package com.jwsystem.dto;

import com.jwsystem.entity.college.MajorPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorDTO {
    private Integer majorId;
    private String name;
    private String collegeName;

    public MajorDTO(MajorPO majorPO) {
        this.majorId = majorPO.getMajorId();
        this.name = majorPO.getName();
    }
}
