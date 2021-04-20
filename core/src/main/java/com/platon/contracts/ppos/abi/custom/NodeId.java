package com.platon.contracts.ppos.abi.custom;

import com.platon.contracts.ppos.abi.CustomType;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import com.platon.utils.Numeric;

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
