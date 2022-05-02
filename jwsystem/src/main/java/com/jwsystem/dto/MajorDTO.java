package com.jwsystem.dto;

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
}
