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
@TableName("admin")
public class AdminPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String role;

    private String number;

    private String password;

    /**
     * 第一轮 第二轮 未开始 结束
     */
    private String curricularVariable;


}
