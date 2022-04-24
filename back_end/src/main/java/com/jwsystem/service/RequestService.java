package com.jwsystem.service;

import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.entity.request.Request;
import com.jwsystem.entity.course.Timepart;

import java.util.List;

public interface RequestService {

    List<Request> getAllRequests();

    Coursepart getReqCoursepartByRequestId(int requestId);

    List<Timepart> getAllReqTimepartByRequestId(int requestId);

    Request selectRequestById(int requestId);

    void examinedById(int requestId, boolean examined, boolean passed);
}
