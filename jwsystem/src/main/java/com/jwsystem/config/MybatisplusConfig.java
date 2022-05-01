package com.jwsystem.config;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;
public class MybatisplusConfig {
    /**
     * 项目路径
     */
    private static final String PARENT_DIR = System.getProperty("user.dir");
    /**
     * 基本路径
     */
    private static final String SRC_MAIN_JAVA = "/src/main/";
    /**
     * xml路径
     */
    private static final String XML_PATH = PARENT_DIR + "/resources/mapper";
    /**
     * entity路径
     */
    private static final String ENTITY_PATH = PARENT_DIR + "/java/com/jwsystem/entity";
    /**
     * dao路径
     */
    private static final String MAPPER_PATH = PARENT_DIR + "/java/com/jwsystem/dao";
    /**
     * service路径
     */
    private static final String SERVICE_PATH = PARENT_DIR + "/java/com/jwsystem/service";
    /**
     * serviceImpl路径
     */
    private static final String SERVICE_IMPL_PATH = PARENT_DIR + "/java/com/jwsystem/service/impl";
    /**
     * controller路径
     */
    private static final String CONTROLLER_PATH = PARENT_DIR + "/java/com/jwsystem/service/controller";

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();// 配置要生成的表名
        tables.add("admin");
        tables.add("building");
        tables.add("classroom");
        tables.add("college");
        tables.add("coursepart");
        tables.add("major");
        tables.add("rela_course_major");
        tables.add("rela_course_student");
        tables.add("req_coursepart");
        tables.add("req_timepart");
        tables.add("req_student");
        tables.add("req_teacher");
        tables.add("student");
        tables.add("teacher");
         tables.add("timepart");
         tables.add("times");

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/lab4?useSSL=false",
                        "root",
                        "123456")


                // 全局配置
                .globalConfig(builder -> builder
                        .outputDir(System.getProperty("user.dir")+"\\src\\main\\java")
                )
//                // 包配置
                .packageConfig(builder -> builder
                        .parent("com.jwsystem")    // /src/main/java/***
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .mapper("dao")
                        //.xml("mapper")
                        .pathInfo(Collections.singletonMap(OutputFile.xml,System.getProperty("user.dir")+"\\src\\main\\resources\\mapper"))
                )

                // 策略配置
                .strategyConfig(builder -> builder
                        // entity
                        .addInclude(tables)
                        .entityBuilder()
                        .fileOverride()
                        .enableChainModel()
                        .formatFileName("%sPO")
                        .fileOverride()
                        .enableLombok()
                        //.enableRemoveIsPrefix()
                        //.logicDeleteColumnName("is_delete")
                        //.idType(IdType.ASSIGN_ID)
                        //.addTableFills(new Column("create_time", FieldFill.INSERT))
                        //.addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        // service
                        .serviceBuilder()
                        //.fileOverride()
                        .superServiceClass(IService.class)
                        .formatServiceFileName("%sServiceMP")
                        .formatServiceImplFileName("%sServiceImpMP")
                        // mapper
                        .mapperBuilder()
                        .fileOverride()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sDaoMP")
                        .formatXmlFileName("%sMapperMP")
                        .enableMapperAnnotation()
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取路径信息map
     *
     * @return {@link Map < OutputFile, String> }
     */
    private static Map<OutputFile, String> getPathInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.entity, ENTITY_PATH);
        pathInfo.put(OutputFile.mapper, MAPPER_PATH);
        pathInfo.put(OutputFile.service, SERVICE_PATH);
        pathInfo.put(OutputFile.serviceImpl, SERVICE_IMPL_PATH);
        pathInfo.put(OutputFile.controller, CONTROLLER_PATH);
        pathInfo.put(OutputFile.xml, XML_PATH);
        return pathInfo;
    }
}