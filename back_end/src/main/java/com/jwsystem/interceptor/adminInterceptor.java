package com.jwsystem.interceptor;

import com.jwsystem.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class adminInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;

    static String adminNum = "1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("adminInterceptor");
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getCliamByToken(token);
        String number = claims.getSubject();
        if(!number.equals(adminNum)){
            System.out.println("非管理员！");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        System.out.println("已验证管理员身份");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
