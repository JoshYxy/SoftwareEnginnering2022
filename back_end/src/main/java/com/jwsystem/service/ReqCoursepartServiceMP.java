package com.jwsystem.service;

<<<<<<< Updated upstream
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.entity.ReqCoursepartPO;
=======
import com.jwsystem.entity.request.ReqCoursepartPO;
>>>>>>> Stashed changes
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface ReqCoursepartServiceMP extends IService<ReqCoursepartPO> {

    CoursepartDTO selectReqCoursepartByRequestId(int requestId);

    int insertReqCoursepart(CoursepartDTO coursepartDTO);
}
