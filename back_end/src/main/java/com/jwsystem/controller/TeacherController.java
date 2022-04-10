package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.service.impl.CourseRequestImp;
import com.jwsystem.vo.CourseRequest;
import com.jwsystem.entity.*;
import com.jwsystem.service.TeaService;
import com.jwsystem.service.impl.BuildingServiceImp;
import com.jwsystem.service.impl.CourseServiceImp;
import com.jwsystem.util.CourseUtil;
import com.jwsystem.util.JwtUtils;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//教师相关操作
@RestController
@RequestMapping("/teacher")
public class TeacherController extends MainController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CourseUtil courseUtil;

    @Autowired
    private TeaService teaService;

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private CourseRequestImp courseRequestImp;

    @Autowired
    private BuildingServiceImp buildingServiceImp;

    //教师获得自己开设的课程信息
    @GetMapping("/course")
    public Result getCourse(){
        //根据token解析得到的工号，查找该教师开设的对应全部课程并返回
        String number = getNumByToken();
        //检查教师是否存在
        Teacher teacher = teaService.selectTeaByNum(number);

        if(teacher == null){
            response.setStatus(NO_USER);
            return Result.fail("教师不存在");
        }

        //根据教师工号查出全部的CoursePart，以list返回
        List<Coursepart> coursepartList = courseServiceImp.getAllCoursepartByTeacherNum(number);

        List<CourseVO> courseVOList = new ArrayList<>();

        //对每个CoursePart，根据课程的id找出对应的所有timePart部分
        for (Coursepart c:
                coursepartList) {
            List<Timepart> timepartList = courseServiceImp.getAllTimepartByCourseId(c.getRelationId());

            //包装成CourseVO的List
            CourseVO tempVO = courseUtil.transToVO(c, timepartList);

            courseVOList.add(tempVO);
        }

        //返回所有教室信息
        //需要写buildingVO的部分
        List<BuildingVO> classroom = buildingServiceImp.getAllRooms();

        return Result.succ(courseVOList,classroom,null);
    }

    //教师提交课程维护申请
    @PostMapping("/courseRequest")
    public Result addRequest(@RequestBody CourseRequest courseRequest){

        //把申请的部分插入申请表，返回requestId给我
        int requestId = courseRequestImp.insertRequest(
                courseRequest.getType(),
                courseRequest.getCourseVO().getCourseId(),
                courseRequest.getCourseVO().getTeacherNum(),
                courseRequest.isExamined(),
                courseRequest.isPassed()
        );

        //存和课程相关的部分，存到req-coursePart和req-timePart表里
        //courseVO 截成两段

        CourseVO courseVO = courseRequest.getCourseVO();
        Coursepart coursePart = new Coursepart(
                requestId,
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getCollegeName(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getTeacherName(),
                courseVO.getTeacherNum(),
                courseVO.getCourseInfo(),
                courseVO.getCapacity());
        //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
        //存到req-coursePart表里
        courseServiceImp.insertReqCoursepart(coursePart);

        try{
            for(int i=0;i<7;i++){
                //周i（从0到6，表示周天，周一到周六

                if(courseVO.getTimes()[i].length>0){
                    int[] intArray = courseVO.getTimes()[i];
                    //周i有课，转int数组为String
                    String[] strArray = Arrays.stream(intArray)
                            .mapToObj(String::valueOf)
                            .toArray(String[]::new);

                    String timeString =Arrays.toString(strArray)
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .replace(',',' ');

                    System.out.println(timeString);

                    //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                    Timepart timePart = new Timepart(
                            null,
                            requestId,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString
                    );

                   //因为是申请的课程信息，就不用判断是否冲突了
                    //存到req-timePart表里
                    courseServiceImp.insertReqTimepart(timePart);
//                    if(res == false){
//                        //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
//                        courseService.deleteCoursePartByRequestId(requestId);
//                        //删除本条申请
//                        courseRequestService.deleteById(requestId);
//                        response.setStatus(CONFLICT_TIME);
//                        return Result.fail("插入失败，时间冲突");
//                    }
                }
            }
        } catch (IndexOutOfBoundsException e ){
            response.setStatus(WRONG_DATA);
            return Result.fail("数组越界");
        }
        return Result.succ("提交申请成功");
    }
}
