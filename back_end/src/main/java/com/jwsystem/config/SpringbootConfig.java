package com.jwsystem.config;

import com.jwsystem.interceptor.adminInterceptor;
import com.jwsystem.interceptor.loginInterceptor;
import com.jwsystem.interceptor.studentInterceptor;
import com.jwsystem.interceptor.teacherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringbootConfig implements WebMvcConfigurer {

    //拦截器配置

    //注入自定义的拦截器
    @Bean
    public loginInterceptor getLoginInterceptor(){
        return new loginInterceptor();
    }

    @Bean
    public adminInterceptor getAdminInterceptor(){
        return new adminInterceptor();
    }

    @Bean
    public studentInterceptor getStuInterceptor() { return new studentInterceptor();}

    @Bean
    public teacherInterceptor getTeaInterceptor() { return new teacherInterceptor();}

    //将自己的拦截器注册到拦截器链中，并说明拦截路径规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())//注册登陆拦截器
                .addPathPatterns("/**")//需要拦截的路径（*表示全部路径）
                .excludePathPatterns("/user");//需要放行的路径
        registry.addInterceptor(getAdminInterceptor())
                .addPathPatterns("/affair/*","/affair","/admin/*","/admin","/course/*","/course");
//                .excludePathPatterns("/**");//需要放行的路径
        registry.addInterceptor(getStuInterceptor())
                .addPathPatterns("/student/*","/student");
//                .excludePathPatterns("/**");//需要放行的路径
        registry.addInterceptor(getTeaInterceptor())
                .addPathPatterns("/teacher/*","/teacher");
//                .excludePathPatterns("/**");//需要放行的路径
    }


    //跨域配置

    static final String[] ORIGINS = new String[] { "GET", "POST", "PUT", "DELETE" };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有的当前站点的请求地址，都支持跨域访问。
                .allowedOriginPatterns("*") // 所有的外部域都可跨域访问。 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
                .allowCredentials(true) // 是否支持跨域用户凭证
                .allowedMethods(ORIGINS) // 当前站点支持的跨域请求类型是什么
                .maxAge(3600); // 超时时长设置为1小时。 时间单位是秒。
    }

}