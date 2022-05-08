package com.jwsystem.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.entity.request.ReqRelaCourseMajorPO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface ReqRelaCourseMajorServiceMP extends IService<ReqRelaCourseMajorPO> {

    List<ReqRelaCourseMajorPO> selectByReqId(Integer requestId);
}
