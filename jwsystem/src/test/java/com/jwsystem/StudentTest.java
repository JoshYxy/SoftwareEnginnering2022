
package com.jwsystem;


import com.jwsystem.controller.StudentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class StudentTest {

    @SpyBean
    private StudentController studentController;

    //选课测试相关函数
    public String selectTest(String studentNum,Integer courseId){
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Map<String,Integer> map = new HashMap<>();
        map.put("courseId",courseId);
        return studentController.firstRound(map).getMsg();
    }

    //学生在选课时间段获取本学期全部课程
    @Test
    public void getAllCourse(){
        String result = "获取可选课程成功";
        String studentNum = "210003";   //学生学号
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("获取本学期全部课程测试失败",result,studentController.avaliableCourse().getMsg());
    }

    //学生获得自己的已选课程
    @Test
    public void getSelectedCourse(){
        String result = "请求成功";
        String studentNum = "210003";   //学生学号
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("获取已选课程测试失败",result,studentController.selected().getMsg());
    }

    //学生获得自己的已修课程：请求成功
    @Test
    public void getCompletedCourse(){
        String result = "请求成功";
        String studentNum = "210004";   //学生学号
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("获取已修课程测试失败",result,studentController.studied().getMsg());
    }

    //学生获得自己的已修课程：没有已修课程
    @Test
    public void noCompletedCourse(){
        String result = "没有已修课程";
        String studentNum = "210003";   //学生学号
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("没有已修课程测试失败",result,studentController.studied().getMsg());
    }

    //选课成功
    @Test
    public void selection(){
        String studentNum = "210003";   //学生学号
        Integer courseId = 4;        //课程编号
        String result = "选课成功";
        Assert.assertEquals("正确选课测试失败",result,selectTest(studentNum,courseId));
    }

    //选课失败：不能选择已修读的课程或已选过同类课程的课程
    @Test
    public void sameCourseSele(){
        String studentNum = "210004";   //学生学号
        Integer courseId = 4;        //课程编号
        String result = "选课失败：不能选择已修读的课程或已选过同类课程的课程";
        Assert.assertEquals("同类选课测试失败",result,selectTest(studentNum,courseId));
    }

    //选课失败：非可选课程
    @Test
    public void invalidCourse(){
        String studentNum = "200010";   //学生学号
        Integer courseId = 16;        //课程编号
        String result = "选课失败：非可选课程";
        Assert.assertEquals("无权限选课测试失败",result,selectTest(studentNum,courseId));
    }

    //学生退选
    @Test
    public void deleteSelection(){
        String studentNum = "210003";   //学生学号
        Integer courseId = 4;        //课程编号
        String result = "退选成功";
        Map<String,Integer> map = new HashMap<>();
        map.put("courseId",courseId);
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("退选测试失败",result,studentController.deleteSelection(map).getMsg());
    }

    //学生获取可以提交选课申请的课程:没有可申请课程
    @Test
    public void getReqCourseNull(){
        String studentNum = "220001";   //学生学号
        String result = "没有可申请课程";
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("无可申请课程测试失败",result,studentController.requestedCourses().getMsg());
    }

    //学生获取可以提交选课申请的课程:获取成功
    @Test
    public void getReqCourse(){
        String studentNum = "210003";   //学生学号
        String result = "获取成功";
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("可申请课程测试失败",result,studentController.requestedCourses().getMsg());
    }

    //学生提交选课申请
    @Test
    public void postCourseReq(){
        Map<String,Object> map = new HashMap<>();
        String studentNum = "210003";   //学生学号
        Integer courseId = 1;        //课程编号
        String reason = "理由";
        String result = "提交选课申请成功！";
        map.put("courseId",courseId);
        map.put("reason",reason);
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("提交选课申请测试失败",result,studentController.addRequest(map).getMsg());
    }

    //学生获得自己的所有选课申请
    @Test
    public void getReq(){
        String studentNum = "210003";   //学生学号
        String result = "获取成功";
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("获取选课申请测试失败",result,studentController.getRequest().getMsg());
    }

    //学生获得全部的可选课程信息
    @Test
    public void getValidCourse(){
        String result = "获取成功";
        String studentNum = "210003";   //学生学号
        Mockito.when(studentController.getNumByToken()).thenReturn(studentNum);
        Assert.assertEquals("获取可选课程测试失败",result,studentController.verifiedCourses().getMsg());
    }

}
