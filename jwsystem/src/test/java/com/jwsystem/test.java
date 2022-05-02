package com.jwsystem;

import com.jwsystem.dto.MajorDTO;
import com.jwsystem.service.impl.AdminServiceImpMP;
import com.jwsystem.service.impl.MajorServiceImpMP;
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
    AdminServiceImpMP adminServiceImpMP;

    @Autowired
    MajorServiceImpMP majorServiceImpMP;

    @Before
    public void before(){
        System.out.println("开始测试");
    }

    @After
    public void after(){
        System.out.println("结束测试");
    }

//    @Test
//    public void test1(){
//        Assert.assertTrue(adminServiceImpMP.getCur());
//    }

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

        Assert.assertNull(majorServiceImpMP.selectMajorByName(mockMajor1.getName()));
        System.out.println(majorServiceImpMP.selectMajorByName(mockMajor1.getName()));
        Assert.assertEquals("有问题",result,majorServiceImpMP.selectMajorByName(mock2.getName()));

    }

}
