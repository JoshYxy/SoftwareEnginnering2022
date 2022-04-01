package com.jwsystem.controller;

import com.jwsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    public static int NO_USER = 418;  //用户不存在
    public static int WRONG_PSWD = 419;   //密码错误
    public static int CONFLICT_ID = 420;  //身份证号冲突
    public static int CONFLICT_NUMBER = 421;  //学工号冲突
    public static int EXPIRED = 422;  //token过期
    public static int NO_COLLEGE = 423; //学院不存在
    public static int NO_MAJOR = 424;   //专业不存在
//    public static String FILE_PATH = "/Users/andyluo/Desktop/";  //上传文件保存路径
    public static int WRONG_FILE = 425; //上传文件格式错误
    public static int WRONG_DATA = 426; //上传数据格式错误
    public static int NO_COURSE = 427;  //课程不存在

    protected String getNumByToken(){
        String token = request.getHeader("token");
        String number = jwtUtils.getCliamByToken(token).getSubject();
        return number;
    }


}
