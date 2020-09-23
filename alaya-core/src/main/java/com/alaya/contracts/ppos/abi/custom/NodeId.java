package com.alaya.contracts.ppos.abi.custom;

import com.alaya.contracts.ppos.abi.CustomType;
import com.alaya.rlp.solidity.RlpString;
import com.alaya.rlp.solidity.RlpType;
import com.alaya.utils.Numeric;

public class NodeId extends CustomType {

    private String nodeId;

    public NodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public RlpType getRlpEncodeData() {
        return RlpString.create(Numeric.hexStringToByteArray(nodeId));
    }
}
