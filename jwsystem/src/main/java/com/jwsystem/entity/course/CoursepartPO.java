package com.jwsystem.entity.course;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@TableName("coursepart")
public class CoursepartPO implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String GENERAL = "通选课程";
    public static String ONE_MAJOR = "专业课程";
    public static String PART_OF_MAJORS = "面向部分专业课程";

    /**
     * 课程id
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程编号
     */
    private String courseNum;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 学时
     */
    private String classHours;
    /**
     * 学分
     */
    private String credits;
    /**
     * 课程简介
     */
    private String courseInfo;
    /**
     * 课程容量
     */
    private String capacity;
    /**
     * 学年
     */
    private String year;
    /**
     * 学期
     */
    private String semester;
    /**
     * 是否为通识课程
     */
    private String isGeneral;
    /**
     * 学院
     */
    private Integer collegeId;
    /**
     * 老师
     */
    private String teacherNum;


}
