package com.jwsystem.dao;

import com.jwsystem.entity.Timepart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimepartDao {
    List<Timepart> getAllTimepart(Integer collegeId);
}
