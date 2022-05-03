package com.jwsystem.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("student")
public class StudentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String role;

    /**
     * 学号
     */
    @TableId(value = "number"/*, type = IdType.AUTO*/)
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

    private Integer majorId;

    private Integer collegeId;

}
