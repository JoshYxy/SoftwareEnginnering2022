package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.service.*;
import com.jwsystem.vo.UserVO;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.CourseVO;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private TransUtil transUtil;

    //@Autowired
    //private StuService stuService;

    @Autowired
    private StudentServiceMP studentServiceMP;

    //@Autowired
    //private CourseServiceImp courseServiceImp;

    @Autowired
    private CoursepartServiceMP coursepartServiceMP;

    @Autowired
    private TimepartServiceMP timepartServiceMP;

   // @Autowired
    //private AdminServiceImp adminServiceImp;

    @Autowired
    private AdminServiceMP adminServiceMP;

    @GetMapping("/course")
    public Result avaliableCourse(){

        if(adminServiceMP.getCur() == "当前不在选课时间段"){
            //从管理员表中查询是否在选课时间段，若不在选课时段
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Result.fail("当前不在选课时间段");
        }

        String number = getNumByToken();
        //UserVO student = stuService.selectStuByNum(number);
        UserVO student = studentServiceMP.selectUserByNumber(number);
        String collegeName = student.getCollege();

        //暂定该专业可选课程为专业所属学院的课程
        //取出该学生所属学院开设的全部课程
        List<CourseVO> courses = new ArrayList<>();
        //根据学院名称取出对应的全部coursePart部分
        List<CoursepartDTO> coursepartDTOList = coursepartServiceMP.selectCoursepartByCollege(collegeName);

        for (CoursepartDTO c:
                coursepartDTOList) {
            List<TimepartDTO> timepartDTOList = timepartServiceMP.selectAllTimepartByCourseId(c.getRelationId());

            //包装成CourseVO的List
            CourseVO tempVO = transUtil.transToVO(c, timepartDTOList);

            courses.add(tempVO);
        }
        return Result.succ("获取可选课程成功",courses);
    }
}
