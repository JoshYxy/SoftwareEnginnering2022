package com.jwsystem.entity.course;

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
@TableName("rela_course_major")
public class RelaCourseMajorPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer courseId;

    private Integer majorId;


}
