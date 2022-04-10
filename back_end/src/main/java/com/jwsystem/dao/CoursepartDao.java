package com.jwsystem.dao;

import com.jwsystem.entity.Coursepart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursepartDao {
    List<Coursepart> getCoursepartInfos();

}
