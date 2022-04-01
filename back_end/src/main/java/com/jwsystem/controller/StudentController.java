package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.Course;
import com.jwsystem.entity.Student;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private StudentService studentService;

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

        //怎样制定该专业可选课程的标准？暂定为专业所属学院的课程吧
        String number = getNumByToken();
        Student student = studentService.getByNumber(number);
        if(student == null){
            response.setStatus(NO_USER);
            return Result.fail("该学生不存在");
        }

        Integer id = student.getCollege().getCollegeId();
        //取出该学生所属学院开设的全部课程
        List<Course> courseList = courseService.getAllById(id);
        return Result.succ("获取可选课程成功",courseList);
    }
}
