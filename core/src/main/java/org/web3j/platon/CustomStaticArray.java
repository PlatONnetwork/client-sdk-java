package org.web3j.platon;

import org.web3j.abi.datatypes.StaticArray;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class CustomStaticArray<T extends CustomType> extends StaticArray<T> {

    public CustomStaticArray(List<T> values) {
        super(values);
    }

    public RlpType getRlpEncodeData() {

        List<T> list = getValue();
        if (list != null && !list.isEmpty()) {
            List<RlpType> rlpListList = new ArrayList<>();
            for (T t : list) {
                rlpListList.add(t.getRlpEncodeData());
            }
            return RlpString.create(RlpEncoder.encode(new RlpList(rlpListList)));
        }
        throw new RuntimeException("unsupported types");
    }
}
