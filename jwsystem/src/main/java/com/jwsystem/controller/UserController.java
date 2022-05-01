package com.jwsystem.controller;

import com.jwsystem.common.Result;

import com.jwsystem.entity.affair.TimesPO;
import com.jwsystem.vo.UserVO;
import com.jwsystem.service.impl.*;
import com.jwsystem.util.JwtUtils;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.TeacherDataVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.jwsystem.vo.UserVO.STUDYING;
import static com.jwsystem.vo.UserVO.WORKING;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
//    StuServiceImp stuServiceImp;
    private StudentServiceImpMP studentServiceImpMP;

    @Autowired
//    private AdminServiceImp adminServiceImp;
    private AdminServiceImpMP adminServiceImpMP;

    @Autowired
//    private TeaServiceImp teaServiceImp;
    private TeacherServiceImpMP teacherServiceImpMP;

    @Autowired
//    private BuildingServiceImp buildingServiceImp;
    private BuildingServiceImpMP buildingServiceImpMP;

    @Autowired
//    private TimesServiceImp timesServiceImp;
    private TimesServiceImpMP timesServiceImpMP;

    public static int TEACHER_NUM_LENGTH = 8;
    public static int STUDENT_NUM_LENGTH = 6;
    public static int ID_LENGTH = 18;

    //登陆请求
    @PostMapping("")
    public Result login (@RequestBody UserVO tempUserVO){

        String number = tempUserVO.getNumber();
        String password = tempUserVO.getPassword();

        //用Dao，以number为键去数据库查询有没有对应的用户
        UserVO userVO;

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            userVO = teacherServiceImpMP.selectUserByNumber(number);
            if( !userVO.getStatus().equals(WORKING) ) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已离职教师无权登陆");
            }
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            userVO = studentServiceImpMP.selectUserByNumber(number);
            if( !userVO.getStatus().equals(STUDYING)) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已毕业学生无权登陆");
            }
        }
        else{
            userVO = adminServiceImpMP.selectAdminUserByNumber(number);
        }

        if(userVO ==null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在");
        }

        if(!userVO.getPassword().equals(password)){
            response.setStatus(WRONG_PSWD);
            return Result.fail("密码错误");
        }

        String token = jwtUtils.generateToken(number.toString()) ;
        if (!StringUtils.isNullOrEmpty(token)) {
            response.addHeader("Access-Control-Expose-Headers","token");
            response.setHeader(jwtUtils.getHeader(),token);
        }

        String role = userVO.getRole();
        response.addHeader("Access-Control-Expose-Headers","role");
        response.setHeader("role",role);

        return Result.succ("登陆成功",null);
    }

    //第一次登陆修改密码
    @PostMapping("/password")
    public Result reset(@RequestBody UserVO tempUserVO) {
        String number = getNumByToken();

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            teacherServiceImpMP.updatePwdByNumber(tempUserVO.getPassword(),number);
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            studentServiceImpMP.updatePwdByNumber(tempUserVO.getPassword(),number);
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户学工号长度非法");
        }

        return Result.succ("密码修改成功");
    }

    //用户查看个人信息
    @GetMapping("/info")
    public Result getInfo(){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        UserVO userVO;
        if(number.length() == STUDENT_NUM_LENGTH){
            //学生
                 userVO = studentServiceImpMP.selectUserByNumber(number);
        } else {
            //老师
                userVO = teacherServiceImpMP.selectUserByNumber(number);
        }
        if(userVO == null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在！");
        }
        return Result.succ(userVO);
    }


    //用户修改个人信息
    //前端一定要返回完整的User对象
    //判断密码重复
    @PostMapping("/info")
    public Result changeInfo(@RequestBody UserVO tempUserVO){
        //判断登陆用户是老师还是学生，再到对应的表中去查
        if(tempUserVO.getRole().equals("student")){
            //学生
            boolean res = (studentServiceImpMP.updateStuInfoByUser(tempUserVO) != 0);
            if(!res){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else if(tempUserVO.getRole().equals("teacher")){
            //老师
            boolean res = (teacherServiceImpMP.updateTeaInfoByUser(tempUserVO) != 0);
            if (!res){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else {
            response.setStatus(WRONG_DATA);
            return Result.fail("无效的用户role", tempUserVO);
        }
        return Result.succ("用户信息修改成功！");
    }

    //获得选课权限
    @GetMapping("/curriculaVariable")
    public Result getCurrVariable(){
        String curr = adminServiceImpMP.getCur();
        return Result.succ("获取选课状态成功",curr);
    }

    //新增课程获得必要信息
    @GetMapping("/course/new")
    public Result getCourseInfo(){
        //返回教师信息：按照学院分类，将每个学院的老师都取出来，以teacherData的List返回
        List<TeacherDataVO> teacherDataVOList = teacherServiceImpMP.getAllTeachersWithCollege();

        //返回教室信息：按照楼分类，将每个楼里的教室都取出来，以classroom list的形式返回
        List<BuildingVO> buildingVOList = buildingServiceImpMP.selectAllBuildingAndRoomByList();

        //上课时间信息
        List<TimesPO> times = timesServiceImpMP.list();

        return Result.succ3(teacherDataVOList,buildingVOList,times);
    }

}
