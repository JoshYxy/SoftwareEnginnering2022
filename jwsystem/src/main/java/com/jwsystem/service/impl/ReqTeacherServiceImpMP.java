package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.dao.ReqTeacherDaoMP;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.entity.request.ReqTeacherPO;
import com.jwsystem.service.ReqTeacherServiceMP;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    TransUtil transUtil;

    @Override
    public List<RequestDTO> selectAllTeacherRequests() {
        List<ReqTeacherPO> reqTeacherPOList = reqTeacherDaoMP.selectList(null);
        List<RequestDTO> requestDTOList = new ArrayList<>();
        for (ReqTeacherPO r: reqTeacherPOList) {
            if(!r.getExamined()) {
                requestDTOList.add(transUtil.ReqTeacherPOtoDTO(r));
            }
        }
        return requestDTOList;
    }

    @Override
    public RequestDTO selectRequestById(int requestId) {
        return transUtil.ReqTeacherPOtoDTO(reqTeacherDaoMP.selectById(requestId));
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
        ReqTeacherPO requestPO = transUtil.RequestDTOtoPO(requestDTO);
        reqTeacherDaoMP.insert(requestPO);
        return requestPO.getRequestId();
    }
}
