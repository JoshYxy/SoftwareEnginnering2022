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
@TableName("req_timepart")
public class ReqTimepartPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer requestId;

    private String teacherNum;

    private Integer weekday;

    private String section;

    private Integer roomId;


}