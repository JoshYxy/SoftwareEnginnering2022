package com.jwsystem.vo;

import com.jwsystem.dto.MajorDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeVO /*extends College*/{
    //把id加上
    Integer collegeVOId;
    String collegeVOName;
    //Map<Integer,String> majors;
    List<MajorDataDTO> majors;
}
