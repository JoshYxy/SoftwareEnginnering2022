package com.jwsystem.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.jwsystem.common.Result;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.course.RelaCourseMajorPO;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.entity.request.ReqStudentPO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.service.*;
import com.jwsystem.service.impl.CoursepartServiceImpMP;
import com.jwsystem.service.impl.MajorServiceImpMP;
import com.jwsystem.service.impl.RelaCourseMajorServiceImpMP;
import com.jwsystem.service.impl.TimepartServiceImpMP;
import com.jwsystem.util.CommonUtil;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.vo.ReqStudentVO;
import com.jwsystem.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jwsystem.entity.course.CoursepartPO.GENERAL;
import static com.jwsystem.entity.course.RelaCourseStudentPO.SELECTED;
import static com.jwsystem.entity.course.RelaCourseStudentPO.STUDIED;
import static com.jwsystem.entity.user.AdminPO.*;

@RestController
@RequestMapping("/student")
public class StudentController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private TransUtil transUtil;

    @Autowired
    private StudentServiceMP studentServiceMP;

    @Autowired
    private CoursepartServiceMP coursepartServiceMP;

    @Autowired
    private TimepartServiceMP timepartServiceMP;

    @Autowired
    private AdminServiceMP adminServiceMP;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RelaCourseStudentServiceMP relaCourseStudentServiceMP;

    @Autowired
    private RelaCourseMajorServiceMP relaCourseMajorServiceMP;

    @Autowired
    private ReqStudentServiceMP reqStudentServiceMP;

    @Autowired
    private TimepartServiceImpMP timepartServiceImpMP;

    @Autowired
    private CoursepartServiceImpMP coursepartServiceImpMP;


    //选课时间段获取本学期全部课程
    @GetMapping("/course")
    public Result avaliableCourse(){

        if(adminServiceMP.getCur().equals(ROUND_ONE_END) || adminServiceMP.getCur().equals(ROUND_TWO_END) || adminServiceMP.getCur().equals(CLOSE)){
            //从管理员表中查询是否在选课时间段，若不在选课时段
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Result.fail("当前不在选课时间段");
        }

        //返回当前学年当前学期所有课程
        //根据系统时间的年月确定所属学年学期，然后根据学年学期查找所有课程并返回
        String schoolYear = commonUtil.getSchoolYear();
        String semester = commonUtil.getSemester();

        //返回当前学年学期的所有coursepartPO
        List<CoursepartPO> coursepartPOList = coursepartServiceMP.selectByYearAndSemester(schoolYear,semester);

        List<CourseVO> courses = new ArrayList<>();

        for (CoursepartPO cp:
             coursepartPOList) {
            CoursepartDTO coursepartDTO = transUtil.CpPOtoCpDTO(cp);
            List<TimepartDTO> timepartDTOList = timepartServiceMP.selectAllTimepartByCourseId(cp.getCourseId());
            //包装成CourseVO的List
            CourseVO tempVO = transUtil.transToVO(coursepartDTO, timepartDTOList,true);
            courses.add(tempVO);
        }

        return Result.succ("获取可选课程成功",courses);
    }

    //获得自己的已选课程
    @GetMapping("/selectedList")
    public Result selected(){
        String number = getNumByToken();

        //根据学号查找所有已选课程,注意不要是已修
        List<RelaCourseStudentPO> courseStudentPOS = relaCourseStudentServiceMP.selectCourseWithStatus(number,SELECTED);

        List<CourseVO> courseVOS = new ArrayList<>();

        for (RelaCourseStudentPO rela:
                courseStudentPOS) {
            CourseVO courseVO = transUtil.getCourseById(rela.getCourseId(),true);
            courseVOS.add(courseVO);
        }

        return Result.succ(courseVOS);
    }

    //获得自己的已修课程
    @GetMapping("/studiedList")
    public Result studied(){
        String number = getNumByToken();

        //根据学号查找所有已修课程,注意不要是已选
        List<RelaCourseStudentPO> courseStudentPOS = relaCourseStudentServiceMP.selectCourseWithStatus(number,STUDIED);

        if(courseStudentPOS.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("没有已修课程");
        }

        List<CourseVO> courseVOS = new ArrayList<>();

        for (RelaCourseStudentPO rela:
             courseStudentPOS) {
            CourseVO courseVO = transUtil.getCourseById(rela.getCourseId(),true);
            courseVOS.add(courseVO);
        }

        return Result.succ(courseVOS);
    }

    //学生选课
    //第一轮选课：无上限；第二轮选课：达到上限就不能选
    //发送的课程id对应的课程应该是本学期的课程
    @PostMapping("/selection")
    public Result firstRound(@RequestBody Map<String,Integer>map){
        //获得当前选课状态
        String curr = adminServiceMP.getCur();

        String num = getNumByToken();
        int courseId = map.get("courseId");
        CoursepartPO coursepartPO = coursepartServiceMP.getById(courseId);

        //判断是否已经修过/选过同类课程
        //根据课程的名字和编号返回同类课程
        List<CoursepartPO> coursepartPOList =
                coursepartServiceMP.selectCoursepartByNameAndNum(coursepartPO.getCourseName(),coursepartPO.getCourseNum());
        for (CoursepartPO c:
             coursepartPOList) {
            //根据课程的id和学生num查找是否有对应的选课/修读记录，有的话就说明已选同类/已修读，则不能选
            List<RelaCourseStudentPO> relaCourseStudentPOS = relaCourseStudentServiceMP.selectRecordByCourseAndStu(c.getCourseId(),num);
            if(!relaCourseStudentPOS.isEmpty()){
                response.setStatus(WRONG_DATA);
                return Result.fail("选课失败：不能选择已修读的课程或已选过同类课程的课程");
            }
        }

        //根据课程可选专业信息判断能不能选

        if(!coursepartPO.getIsGeneral().equals(GENERAL)){
            //非通选课程，根据课程id查到所有的可选专业id，然后进行判断
            List<RelaCourseMajorPO> courseMajorPOS = relaCourseMajorServiceMP.selectByCourseId(courseId);
            StudentPO studentPO = studentServiceMP.getById(num);
            int stuMajor = studentPO.getMajorId();

            boolean verified = false;
            for (RelaCourseMajorPO relaMajor:
                    courseMajorPOS) {
                if(relaMajor.getMajorId() == stuMajor){
                    verified = true;
                    break;
                }
            }

            //学生不在可选专业内
            if(!verified){
                response.setStatus(WRONG_DATA);
                return Result.fail("选课失败：非可选课程");
            }
        }

        if(curr.equals(ROUND_TWO_OPEN)){
            //判断课程是否已选满
            int capacity = Integer.parseInt(coursepartPO.getCapacity());
            int selected = relaCourseStudentServiceMP.selectStuNumberSelectCourse(courseId,SELECTED);// TODO: 2022/5/4 根据课程id查出已选的relaCourseStudent对象List并返回
            if(capacity <= selected){
                response.setStatus(WRONG_DATA);
                return Result.fail("二轮选课失败：该门课程已被选满");
            }
        }

        //将选课信息增加到relaCourseStudent表里
        RelaCourseStudentPO relaCourseStudentPO = new RelaCourseStudentPO(
                null,
                courseId,
                num,
                "已选"
        );
        relaCourseStudentServiceMP.save(relaCourseStudentPO);

        return Result.succ("选课成功");
    }

    //学生退选
    @DeleteMapping("/selection")
    public Result deleteSelection(@RequestBody Map<String,Integer>map){

        //获得当前选课状态
        String curr = adminServiceMP.getCur();

        if(curr.equals(CLOSE)||curr.equals(ROUND_ONE_END)||curr.equals(ROUND_TWO_END)){
            //不在选课阶段，不能退课
            response.setStatus(WRONG_DATA);
            return Result.fail("退课失败，当前不在选课阶段内");
        }

        String num = getNumByToken();
        int courseId = map.get("courseId");

        // TODO: 2022/5/4 根据课程id和学生num删除对应的选课请求
        relaCourseStudentServiceMP.deleteByCourseIdAndStuNum(courseId,num);

        return Result.succ("退选成功");
    }

    //判断课程是否可选的函数
    public List<Integer> verifyCourses(int stuMajor,List<Integer> fullCourse){
        //找出其中可选的（本学期，且可选专业有该学生的专业）
        List<Integer> requestCourses = new ArrayList<>();

        for (Integer i:
             fullCourse) {
            CoursepartPO coursepartPO = coursepartServiceMP.getById(i);

            //不是本学期的课，剔除
            String year = commonUtil.getSchoolYear();
            String semester = commonUtil.getSemester();
            if(!coursepartPO.getYear().equals(year) || !coursepartPO.getSemester().equals(semester)) continue;

            if(!coursepartPO.getIsGeneral().equals(GENERAL)){
                //非通选课程，根据课程id查到所有的可选专业id，然后进行判断
                List<RelaCourseMajorPO> courseMajorPOS = relaCourseMajorServiceMP.selectByCourseId(i);

                boolean verified = false;
                for (RelaCourseMajorPO relaMajor:
                        courseMajorPOS) {
                    if(relaMajor.getMajorId() == stuMajor){
                        verified = true;
                        break;
                    }
                }

                //学生在可选专业内，把这个课程id加入
                if(verified) requestCourses.add(i);
            }
            else{
                requestCourses.add(i);
            }
        }
        return requestCourses;
    }

    //学生获取可以提交选课申请的课程：只给能选且选课人数已满的课
    //根据token获取学生信息，然后获取可选课程里已满课程，返回
    @GetMapping("/requestCourses")
    public Result requestedCourses(){
        //先找出人数已满的课
        List<CoursepartPO> coursepartPOList = coursepartServiceMP.list();
        List<Integer> fullCourse = new ArrayList<>();
        for (CoursepartPO course:
             coursepartPOList) {
            int capacity = Integer.parseInt(course.getCapacity());
            //找出该门课程的选课人数
            int selected = relaCourseStudentServiceMP.selectStuNumberSelectCourse(course.getCourseId(),SELECTED);
            if (capacity == selected) {
                fullCourse.add(course.getCourseId());
            }
        }

        //再找出其中可选的（本学期，且可选专业有该学生的专业）
        StudentPO studentPO = studentServiceMP.getById(getNumByToken());
        int stuMajor = studentPO.getMajorId();
        List<Integer> requestCourses = verifyCourses(stuMajor,fullCourse);

        if(requestCourses.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("没有可申请课程");
        }
        else{
            //根据选出的课程id转化为课程信息返回
            List<CourseVO> requestCoursesVO = new ArrayList<>();
            for (Integer i:
                 requestCourses) {
                requestCoursesVO.add(transUtil.getCourseById(i,true));
            }
            return Result.succ("获取成功",requestCoursesVO);
        }
    }

    //学生提交选课申请
    @PostMapping("/course/request")
    public Result addRequest(@RequestBody /*ReqStudentVO reqStudentVO*/ Map<String,Object> map){
//        ReqStudentPO reqStudentPO = transUtil.ReqStuVOtoPO(reqStudentVO,getNumByToken());
        Integer courseId = (Integer) map.get("courseId");
        String reason = (String) map.get("reason");
        ReqStudentPO reqStudentPO = new ReqStudentPO(
                null,
                courseId,
                getNumByToken(),
                reason,
                0,
                0
        );
        reqStudentServiceMP.save(reqStudentPO);
        return Result.succ("提交选课申请成功！");
    }

    //学生获得自己的所有选课申请
    @GetMapping("/course/request")
    public Result getRequest(){
        String num = getNumByToken();
        //根据学号获得全部选课申请并返回
        List<ReqStudentPO> reqStudentPOS = reqStudentServiceMP.list(Wrappers.lambdaQuery(ReqStudentPO.class)
                .eq(ReqStudentPO::getStudentNum,num));
        List<ReqStudentVO> reqStudentVOS = new ArrayList<>();
        for (ReqStudentPO r:
             reqStudentPOS) {
            ReqStudentVO reqStudentVO = transUtil.ReqStuPOtoVO(r);
            reqStudentVOS.add(reqStudentVO);
        }

        return Result.succ("获取成功",reqStudentVOS);
    }

    /*
        条件搜索部分
     */

    //获得全部的可选课程信息
    @GetMapping("/course/verified")
    public Result verifiedCourses(){
        List<CoursepartPO> coursepartPOList = coursepartServiceMP.list();
        List<Integer> fullCourse = new ArrayList<>();
        for (CoursepartPO course:
                coursepartPOList) {
            fullCourse.add(course.getCourseId());
        }

        //再找出其中可选的（本学期，且可选专业有该学生的专业）
        StudentPO studentPO = studentServiceMP.getById(getNumByToken());
        int stuMajor = studentPO.getMajorId();
        List<Integer> requestCourses = verifyCourses(stuMajor,fullCourse);

        if(requestCourses.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("没有可选课程");
        }
        else{
            //根据选出的课程id转化为课程信息返回
            List<CourseVO> requestCoursesVO = new ArrayList<>();
            for (Integer i:
                    requestCourses) {
                requestCoursesVO.add(transUtil.getCourseById(i,true));
            }
            return Result.succ("获取成功",requestCoursesVO);
        }
    }


    //根据课程代码、课程名称、教师模糊搜索可选课程
    @PostMapping("/course/verified/search")
    public Result blurSearch(@RequestBody Map<String,String>map){
        //先搜出来，再判断可选，最后返回
        String word = map.get("search");
        //根据word对课程代码、课程名称、教师字段模糊查询，返回coursepartPO的List
        List<CoursepartPO> temp = coursepartServiceImpMP.conditionSearch(word);

        List<Integer> fullCourse = new ArrayList<>();
        for (CoursepartPO course:
                temp) {
            fullCourse.add(course.getCourseId());
        }

        //再找出其中可选的（本学期，且可选专业有该学生的专业）
        StudentPO studentPO = studentServiceMP.getById(getNumByToken());
        int stuMajor = studentPO.getMajorId();
        List<Integer> requestCourses = verifyCourses(stuMajor,fullCourse);

        if(requestCourses.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("没有符合条件的可选课程");
        }
        else{
            //根据选出的课程id转化为课程信息返回
            List<CourseVO> requestCoursesVO = new ArrayList<>();
            for (Integer i:
                    requestCourses) {
                requestCoursesVO.add(transUtil.getCourseById(i,true));
            }
            return Result.succ("获取成功",requestCoursesVO);
        }
    }
}
