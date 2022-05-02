package com.jwsystem.vo;

import com.jwsystem.dto.MajorDataDTO;
import com.jwsystem.entity.college.CollegePO;
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
    List<MajorDataDTO> majors;

    public CollegeVO(CollegePO c) {
        this.collegeVOId = c.getCollegeId();
        this.collegeVOName = c.getName();
    }
}
