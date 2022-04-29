package com.jwsystem.dao;

import com.jwsystem.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao {
    //得到admin信息
    UserVO getUserByNumber(String number);
    //得到选课功能状态
    boolean getCurr();
    //设置选课功能是否开启
    void setCurr(boolean curricularVariable);
}