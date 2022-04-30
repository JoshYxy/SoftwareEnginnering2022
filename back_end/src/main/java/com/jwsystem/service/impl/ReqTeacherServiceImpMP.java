package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.entity.request.ReqTeacherPO;

import com.jwsystem.dao.ReqTeacherDaoMP;
import com.jwsystem.service.ReqTeacherServiceMP;
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
public class ReqTeacherServiceImpMP extends ServiceImpl<ReqTeacherDaoMP, ReqTeacherPO> implements ReqTeacherServiceMP {
    @Autowired
    ReqTeacherDaoMP reqTeacherDaoMP;

    @Override
    public List<RequestDTO> selectAllTeacherRequests() {
        reqTeacherDaoMP.selectList(null);
        return null;
    }

    @Override
    public RequestDTO selectRequestById(int requestId) {
        reqTeacherDaoMP.selectById(requestId);
        return null;
    }

    @Override
    public void examinedById(int requestId, boolean examined, boolean passed) {
        reqTeacherDaoMP.update(null,Wrappers.lambdaUpdate(ReqTeacherPO.class)
                .eq(ReqTeacherPO::getRequestId,requestId)
                .set(ReqTeacherPO::getExamined,examined)
                .set(ReqTeacherPO::getPassed,passed));
    }

    @Override
    public int insertRequest(RequestDTO requestDTO) {
        reqTeacherDaoMP.insert(requestPO);
        return 0;
    }
}
