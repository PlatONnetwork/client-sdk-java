package com.alaya.contracts.ppos.abi;

import com.alaya.abi.solidity.datatypes.Type;
import com.alaya.rlp.solidity.RlpEncoder;
import com.alaya.rlp.solidity.RlpList;
import com.alaya.rlp.solidity.RlpString;
import com.alaya.rlp.solidity.RlpType;

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
