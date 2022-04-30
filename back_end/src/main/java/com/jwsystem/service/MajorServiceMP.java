package com.jwsystem.service;

import com.jwsystem.dto.MajorDTO;
import com.jwsystem.entity.college.MajorPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface MajorServiceMP extends IService<MajorPO> {
    //插入专业，判专业是否存在
    Boolean insertMajor(MajorDTO majorDTO);
    //根据专业名查找专业
    MajorDTO selectMajorByName(String majorName);
    //判断专业和学院是否对应
    Boolean judgeMajorAndCollege(String majorName,String collegeName);
    //修改专业名
    void updateMajor(MajorDTO majorDTO);
    //删除专业
    void deleteMajor(MajorDTO majorDTO);
    boolean findOthersByMajor(MajorDTO majorDTO);
}
