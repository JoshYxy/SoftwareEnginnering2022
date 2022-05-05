package com.jwsystem.entity.course;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rela_course_student")
public class RelaCourseStudentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String SELECTED = "已选";
    public static String STUDIED = "已修";

    private Integer id;

    private Integer courseId;

    private String studentNum;

    /**
     * 选课状态
     */
    private String status;


}
