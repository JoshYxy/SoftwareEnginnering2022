package com.jwsystem.entity.request;

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
@TableName("req_teacher")
public class ReqTeacherPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "request_id", type = IdType.AUTO)
    private Integer requestId;

    private String type;

    private Integer courseId;

    private String teacherNum;

    private Boolean examined;

    private Boolean passed;

    private Integer roomId;


}
