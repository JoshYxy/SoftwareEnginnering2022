package com.jwsystem.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@Accessors(chain = true)
@TableName("req_coursepart")
public class ReqCoursepartPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "request_id", type = IdType.AUTO)
    private Integer requestId;

    /**
     * 编号
     */
    private String courseNum;

    /**
     * 名称
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
     * 课程介绍
     */
    private String courseInfo;

    /**
     * 教师容量
     */
    private String capacity;

    /**
     * 是否为通识
     */
    private String isGeneral;

    /**
     * 学年
     */
    private String year;

    /**
     * 学期
     */
    private String semester;

    /**
     * 学院
     */
    private Integer collegeId;

    /**
     * 老师
     */
    private String teacherNum;


}
