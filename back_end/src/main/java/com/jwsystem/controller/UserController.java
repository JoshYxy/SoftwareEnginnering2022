package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.User;
import com.jwsystem.entity.Times;
import com.jwsystem.service.impl.*;
import com.jwsystem.util.JwtUtils;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.TeacherData;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.jwsystem.dto.User.STUDYING;
import static com.jwsystem.dto.User.WORKING;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    StuServiceImp stuServiceImp;

    @Autowired
    private AdminServiceImp adminServiceImp;

    @Autowired
    private TeaServiceImp teaServiceImp;

    @Autowired
    private BuildingServiceImp buildingServiceImp;

    @Autowired
    private TimesServiceImp timesServiceImp;

    public static int TEACHER_NUM_LENGTH = 8;
    public static int STUDENT_NUM_LENGTH = 6;

    //登陆请求
    @PostMapping("")
    public Result login (@RequestBody User tempUser){

        String number = tempUser.getNumber();
        String password = tempUser.getPassword();

        //用Dao，以number为键去数据库查询有没有对应的用户
        User user;

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            user = teaServiceImp.getUserByNumber(number);
            if( !user.getStatus().equals(WORKING) ) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已离职教师无权登陆");
            }
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            user = stuServiceImp.getUserByNumber(number);
            if( !user.getStatus().equals(STUDYING)) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已毕业学生无权登陆");
            }
        }
        else{
            user = adminServiceImp.getUserByNumber(number);
        }

        if(user==null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在");
        }

        if(!user.getPassword().equals(password)){
            response.setStatus(WRONG_PSWD);
            return Result.fail("密码错误");
        }

        String token = jwtUtils.generateToken(number.toString()) ;
        if (!StringUtils.isNullOrEmpty(token)) {
            response.addHeader("Access-Control-Expose-Headers","token");
            response.setHeader(jwtUtils.getHeader(),token);
        }

        String role = user.getRole();
        response.addHeader("Access-Control-Expose-Headers","role");
        response.setHeader("role",role);

        return Result.succ("登陆成功",null);
    }

    //第一次登陆修改密码
    @PostMapping("/password")
    public Result reset(@RequestBody User tempUser) {
        String number = getNumByToken();

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            teaServiceImp.updatePwdByNumber(tempUser.getPassword(),number);
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            stuServiceImp.updatePwdByNumber(tempUser.getPassword(),number);
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
        User user;
        if(number.length() == STUDENT_NUM_LENGTH){
            //学生
                 user = stuServiceImp.getUserByNumber(number);
        } else {
            //老师
                user = teaServiceImp.getUserByNumber(number);
        }
        if(user == null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在！");
        }
        return Result.succ(user);
    }


    //用户修改个人信息
    //前端一定要返回完整的User对象
    //判断密码重复
    @PostMapping("/info")
    public Result changeInfo(@RequestBody User tempUser){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        if(tempUser.getRole().equals("student")){
            //学生
            boolean res = stuServiceImp.updateStuInfoByUser(tempUser);
            if(res == false){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else if(tempUser.getRole().equals("teacher")){
            //老师
            boolean res = teaServiceImp.updateTeaInfoByUser(tempUser);
            if (res == false){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else {
            response.setStatus(WRONG_DATA);
            return Result.fail("无效的用户role",tempUser);
        }
        return Result.succ("用户信息修改成功！");
    }

    //获得选课权限
    @GetMapping("/curriculaVariable")
    public Result getCurrVariable(){
        boolean curr = adminServiceImp.getCurr();
        return Result.succ("获取选课状态成功",curr);
    }

    //新增课程获得必要信息
    @GetMapping("/course/new")
    public Result getCourseInfo(){
        //返回教师信息：按照学院分类，将每个学院的老师都取出来，以teacherData的List返回
        List<TeacherData> teacherDataList = teaServiceImp.getAllTeachersWithCollege();

        //返回教室信息：按照楼分类，将每个楼里的教室都取出来，以classroom list的形式返回
        List<BuildingVO> buildingVOList = buildingServiceImp.getAllRooms();

        //上课时间信息
        List<Times> times = timesServiceImp.getAllTimes();

        return Result.succ3(teacherDataList,buildingVOList,times);
    }

}
