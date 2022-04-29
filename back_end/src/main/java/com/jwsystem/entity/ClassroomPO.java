package com.jwsystem.entity;

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
@TableName("classroom")
public class ClassroomPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "room_id", type = IdType.AUTO)
    private Integer roomId;

    private Integer buildingId;

    private String roomNum;

    private String capacity;


}
