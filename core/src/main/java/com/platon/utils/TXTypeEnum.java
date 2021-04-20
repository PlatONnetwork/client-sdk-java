package com.platon.utils;

public enum TXTypeEnum {
    MPC(Long.valueOf(5)),WASM(Long.valueOf(2)),DEFAULT(null);
    public Long type;
    TXTypeEnum(Long type){
        this.type = type;
    }
}
