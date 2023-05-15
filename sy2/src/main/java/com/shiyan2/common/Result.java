package com.shiyan2.common;

import lombok.Data;

@Data
public class Result {

    private int code; //编码200/400
    private String msg; //成功/失败
    private long total; //总记录数
    private Object data; //数据

    public static Result fail(){
        return result(400,"失败",0L,null);
    }

    public static Result suc(Object data){return result(200,"成功",0L,data);}
    public static Result suc(Object data,Long total){return result(200,"成功",total,data);}
    public static Result suc(){return result(200,"success",0L,null);}
    public static Result result(int code,String msg,Long total,Object data){

        Result result =new Result();
        result.setData(data);
        result.setCode(code);
        result.setMsg(msg);
        result.setTotal(total);

        return result;
    }
}
