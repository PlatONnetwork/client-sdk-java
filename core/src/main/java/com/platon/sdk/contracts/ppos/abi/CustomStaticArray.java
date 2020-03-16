package com.platon.sdk.contracts.ppos.abi;

import org.web3j.abi.datatypes.Type;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class CustomStaticArray<T extends CustomType> implements Type<List<T>> {

    private List<T> list;

    public CustomStaticArray(List<T> values) {
        list = values;
    }

    public RlpType getRlpEncodeData() {
        if (list != null ) {
            List<RlpType> rlpListList = new ArrayList<>();
            for (T t : list) {
                rlpListList.add(t.getRlpEncodeData());
            }
            return RlpString.create(RlpEncoder.encode(new RlpList(rlpListList)));
        }
        throw new RuntimeException("unsupported types");
    }

    @Override
    public List<T> getValue() {
        return list;
    }

    @Override
    public String getTypeAsString() {
        return null;
    }
}
