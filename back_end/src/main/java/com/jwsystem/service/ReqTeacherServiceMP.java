package com.jwsystem.service;

import com.jwsystem.dto.RequestDTO;
import com.jwsystem.entity.request.ReqTeacherPO;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface ReqTeacherServiceMP extends IService<ReqTeacherPO> {
    //得到所有老师的课程申请
    List<RequestDTO> selectAllTeacherRequests();
    //通过request_id（主键）得到申请
    RequestDTO selectRequestById(int requestId);
    //根据requestID更新examined passed
    void examinedById(int requestId, boolean examined, boolean passed);
    //插入申请 先转类型 再insert
    int insertRequest(RequestDTO requestDTO);
}
