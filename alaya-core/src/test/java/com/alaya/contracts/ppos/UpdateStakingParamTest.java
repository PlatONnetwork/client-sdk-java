package com.alaya.contracts.ppos;

import com.alaya.abi.solidity.datatypes.BytesType;
import com.alaya.abi.solidity.datatypes.generated.Uint16;
import com.alaya.contracts.ppos.dto.req.UpdateStakingParam;
import com.alaya.protocol.core.DefaultBlockParameterName;
import com.alaya.tx.Transfer;
import com.alaya.utils.Convert;
import com.alaya.utils.Numeric;
import org.junit.Test;

import java.math.BigDecimal;

public class UpdateStakingParamTest {
    @Test
    public void getSubmitInputParameters() throws Exception {


        UpdateStakingParam.Builder builder = new UpdateStakingParam.Builder();
        builder.setBenifitAddress(null);
        builder.setNodeId(null);
        builder.setNodeName(null);
        builder.setRewardPer(null);
        UpdateStakingParam param = new UpdateStakingParam(builder);

        param.getSubmitInputParameters();
        System.out.println("paramList:" + param.toString());

  }

}
