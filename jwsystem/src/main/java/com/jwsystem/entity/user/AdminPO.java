package com.jwsystem.entity.user;

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
@TableName("admin")
public class AdminPO implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String ROUND_ONE_OPEN = "一轮选课开始";
    public static String ROUND_ONE_END = "一轮选课结束";
    public static String ROUND_TWO_OPEN = "二轮选课开始";
    public static String ROUND_TWO_END = "二轮选课结束";
    public static String CLOSE = "当前不在选课时间段内";

    private String role;

    @TableId(value = "number", type = IdType.AUTO)
    private String number;

    private String password;

    /**
     * 第一轮 第二轮 未开始 结束
     */
    private String curricularVariable;


}
