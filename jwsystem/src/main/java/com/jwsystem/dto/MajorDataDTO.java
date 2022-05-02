package com.jwsystem.dto;

import com.jwsystem.entity.college.MajorPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorDataDTO {
    Integer majorId;
    String majorName;

    public MajorDataDTO(MajorPO majorPO) {
        this.majorId = majorPO.getMajorId();
        this.majorName = majorPO.getName();
    }
}