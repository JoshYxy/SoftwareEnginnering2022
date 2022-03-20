package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dao.UserDao;
import com.jwsystem.entity.User;
import com.jwsystem.service.impl.UserServiceImpl;
import com.jwsystem.util.JwtUtils;
import com.mysql.jdbc.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserServiceImpl userService;

    @Resource
    UserDao userDao;

    public static int NO_USER = 418;  //用户不存在
    public static int WRONG_PSWD = 419;   //密码错误
    public static int CONFLICT_ID = 420;  //身份证号冲突
    public static int CONFLICT_NUMBER = 421;  //学工号冲突
    public static int EXPIRED = 422;  //token过期

    /*
        API也要一一对应！分别是   /login  /register   /reset  /verify
        请求里对应的参数键值对的键也要对应！不能有任何差别！
        User的属性名要对应！
     */

    //登陆请求
    @PostMapping("/user")
    public Result login (@RequestBody User tempUser){

        Long number = tempUser.getNumber();
        String password = tempUser.getPassword();

        //用Dao，以number为键去数据库查询有没有对应的用户
        User user = userDao.selectByNumber(number);
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
        //放body里？
        response.addHeader("Access-Control-Expose-Headers","role");
        response.setHeader("role",role);

        return Result.succ("登陆成功",null);
    }

    //注册请求
    @PostMapping("/user/new")
    public Result register(@RequestBody User tempUser){

        //判断登陆是否有效，以及是否是管理员
        String token = request.getHeader("token");

        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Result.fail("未登陆！");
        }

        Claims claims = jwtUtils.getCliamByToken(token);

        if(claims == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Result.fail("token验证失败");
        }

        if(jwtUtils.isTokenExpired(claims)){
            response.setStatus(EXPIRED);
            return Result.fail("token过期，请重新登陆");
        }

        //通过claim取出jwt主体（用户的number）
        String requestNumber = claims.getSubject();
        User requestUser = userDao.selectByNumber(Long.parseLong(requestNumber));

        //判断是不是管理员
        if(!requestUser.getRole().equals("admin")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Result.fail("权限不足！");
        }

        if(userDao.selectById(tempUser.getId()) != null){
            response.setStatus(CONFLICT_ID);
            return Result.fail("该身份证号已注册！");
        }

        if(userDao.selectByNumber(tempUser.getNumber()) != null){
            response.setStatus(CONFLICT_NUMBER);
            return Result.fail("该学工号已注册！");
        }

        userDao.insertUser(tempUser);
        return Result.succ("注册成功！ ",tempUser);
    }

    @PostMapping("/user/password")
    public Result reset(@RequestBody User tempUser) {
        //用token判断是哪个用户，并修改密码
        String token = request.getHeader("token");

        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Result.fail("未登陆！");
        }

        Claims claims = jwtUtils.getCliamByToken(token);

        if(claims == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Result.fail("token验证失败");
        }

        if(jwtUtils.isTokenExpired(claims)){
            //token过期
            response.setStatus(EXPIRED);
            return Result.fail("token过期，请重新登陆");
        }

        //通过claim取出jwt主体（用户的number）
        Long number = Long.parseLong(claims.getSubject());
        userService.updateUserPwdByNumber(number,tempUser.getPassword());
        return Result.succ("密码修改成功");
    }
}


//全局异常处理
