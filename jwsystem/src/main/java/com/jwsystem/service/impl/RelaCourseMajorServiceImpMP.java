package com.jwsystem.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.entity.course.RelaCourseMajorPO;
import com.jwsystem.dao.RelaCourseMajorDaoMP;
import com.jwsystem.service.RelaCourseMajorServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class RelaCourseMajorServiceImpMP extends ServiceImpl<RelaCourseMajorDaoMP, RelaCourseMajorPO> implements RelaCourseMajorServiceMP {

    @Autowired
    RelaCourseMajorDaoMP relaCourseMajorDaoMP;
    @Override
    public List<RelaCourseMajorPO> selectByCourseId(Integer i) {
        return relaCourseMajorDaoMP.selectList(Wrappers.lambdaQuery(RelaCourseMajorPO.class)
                .eq(RelaCourseMajorPO::getCourseId,i));
    }

    @Override
    public void deleteAllByCourseId(int courseId) {
        relaCourseMajorDaoMP.delete(Wrappers.lambdaQuery(RelaCourseMajorPO.class)
                .eq(RelaCourseMajorPO::getCourseId,courseId));
    }
}
