package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.entity.request.ReqCoursepartPO;
import com.jwsystem.service.*;
import com.jwsystem.service.impl.RelaCourseStudentServiceImpMP;
import com.jwsystem.util.CommonUtil;
import com.jwsystem.vo.SelectedStudentVO;
import com.jwsystem.vo.UserVO;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.CourseRequestVO;
import com.jwsystem.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.util.*;

//教师相关操作
@RestController
@RequestMapping("/teacher")
public class TeacherController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private TransUtil transUtil;

    @Autowired
    private TeacherServiceMP teacherServiceMP;

    @Autowired
    private CoursepartServiceMP coursepartServiceMP;

    @Autowired
    private ReqCoursepartServiceMP reqCoursepartServiceMP;

    @Autowired
    private ReqTeacherServiceMP reqTeacherServiceMP;

    @Autowired
    private TimepartServiceMP timepartServiceMP;

    @Autowired
    private ReqTimepartServiceMP reqTimepartServiceMP;

    @Autowired
    private RelaCourseStudentServiceMP relaCourseStudentServiceMP;

    @Autowired
    private StudentServiceMP studentServiceMP;

    @Autowired
    private CommonUtil commonUtil;


    //教师获得自己开设的所有课程信息
    @GetMapping("/course")
    public Result getCourse(){
        //根据token解析得到的工号，查找该教师开设的对应全部课程并返回
        String number = getNumByToken();
        //检查教师是否存在
        UserVO teacher = teacherServiceMP.selectUserByNumber(number);

        if(teacher == null){
            response.setStatus(NO_USER);
            return Result.fail("教师不存在");
        }

        //根据教师工号查出全部的CoursePart，以list返回
        List<CoursepartDTO> coursepartDTOList = coursepartServiceMP.selectAllCoursepartByTeacherNum(number);

        List<CourseVO> courseVOList = new ArrayList<>();

        //对每个CoursePart，根据课程的id找出对应的所有timePart部分
        for (CoursepartDTO c:
                coursepartDTOList) {
            List<TimepartDTO> timepartDTOList = timepartServiceMP.selectAllTimepartByCourseId(c.getRelationId());

            //包装成CourseVO的List
            CourseVO tempVO = transUtil.transToVO(c, timepartDTOList,true);

            courseVOList.add(tempVO);
        }

        return Result.succ3(courseVOList,teacher,null);
    }

    //根据老师token返回当前学期全部有课时间
    @GetMapping("/time")
    public Result getTime(){
        //根据token解析得到的工号，查找该教师开设的对应全部课程并返回
        String number = getNumByToken();
        //检查教师是否存在
        UserVO teacher = teacherServiceMP.selectUserByNumber(number);

        if(teacher == null){
            response.setStatus(NO_USER);
            return Result.fail("教师不存在");
        }

        List<CoursepartDTO> temps = coursepartServiceMP.selectAllCoursepartByTeacherNum(number);

        //选出当前学年学期的课程
        String year = commonUtil.getSchoolYear();
        String semester = commonUtil.getSemester();
        List<TimepartDTO> timepartDTOList = new ArrayList<>();

        //如果课程是当前学年学期的，将其timepart加入
        for(CoursepartDTO c:
        temps){
            if(c.getSemester().equals(semester) && c.getYear().equals(year)) timepartDTOList.addAll(timepartServiceMP.selectAllTimepartByCourseId(c.getRelationId()));
        }

        int[][] time = new int[7][];
        for (TimepartDTO t:
                timepartDTOList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            if(time[i]==null){
                //首次录入该天的上课时间数据，新建一个数组
                time[i] = new int[s.length];
                for(int cnt=0; cnt<s.length; cnt++){
                    time[i][cnt]=Integer.parseInt(s[cnt]);
                }
            }
            else{
                //多次录入，需要将数组进行从小到大拼接
                int l = time[i].length + s.length;
                int[] temp = new int[l];
                List<Integer> tempList = new ArrayList<>();
                for(int cnt=0;cnt<time[i].length;cnt++){
                    tempList.add(time[i][cnt]);
                }

                for (String value : s) {
                    tempList.add(Integer.parseInt(value));
                }
                Collections.sort(tempList);

                for(int cnt=0;cnt<l;cnt++){
                    temp[cnt] = tempList.get(cnt);
                }

                time[i]=temp;
            }
        }

        for(int i=0;i<7;i++){
            if(time[i]==null) time[i] = new int[0];
        }

        return Result.succ(time);
    }

    //教师提交课程维护申请
    @PostMapping("/courseRequest")
    public Result addRequest(@RequestBody CourseRequestVO courseRequestVO){


        RequestDTO requestDTO = new RequestDTO(
                courseRequestVO.getRequestId(),
                courseRequestVO.getType(),
                courseRequestVO.getCourseVO().getCourseId(),
                courseRequestVO.getCourseVO().getTeacherNum(),
                courseRequestVO.getCourseVO().getBuilding(),
                courseRequestVO.getCourseVO().getRoomNum(),
                courseRequestVO.isExamined(),
                courseRequestVO.isPassed()
        );

        //把申请插入申请表，返回requestId给我
        int requestId = reqTeacherServiceMP.insertRequest(requestDTO);
        //存和课程相关的部分，存到req-coursePart和req-timePart表里
        //courseVO 截成两段

        CourseVO courseVO = courseRequestVO.getCourseVO();

        CoursepartDTO coursePart = new CoursepartDTO(
                requestId,
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getCourseInfo(),
                courseVO.getCollegeName(),
                courseVO.getTeacherNum(),
                courseVO.getTeacherName(),
                courseVO.getCapacity(),
                courseVO.getYear(),
                courseVO.getSemester(),
                courseVO.getIsGeneral());
        //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
        //存到req-coursePart表里
        ReqCoursepartPO reqCoursepartPO = transUtil.CpDTOtoReqCpPO(coursePart);
        reqCoursepartServiceMP.saveOrUpdate(reqCoursepartPO);

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
                    TimepartDTO timePart = new TimepartDTO(
                            //null,
                            requestId,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString
                    );

                   //因为是申请的课程信息，就不用判断是否冲突了
                    //存到req-timePart表里
                    reqTimepartServiceMP.insertReqTimepart(timePart);
                }
            }
        } catch (IndexOutOfBoundsException e ){
            response.setStatus(WRONG_DATA);
            return Result.fail("数组越界");
        }
        return Result.succ("提交申请成功");
    }

    //获得某门课程已选/已修学生名单
    @GetMapping("/selectedList")
    public Result selected(@RequestBody Map<String,Integer> map){

        int courseId = map.get("courseId");
        //根据课程id取出已选或已修名单（当前学期为已选，以前的课为已修，因此只传了id进去）
        List<RelaCourseStudentPO> relaCourseStudentPOList = relaCourseStudentServiceMP.xxx(courseId);

        List<SelectedStudentVO> selectedStudentVOS = new ArrayList<>();

        for (RelaCourseStudentPO rela:
                relaCourseStudentPOList) {
            UserVO student = transUtil.StudentPOtoUserVO(studentServiceMP.getById(rela.getStudentNum()));
            SelectedStudentVO s = new SelectedStudentVO(
                    student.getName(),
                    student.getNumber(),
                    student.getMajor()
            );

            selectedStudentVOS.add(s);
        }

        return Result.succ(selectedStudentVOS);
    }
}
