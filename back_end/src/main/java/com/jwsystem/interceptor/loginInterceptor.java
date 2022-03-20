package com.jwsystem.interceptor;

import com.jwsystem.common.Result;
import com.jwsystem.util.JwtUtils;
import com.mysql.jdbc.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwsystem.controller.UserController.EXPIRED;

//登陆状态拦截器
public class loginInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;

    //在执行被拦截器拦截的路径前执行，返回true则放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isNullOrEmpty(token)) {
            System.out.println("get token from parameter");
            token = request.getParameter("token");
        }

        if(StringUtils.isNullOrEmpty(token)){
            System.out.println("请先登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Claims claims = jwtUtils.getCliamByToken(token);
        if(claims == null){
            System.out.println("token验证失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if(jwtUtils.isTokenExpired(claims)){
            //token过期
            response.setStatus(EXPIRED);
            return false;
        }

        System.out.println("已登录");
        return true;
    }

    //放行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("post");
    }

    //拦截器生命周期结束前执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("after");
    }
}