package com.jwsystem;

import com.jwsystem.dto.MajorDTO;
import com.jwsystem.service.impl.AdminServiceImp;
import com.jwsystem.service.impl.EduServiceImp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class test {

    @Autowired
    AdminServiceImp adminServiceImp;

    @Autowired
    EduServiceImp eduServiceImp;

    @Before
    public void before(){
        System.out.println("开始测试");
    }

    @After
    public void after(){
        System.out.println("结束测试");
    }

    @Test
    public void test1(){
        Assert.assertTrue(adminServiceImp.getCurr());
    }

    @Test
    public void test2(){
        MajorDTO mockMajor1 = new MajorDTO(
                null,"233","666"
        );

        MajorDTO mock2 = new MajorDTO(
                null,"经济学","经济学院"
        );

        MajorDTO result = new MajorDTO(
                233,"经济学","经济学院"
        );

        Assert.assertNull(eduServiceImp.selectMajorByName(mockMajor1));
        System.out.println(eduServiceImp.selectMajorByName(mockMajor1));
        Assert.assertEquals("有问题",result,eduServiceImp.selectMajorByName(mock2));

    }

}
