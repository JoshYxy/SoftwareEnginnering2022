package com.jwsystem.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    //数据封装类
    private String msg;//信息
    private Object data1;//返回的数据对象
    private Object data2;
    private Object data3;

    public static Result succ(Object data){
        return succ("请求成功",data);
    }

    public static Result succ3(Object data1,Object data2,Object data3){
            Result r = new Result();
            r.setData1(data1);
            r.setData2(data2);
            r.setData3(data3);
            r.setMsg("请求成功");
            return r;
    }

    public static Result succ(String msg){
        return succ( msg ,null);
    }

    public static Result succ(String msg, Object data){
        Result r = new Result();
        r.setMsg(msg);
        r.setData1(data);
        return r;
    }

    public static Result fail(String msg){
        return fail(msg,null);
    }

    public static Result fail(String msg, Object data){
        Result r = new Result();
        r.setMsg(msg);
        r.setData1(data);
        return r;
    }
}
