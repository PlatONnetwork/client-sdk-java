package com.platon.contracts.ppos.dto.enums;

import com.platon.contracts.ppos.dto.resp.ParamItem;

public interface GovernParamItemSupported {

    ParamItem Staking_stakeThreshold = new ParamItem("staking", "stakeThreshold");
    ParamItem Staking_operatingThreshold = new ParamItem("staking", "operatingThreshold");
    ParamItem Staking_maxValidators = new ParamItem("staking", "maxValidators");
    ParamItem Staking_unStakeFreezeDuration = new ParamItem("staking", "unStakeFreezeDuration");
    ParamItem Staking_rewardPerMaxChangeRange = new ParamItem("staking", "rewardPerMaxChangeRange");
    ParamItem Staking_rewardPerChangeInterval = new ParamItem("staking", "rewardPerChangeInterval");
    ParamItem Staking_unDelegateFreezeDuration = new ParamItem("staking", "unDelegateFreezeDuration");

    ParamItem Slashing_stakeThreshold = new ParamItem("slashing", "KeySlashFractionDuplicateSign");
    ParamItem Slashing_duplicateSignReportReward = new ParamItem("slashing", "duplicateSignReportReward");
    ParamItem Slashing_maxEvidenceAge = new ParamItem("slashing", "maxEvidenceAge");
    ParamItem Slashing_slashBlocksReward = new ParamItem("slashing", "slashBlocksReward");
    ParamItem Slashing_zeroProduceCumulativeTime = new ParamItem("slashing", "zeroProduceCumulativeTime");
    ParamItem Slashing_zeroProduceNumberThreshold = new ParamItem("slashing", "zeroProduceNumberThreshold");
    ParamItem Slashing_zeroProduceFreezeDuration = new ParamItem("slashing", "zeroProduceFreezeDuration");


    ParamItem Block_maxBlockGasLimit = new ParamItem("block", "maxBlockGasLimit");

    ParamItem Reward_increaseIssuanceRatio = new ParamItem("reward", "increaseIssuanceRatio");

    ParamItem Restricting_minimumRelease = new ParamItem("restricting", "minimumRelease");
}
