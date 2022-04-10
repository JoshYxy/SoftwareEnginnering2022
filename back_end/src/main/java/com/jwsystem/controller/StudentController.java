package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.*;
import com.jwsystem.service.AdminService;
import com.jwsystem.service.StuService;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.CourseUtil;
import com.jwsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController extends MainController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CSVUtils csvUtils;

    @Autowired
    private CourseUtil courseUtil;

    @Autowired
    private StuService stuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseRequestService courseRequestService;

    @GetMapping("/course")
    public Result avaliableCourse(){

        if(adminService.getCurr() == false){
            //从管理员表中查询是否在选课时间段，若不在选课时段
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Result.fail("当前不在选课时间段");
        }

        String number = getNumByToken();
        Student student = stuService.selectStuByNum(number);
        String collegeName = student.getCollege();

        //暂定该专业可选课程为专业所属学院的课程
        //取出该学生所属学院开设的全部课程
        List<CourseVO> courses = new ArrayList<>();
        //根据学院名称取出对应的全部coursePart部分
        List<CoursePart> coursePartList = courseService.getCoursePartByCollege(collegeName);

        for (CoursePart c:
                coursePartList) {
            List<TimePart> timePartList = courseService.getAllTimePartByCourseId(c.getId());

            //包装成CourseVO的List
            CourseVO tempVO = courseUtil.transToVO(c,timePartList);

            courses.add(tempVO);
        }
        return Result.succ("获取可选课程成功",courses);
    }
}
