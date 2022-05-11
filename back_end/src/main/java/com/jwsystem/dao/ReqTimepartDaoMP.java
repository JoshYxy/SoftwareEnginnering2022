package com.jwsystem.dao;


import com.jwsystem.entity.request.ReqTimepartPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Mapper
public interface ReqTimepartDaoMP extends BaseMapper<ReqTimepartPO> {
    List<String> selectAllSections();
}