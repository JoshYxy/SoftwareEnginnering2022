package com.jwsystem.service;

<<<<<<< Updated upstream
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.ReqTimepartPO;
=======
import com.jwsystem.entity.request.ReqTimepartPO;
>>>>>>> Stashed changes
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
public interface ReqTimepartServiceMP extends IService<ReqTimepartPO> {

    List<TimepartDTO> selectAllReqTimepartByRequestId(int requestId);

    void insertReqTimepart(TimepartDTO timepartDTO);

    List<Integer> selectRequestIdByBuilding(String building);

    List<Integer> selectRequestIdByRoom(String building, String roomNum);
}
