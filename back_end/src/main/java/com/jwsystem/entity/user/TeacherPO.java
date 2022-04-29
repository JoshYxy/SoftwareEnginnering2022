package com.jwsystem.entity.user;

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
@TableName("teacher")
public class TeacherPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String role;

    /**
     * 工号
     */
    private String number;

    /**
     * 身份证号
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    private String password;

    private String phone;

    private String email;

    /**
     * 状态
     */
    private String status;

    /**
     * 专业
     */
    private Integer majorId;


}
