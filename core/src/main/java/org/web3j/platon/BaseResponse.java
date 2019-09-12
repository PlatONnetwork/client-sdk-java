package org.web3j.platon;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseResponse<T> {

    @JSONField(name = "Code")
    public int code;

    @JSONField(name = "Data")
    public T data;

    @JSONField(name = "ErrMsg")
    public String errMsg;
    
    public TransactionReceipt transactionReceipt;

    public BaseResponse() {
    }

    public int getCode() {
        return code;
    }
    
    public boolean isStatusOk() {
    	return code == 0 ? true : false;
    }

	@Override
	public String toString() {
		return "BaseResponse [code=" + code + ", data=" + data + ", errMsg=" + errMsg + ", transactionReceipt="
				+ transactionReceipt + "]";
	}
}
