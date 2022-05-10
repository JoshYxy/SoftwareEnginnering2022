package com.jwsystem;

import com.jwsystem.controller.CourseController;
import org.junit.Assert;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class AdminTest {

    @SpyBean
    CourseController courseController;

    //管理员获得某门课程已选/已修名单
    @Test
    public void putSelectedList(){
        Integer courseId = 9;        //课程编号
        String result = "请求成功";
        Map<String,Integer> map = new HashMap<>();
        map.put("courseId",courseId);
        Assert.assertEquals("管理员获得某门课程已选/已修名单测试失败",result,courseController.selected(map).getMsg());
    }

    //管理员获得所有未审核的选课申请
    @Test
    public void getStuRequests(){
        String result = "获取成功";
        Assert.assertEquals("管理员获得所有未审核的选课申请测试失败",result,courseController.getStuRequests().getMsg());
    }

    //管理员审核学生选课申请:审核成功：已拒绝该选课申请
    @Test
    public void refuseStuReqs(){
        Object requestId = 8;
        Object res = false;
        Map<String,Object> map = new HashMap<>();
        map.put("requestId",requestId);
        map.put("approved",res);
        String result = "审核成功：已拒绝该选课申请";
        Assert.assertEquals("管理员拒绝学生选课申请测试失败",result,courseController.dealStuReqs(map).getMsg());
    }

    //管理员审核学生选课申请:审核成功：成功增加选课信息
    @Test
    public void acceptStuReqs(){
        Object requestId = 8;
        Object res = true;
        Map<String,Object> map = new HashMap<>();
        map.put("requestId",requestId);
        map.put("approved",res);
        String result = "审核成功：成功增加选课信息";
        Assert.assertEquals("管理员通过学生选课申请测试失败",result,courseController.dealStuReqs(map).getMsg());
    }

    //管理员审核学生选课申请: 申请通过失败：学生不能选择已修读的课程或已选过同类课程的课程
    @Test
    public void postStuReqsWithWrongCourse(){
        Object requestId = 8;
        Object res = true;
        Map<String,Object> map = new HashMap<>();
        map.put("requestId",requestId);
        map.put("approved",res);
        String result = "申请通过失败：学生不能选择已修读的课程或已选过同类课程的课程";
        Assert.assertEquals("学生申请已修/已读课程测试失败",result,courseController.dealStuReqs(map).getMsg());
    }

    //管理员审核学生选课申请:申请通过失败：教室已满，不能再加人！
    @Test
    public void postStuReqsWithWrongRoom(){
        Object requestId = 8;
        Object res = true;
        Map<String,Object> map = new HashMap<>();
        map.put("requestId",requestId);
        map.put("approved",res);
        String result = "申请通过失败：教室已满，不能再加人！";
        Assert.assertEquals("学生申请满员教室课程测试失败",result,courseController.dealStuReqs(map).getMsg());
    }
}
