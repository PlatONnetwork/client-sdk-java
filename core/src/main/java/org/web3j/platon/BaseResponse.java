package org.web3j.platon;

import com.alibaba.fastjson.annotation.JSONField;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class BaseResponse<T> {

    @JSONField(name = "Code")
    public int code;

    @JSONField(name = "Ret")
    public T data;

    @JSONField(name = "ErrMsg")
    public String errMsg;

    public TransactionReceipt transactionReceipt;

    public BaseResponse() {
        this.code = ErrorCode.SYSTEM_ERROR;
        this.errMsg = ErrorCode.getErrorMsg(code);
    }

    public boolean isStatusOk() {
        return code == ErrorCode.SUCCESS;
    }

    public BaseResponse(Throwable throwable) {
        this.code = ErrorCode.SYSTEM_ERROR;
        this.errMsg = throwable.getMessage();
    }

    public BaseResponse(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public BaseResponse(int code, TransactionReceipt transactionReceipt) {
        this.code = code;
        this.errMsg = ErrorCode.getErrorMsg(code);
        this.transactionReceipt = transactionReceipt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public TransactionReceipt getTransactionReceipt() {
        return transactionReceipt;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    @Override
    public String toString() {
        return "BaseResponse [code=" + code + ", data=" + data + ", errMsg=" + errMsg + "]";
    }
}
