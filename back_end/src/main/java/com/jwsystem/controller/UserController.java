package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.User;
import com.jwsystem.service.impl.AdminServiceImp;
import com.jwsystem.service.impl.StuServiceImp;
import com.jwsystem.service.impl.TeaServiceImp;
import com.jwsystem.util.JwtUtils;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jwsystem.util.CSVUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwsystem.entity.User.*;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CSVUtils csvUtils;

    @Autowired
    StuServiceImp stuServiceImp;

    @Autowired
    private AdminServiceImp adminServiceImp;

    @Autowired
    TeaServiceImp teaServiceImp;

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
    //前端一定要返回完整的User对象，不然就寄了
    @PostMapping("/info")
    public Result changeInfo(@RequestBody User tempUser){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        if(tempUser.getRole().equals("student")){
            //学生
            boolean res = stuServiceImp.updateStuInfo(tempUser);
            if(res == false){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else if(tempUser.getRole().equals("teacher")){
            //老师
            boolean res = teaServiceImp.updateTeaInfo(tempUser);
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


}
