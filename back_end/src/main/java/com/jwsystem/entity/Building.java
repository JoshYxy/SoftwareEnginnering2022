package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    private Integer id;
    private String fullName;
    private String abbrName;
}
