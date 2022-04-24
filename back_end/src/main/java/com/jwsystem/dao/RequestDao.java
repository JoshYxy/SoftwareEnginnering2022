package com.jwsystem.dao;

import com.jwsystem.entity.request.Request;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestDao {
    List<Request> getAllRequests();
    Request selectRequestById(int requestId);
    int insertRequest(Request request);
    void examinedById(@Param("request_id") int requestId, boolean examined, boolean passed);
    void deleteReqByRequestId(int requestId);
}
