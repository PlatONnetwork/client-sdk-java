package com.platon.sdk.contracts.ppos.abi;

import org.web3j.abi.datatypes.Type;
import org.web3j.rlp.RlpType;

public abstract class CustomType implements Type {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getTypeAsString() {
        return null;
    }

    public abstract RlpType getRlpEncodeData();
}
