package org.web3j.platon;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseResponse<T> {

    @JSONField(name = "Status")
    public boolean status;

    @JSONField(name = "Data")
    public T data;

    @JSONField(name = "ErrMsg")
    public String errMsg;

    public BaseResponse() {
    }

    public boolean isStatusOk() {
        return status;
    }

    public BaseResponse(Throwable throwable) {
        this.status = false;
        this.errMsg = throwable.getMessage();
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", data=" + data +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
