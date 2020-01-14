package com.platon.sdk.contracts.ppos.abi.custom;

import com.platon.sdk.contracts.ppos.abi.CustomType;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

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
