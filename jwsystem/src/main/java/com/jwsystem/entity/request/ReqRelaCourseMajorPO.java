package com.jwsystem.entity.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("req_rela_course_major")
public class ReqRelaCourseMajorPO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer requestId;

    private Integer majorId;
}
