package com.platon.tx.exceptions;

import com.platon.protocol.core.Response;

import java.io.IOException;

/**
 * @Author liushuyu
 * @Date 2021/5/12 16:46
 * @Version
 * @Desc
 */
public class PlatonCallTimeoutException extends IOException {

    private Response response;
    private int code;
    private String msg;

    public PlatonCallTimeoutException(int code,String msg,Response response){
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public PlatonCallTimeoutException(int code,String msg,Response response,Throwable ex){
        super(ex);
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
