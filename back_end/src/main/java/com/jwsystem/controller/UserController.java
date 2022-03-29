package com.jwsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.jwsystem.common.Result;
import com.jwsystem.dao.UserDao;
import com.jwsystem.entity.College;
import com.jwsystem.entity.User;
import com.jwsystem.service.impl.UserServiceImpl;
import com.jwsystem.util.JwtUtils;
import com.mysql.jdbc.StringUtils;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.*;
import com.jwsystem.util.CSVUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

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
    UserServiceImpl userService;

    @Resource
    UserDao userDao;

    public static int TEACHER_NUM_LENGTH = 8;

    //登陆请求
    @PostMapping("")
    public Result login (@RequestBody User tempUser){

        String number = tempUser.getNumber();
        String password = tempUser.getPassword();

        //用Dao，以number为键去数据库查询有没有对应的用户
        User user;
        if(number.length()<TEACHER_NUM_LENGTH){
            //学生
            user = stuDao.getByNumber(number);
        } else {
            //老师
            user = teacherDao.getByNumber(number);
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
        userService.updateUserPwdByNumber(number,tempUser.getPassword());
        return Result.succ("密码修改成功");
    }

    //用户查看个人信息
    @GetMapping("/info")
    public Result getInfo(){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        User user;
        if(number.length()<TEACHER_NUM_LENGTH){
            //学生
                 user = stuDao.getByNumber(number);
        } else {
            //老师
                user = teacherDao.getByNumber(number);
        }

        if(user == null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在！");
        }
        return Result.succ(user);
    }


    //用户修改个人信息
    @PostMapping("/info")
    public Result changeInfo(@RequestBody User tempUser){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        if(tempUser.getRole().equals("student")){
            //学生
                stuService.updateInfoByNumber(number);
        } else {
            //老师
                teacherService.updateInfoByNumber(number);
        }
        return Result.succ(null);
    }


}
