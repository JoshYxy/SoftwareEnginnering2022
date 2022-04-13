package com.jwsystem.service;

import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Request;
import com.jwsystem.entity.Timepart;

import java.util.List;

public interface RequestService {

    List<Request> getAllRequests();

    Coursepart getReqCoursepartByRequestId(int requestId);

    List<Timepart> getAllReqTimepartByRequestId(int requestId);

    Request selectRequestById(int requestId);

    void examinedById(int requestId, boolean examined, boolean passed);
}
