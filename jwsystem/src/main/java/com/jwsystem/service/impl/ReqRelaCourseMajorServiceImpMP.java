package com.jwsystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.dao.ReqRelaCourseMajorDaoMP;
import com.jwsystem.entity.request.ReqRelaCourseMajorPO;
import com.jwsystem.service.ReqRelaCourseMajorServiceMP;
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
public class ReqRelaCourseMajorServiceImpMP extends ServiceImpl<ReqRelaCourseMajorDaoMP, ReqRelaCourseMajorPO> implements ReqRelaCourseMajorServiceMP {

    @Autowired
    ReqRelaCourseMajorDaoMP reqRelaCourseMajorDaoMP;
    @Override
    public List<ReqRelaCourseMajorPO> selectByReqId(Integer requestId) {
        return reqRelaCourseMajorDaoMP.selectList(Wrappers.lambdaQuery(ReqRelaCourseMajorPO.class)
                .eq(ReqRelaCourseMajorPO::getRequestId,requestId));
    }
}
