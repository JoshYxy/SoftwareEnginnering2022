package com.jwsystem.entity.request;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("req_student")
public class ReqStudentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选课申请id
     */
    private Integer id;

    /**
     * 课程
     */
    private Integer courseId;

    /**
     * 学生
     */
    private String studentNum;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 申请时间
     */
    private LocalDateTime time;

    /**
     * 处理状态
     */
    private Integer dealt;

    /**
     * 审批是否通过
     */
    private Integer approved;


}
