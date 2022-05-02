package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dto.CoursepartDTO;

import com.jwsystem.entity.request.ReqCoursepartPO;

import com.jwsystem.dao.ReqCoursepartDaoMP;
import com.jwsystem.service.ReqCoursepartServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class ReqCoursepartServiceImpMP extends ServiceImpl<ReqCoursepartDaoMP, ReqCoursepartPO> implements ReqCoursepartServiceMP {

    @Autowired
    ReqCoursepartDaoMP reqCoursepartDaoMP;

    @Autowired
    TransUtil transUtil;

    @Override
    public CoursepartDTO selectReqCoursepartByRequestId(int requestId) {
        return transUtil.ReqCpPOtoCpDTO(reqCoursepartDaoMP.selectOne(Wrappers.lambdaQuery(ReqCoursepartPO.class)
                .eq(ReqCoursepartPO::getRequestId,requestId)));
    }

    @Override
    public int insertReqCoursepart(CoursepartDTO coursepartDTO) {
        ReqCoursepartPO reqcoursepartPO = transUtil.CpDTOtoReqCpPO(coursepartDTO);
        return reqCoursepartDaoMP.insert(reqcoursepartPO);
    }
}
