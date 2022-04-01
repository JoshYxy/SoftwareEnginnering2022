package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.College;
import com.jwsystem.entity.Major;
import com.jwsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/edu")
public class EduController extends MainController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

}
