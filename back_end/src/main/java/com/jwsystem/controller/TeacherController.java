//package com.jwsystem.controller;
//
//import com.jwsystem.common.Result;
//import com.jwsystem.entity.Course;
//import com.jwsystem.entity.CourseRequest;
//import com.jwsystem.entity.Teacher;
//import com.jwsystem.util.JwtUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@RestController
//@RequestMapping("/teacher")
//public class TeacherController extends MainController{
//
//    @Autowired
//    private HttpServletRequest request;
//
//    @Autowired
//    private HttpServletResponse response;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private TeacherService teacherService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private CourseRequestService courseRequestService;
//
//    //教师获得自己开设的课程信息
//    @GetMapping("/course")
//    public Result getCourse(){
//        //根据token解析得到的工号，查找该教师开设的对应全部课程并返回
//        String number = getNumByToken();
//        //检查教师是否存在
//        Teacher teacher = teacherService.getByNum(number);
//
//        if(teacher == null){
//            response.setStatus(NO_USER);
//            return Result.fail("教师不存在");
//        }
//
//        List<Course> courseList = courseService.getAllByTeacherNum(number);
//        return Result.succ("获取教师开设课程成功",courseList);
//    }
//
//    //教师提交课程维护申请
//    @PostMapping("/courseRequest")
//    public Result addRequest(@RequestBody CourseRequest courseRequest){
//
//        courseRequestService.insertRequest(courseRequest);
//
//        return Result.succ("提交申请成功");
//    }
//}
