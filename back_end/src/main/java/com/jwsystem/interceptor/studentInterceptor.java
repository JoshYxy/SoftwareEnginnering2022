package com.jwsystem.interceptor;

import com.jwsystem.entity.Student;
import com.jwsystem.service.StuService;
import com.jwsystem.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class studentInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    StuService stuService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("studentInterceptor");
        if("OPTIONS".equals(httpServletRequest.getMethod().toUpperCase())) {
            System.out.println("Method:OPTIONS");
            return true;
        }

        String token = request.getHeader("token");
        Claims claims = jwtUtils.getCliamByToken(token);
        String number = claims.getSubject();
        Student student = stuService.selectStuByNum(number);

        if(student==null){
            System.out.println("非学生账号!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        System.out.println("已验证学生身份");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
