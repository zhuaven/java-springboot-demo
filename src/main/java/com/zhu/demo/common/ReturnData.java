package com.zhu.demo.common;

import lombok.Data;

@Data
public class ReturnData<T> {
    public String code;

    public String msg;

    public T data;

    public ReturnData(){
        code = "000000";
        msg = "成功";
    }

    public ReturnData(T data){
        this.data = data;
    }

    public ReturnData(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

