package com.jwsystem.dto;

import com.jwsystem.entity.college.MajorPO;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MajorDTO {
    private Integer majorId;
    private String name;
    private String collegeName;
}
