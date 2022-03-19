package com.jwsystem.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    //数据封装类
    private String msg;//信息
    private Object data;//返回的数据对象

    public static Result succ(Object data){
        return succ("请求成功",data);
    }

    public static Result succ(String msg){
        return succ( msg ,null);
    }

    public static Result succ(String msg, Object data){
        Result r = new Result();
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg){
        return succ(msg,null);
    }

    public static Result fail(String msg, Object data){
        Result r = new Result();
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
