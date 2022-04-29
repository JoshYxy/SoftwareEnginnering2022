package com.jwsystem.entity;

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
@TableName("rela_course_student")
public class RelaCourseStudentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer courseId;

    private String studentNum;

    /**
     * 选课状态
     */
    private String status;


}
