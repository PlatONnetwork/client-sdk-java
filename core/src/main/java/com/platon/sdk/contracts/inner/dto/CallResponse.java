package com.platon.sdk.contracts.inner.dto;

public class CallResponse<T> extends BaseResponse {

    private T data;

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    
	@Override
	public String toString() {
		return "CallResponse [data=" + data + ", getCode()=" + getCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}
}
