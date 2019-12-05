package com.platon.sdk.contracts.inner.dto;

import org.web3j.platon.ErrorCode;

public class BaseResponse {
	
    private int code;
    private String errMsg;
    
    public boolean isStatusOk() {
        return code == ErrorCode.SUCCESS;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "BaseResponse [code=" + code + ", errMsg=" + errMsg + "]";
    }
}
