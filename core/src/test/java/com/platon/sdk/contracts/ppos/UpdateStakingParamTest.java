package com.platon.sdk.contracts.ppos;

import com.platon.sdk.contracts.ppos.abi.Function;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.req.UpdateStakingParam;
import com.platon.sdk.contracts.ppos.utils.EncoderUtils;
import org.junit.Test;

public class UpdateStakingParamTest {
    @Test
    public void getSubmitInputParameters() throws Exception {
        UpdateStakingParam.Builder builder = new UpdateStakingParam.Builder();
        builder.setNodeId("0x7e4ebe1f887cb2c00d67e89f9b807a07117b302c7b52ec17d92832ea9776d0619d9bd3af04b37b9af56cc68fdfde2e4577efa554426d0e110ee7af89618b2940");
        builder.setBenifitAddress("");
        builder.setNodeName("");
        builder.setRewardPer(null);
        builder.setExternalId("ExternalId");
        builder.setDetails("Details");
        builder.setWebSite(null);

        UpdateStakingParam param = new UpdateStakingParam(builder);

        param.getSubmitInputParameters();
        System.out.println("paramList:" + param.toString());


        Function function = new Function(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                param.getSubmitInputParameters());

        String input = EncoderUtils.functionEncoder(function);

        System.out.println("input:" + input);
    }
}

