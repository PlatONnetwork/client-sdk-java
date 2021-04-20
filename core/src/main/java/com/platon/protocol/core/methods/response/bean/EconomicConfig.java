package com.platon.protocol.core.methods.response.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EconomicConfig {
    /**
     * common的配置项
     */
    private Common common;
    /**
     * staking的配置项
     */
    private Staking staking;
    /**
     * slashing的配置项
     */
    private Slashing slashing;
    /**
     * gov的配置项
     */
    private Gov gov;
    /**
     * reward的配置项
     */
    private Reward reward;
    /**
     * innerAcc的配置项
     */
    private InnerAcc innerAcc;

    /**
     * config item for restricting plan
     */
    private Restricting restricting;

    public EconomicConfig() {
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public Staking getStaking() {
        return staking;
    }

    public void setStaking(Staking staking) {
        this.staking = staking;
    }

    public Slashing getSlashing() {
        return slashing;
    }

    public void setSlashing(Slashing slashing) {
        this.slashing = slashing;
    }

    public Gov getGov() {
        return gov;
    }

    public void setGov(Gov gov) {
        this.gov = gov;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public InnerAcc getInnerAcc() {
        return innerAcc;
    }

    public void setInnerAcc(InnerAcc innerAcc) {
        this.innerAcc = innerAcc;
    }

    public Restricting getRestricting() {
        return restricting;
    }

    public void setRestricting(Restricting restricting) {
        this.restricting = restricting;
    }

    public class Common {
        /**
         * 结算周期规定的分钟数（整数）(eh)
         */
        private BigInteger maxEpochMinutes;
        /**
         * 共识轮验证人数
         */
        private BigInteger maxConsensusVals;
        /**
         * 增发周期的时间（分钟）
         */
        private BigInteger additionalCycleTime;
        /**
         * 底层内部调试用
         */
        private BigInteger nodeBlockTimeWindow;
        /**
         * 底层内部调试用
         */
        private BigInteger perRoundBlocks;

        public Common() {

        }

        public BigInteger getMaxEpochMinutes() {
            return maxEpochMinutes;
        }

        public void setMaxEpochMinutes(BigInteger maxEpochMinutes) {
            this.maxEpochMinutes = maxEpochMinutes;
        }

        public BigInteger getMaxConsensusVals() {
            return maxConsensusVals;
        }

        public void setMaxConsensusVals(BigInteger maxConsensusVals) {
            this.maxConsensusVals = maxConsensusVals;
        }

        public BigInteger getAdditionalCycleTime() {
            return additionalCycleTime;
        }

        public void setAdditionalCycleTime(BigInteger additionalCycleTime) {
            this.additionalCycleTime = additionalCycleTime;
        }

        public BigInteger getNodeBlockTimeWindow() {
            return nodeBlockTimeWindow;
        }

        public void setNodeBlockTimeWindow(BigInteger nodeBlockTimeWindow) {
            this.nodeBlockTimeWindow = nodeBlockTimeWindow;
        }

        public BigInteger getPerRoundBlocks() {
            return perRoundBlocks;
        }

        public void setPerRoundBlocks(BigInteger perRoundBlocks) {
            this.perRoundBlocks = perRoundBlocks;
        }

        @Override
        public String toString() {
            return "Common{" +
                    "maxEpochMinutes=" + maxEpochMinutes +
                    ", maxConsensusVals=" + maxConsensusVals +
                    ", additionalCycleTime=" + additionalCycleTime +
                    ", nodeBlockTimeWindow=" + nodeBlockTimeWindow +
                    ", perRoundBlocks=" + perRoundBlocks +
                    '}';
        }
    }

    public class Staking {
        /**
         * 质押的von门槛（单位：Von）===>100w lat
         */
        private BigInteger stakeThreshold;
        /**
         * (incr, decr)委托或incr设置允许的最小阈值（单位：Von）===> 10 lat
         */
        private BigInteger operatingThreshold;
        /**
         * 结算周期验证人个数
         */
        private BigInteger maxValidators;
        /**
         * 退出质押后von被冻结的周期(单位： 结算周期，退出表示主动撤销和被动失去资格)
         */
        private BigInteger unStakeFreezeDuration;
        private BigInteger rewardPerMaxChangeRange;
        private BigInteger rewardPerChangeInterval;

        public Staking() {
        }

        public BigInteger getStakeThreshold() {
            return stakeThreshold;
        }

        public void setStakeThreshold(BigInteger stakeThreshold) {
            this.stakeThreshold = stakeThreshold;
        }

        public BigInteger getOperatingThreshold() {
            return operatingThreshold;
        }

        public void setOperatingThreshold(BigInteger operatingThreshold) {
            this.operatingThreshold = operatingThreshold;
        }

        public BigInteger getMaxValidators() {
            return maxValidators;
        }

        public void setMaxValidators(BigInteger maxValidators) {
            this.maxValidators = maxValidators;
        }

        public BigInteger getUnStakeFreezeDuration() {
            return unStakeFreezeDuration;
        }

        public void setUnStakeFreezeDuration(BigInteger unStakeFreezeDuration) {
            this.unStakeFreezeDuration = unStakeFreezeDuration;
        }

        public BigInteger getRewardPerMaxChangeRange() {
            return  rewardPerMaxChangeRange;
        }

        public void setRewardPerMaxChangeRange(BigInteger rewardPerMaxChangeRange) {
            this.rewardPerMaxChangeRange = rewardPerMaxChangeRange;
        }

        public BigInteger getRewardPerChangeInterval() {
           return rewardPerChangeInterval;
        }

        public void setRewardPerChangeInterval(BigInteger rewardPerChangeInterval) {
            this.rewardPerChangeInterval = rewardPerChangeInterval;
        }

        @Override
        public String toString() {
            return "Staking{" +
                    "stakeThreshold=" + stakeThreshold +
                    ", operatingThreshold=" + operatingThreshold +
                    ", maxValidators=" + maxValidators +
                    ", unStakeFreezeDuration=" + unStakeFreezeDuration +
                    ", rewardPerMaxChangeRange=" + rewardPerMaxChangeRange +
                    ", rewardPerChangeInterval=" + rewardPerChangeInterval +
                    '}';
        }
    }

    public class Slashing {

        /**
         * 双签高处罚金额，万分比（‱）
         */
        private BigInteger slashFractionDuplicateSign;
        /**
         * 表示从扣除的惩罚金里面，拿出x%奖励给举报者（%）
         */
        private BigDecimal duplicateSignReportReward;
        /**
         * 零出块率惩罚的区块奖励数
         */
        private BigInteger slashBlocksReward;
        /**
         * 证据有效期
         */
        private BigInteger maxEvidenceAge;

        private BigInteger zeroProduceCumulativeTime;
        private BigInteger zeroProduceNumberThreshold;
        // 节点零出块惩罚被锁定时间
        private BigInteger zeroProduceFreezeDuration;

        public Slashing() {
        }

        public BigInteger getSlashFractionDuplicateSign() {
            return slashFractionDuplicateSign;
        }

        public void setSlashFractionDuplicateSign(BigInteger slashFractionDuplicateSign) {
            this.slashFractionDuplicateSign = slashFractionDuplicateSign;
        }

        public BigDecimal getDuplicateSignReportReward() {
            return duplicateSignReportReward;
        }

        public void setDuplicateSignReportReward(BigDecimal duplicateSignReportReward) {
            this.duplicateSignReportReward = duplicateSignReportReward;
        }

        public BigInteger getSlashBlocksReward() {
            return slashBlocksReward;
        }

        public void setSlashBlocksReward(BigInteger slashBlocksReward) {
            this.slashBlocksReward = slashBlocksReward;
        }

        public BigInteger getMaxEvidenceAge() {
            return maxEvidenceAge;
        }

        public void setMaxEvidenceAge(BigInteger maxEvidenceAge) {
            this.maxEvidenceAge = maxEvidenceAge;
        }

        public BigInteger getZeroProduceCumulativeTime() {
            return zeroProduceCumulativeTime;
        }

        public void setZeroProduceCumulativeTime(BigInteger zeroProduceCumulativeTime) {
            this.zeroProduceCumulativeTime = zeroProduceCumulativeTime;
        }

        public BigInteger getZeroProduceNumberThreshold() {
            return zeroProduceNumberThreshold;
        }

        public void setZeroProduceNumberThreshold(BigInteger zeroProduceNumberThreshold) {
            this.zeroProduceNumberThreshold = zeroProduceNumberThreshold;
        }

        public BigInteger getZeroProduceFreezeDuration() {
            return zeroProduceFreezeDuration;
        }

        public void setZeroProduceFreezeDuration(BigInteger zeroProduceFreezeDuration) {
            this.zeroProduceFreezeDuration = zeroProduceFreezeDuration;
        }

        @Override
        public String toString() {
            return "Slashing{" +
                    "slashFractionDuplicateSign=" + slashFractionDuplicateSign +
                    ", duplicateSignReportReward=" + duplicateSignReportReward +
                    ", slashBlocksReward=" + slashBlocksReward +
                    ", maxEvidenceAge=" + maxEvidenceAge +
                    ", zeroProduceCumulativeTime=" + zeroProduceCumulativeTime +
                    ", zeroProduceNumberThreshold=" + zeroProduceNumberThreshold +
                    ", zeroProduceFreezeDuration=" + zeroProduceFreezeDuration +
                    '}';
        }
    }

    public class Gov {
        /**
         * 升级提案的投票持续最长的时间（单位：s）
         */
        private BigInteger versionProposalVoteDurationSeconds;
        /**
         * 升级提案投票支持率阈值（大于等于此值，则升级提案投票通过）
         */
        private BigDecimal versionProposalSupportRate;
        /**
         * 文本提案的投票持续最长的时间（单位：s）
         */
        private BigInteger textProposalVoteDurationSeconds;
        /**
         * 文本提案投票参与率阈值（文本提案投票通过条件之一：大于此值，则文本提案投票通过）
         */
        private BigDecimal textProposalVoteRate;
        /**
         * 文本提案投票支持率阈值（文本提案投票通过条件之一：大于等于此值，则文本提案投票通过）
         */
        private BigDecimal textProposalSupportRate;
        /**
         * 取消提案投票参与率阈值（取消提案投票通过条件之一：大于此值，则取消提案投票通过）
         */
        private BigDecimal cancelProposalVoteRate;
        /**
         * 取消提案投票支持率阈值（取消提案投票通过条件之一：大于等于此值，则取消提案投票通过）
         */
        private BigDecimal cancelProposalSupportRate;
        /**
         * 参数提案的投票持续最长的时间（单位：s）
         */
        private BigInteger paramProposalVoteDurationSeconds;
        /**
         * 参数提案投票参与率阈值（参数提案投票通过条件之一：大于此值，则参数提案投票通过)
         */
        private BigDecimal paramProposalVoteRate;
        /**
         * 参数提案投票支持率阈值（参数提案投票通过条件之一：大于等于此值，则参数提案投票通过
         */
        private BigDecimal paramProposalSupportRate;

        public Gov() {
        }

        public BigInteger getVersionProposalVoteDurationSeconds() {
            return versionProposalVoteDurationSeconds;
        }

        public void setVersionProposalVoteDurationSeconds(BigInteger versionProposalVoteDurationSeconds) {
            this.versionProposalVoteDurationSeconds = versionProposalVoteDurationSeconds;
        }

        public BigDecimal getVersionProposalSupportRate() {
            return versionProposalSupportRate;
        }

        public void setVersionProposalSupportRate(BigDecimal versionProposalSupportRate) {
            this.versionProposalSupportRate = versionProposalSupportRate;
        }

        public BigInteger getTextProposalVoteDurationSeconds() {
            return textProposalVoteDurationSeconds;
        }

        public void setTextProposalVoteDurationSeconds(BigInteger textProposalVoteDurationSeconds) {
            this.textProposalVoteDurationSeconds = textProposalVoteDurationSeconds;
        }

        public BigDecimal getTextProposalVoteRate() {
            return textProposalVoteRate;
        }

        public void setTextProposalVoteRate(BigDecimal textProposalVoteRate) {
            this.textProposalVoteRate = textProposalVoteRate;
        }

        public BigDecimal getTextProposalSupportRate() {
            return textProposalSupportRate;
        }

        public void setTextProposalSupportRate(BigDecimal textProposalSupportRate) {
            this.textProposalSupportRate = textProposalSupportRate;
        }

        public BigDecimal getCancelProposalVoteRate() {
            return cancelProposalVoteRate;
        }

        public void setCancelProposalVoteRate(BigDecimal cancelProposalVoteRate) {
            this.cancelProposalVoteRate = cancelProposalVoteRate;
        }

        public BigDecimal getCancelProposalSupportRate() {
            return cancelProposalSupportRate;
        }

        public void setCancelProposalSupportRate(BigDecimal cancelProposalSupportRate) {
            this.cancelProposalSupportRate = cancelProposalSupportRate;
        }

        public BigInteger getParamProposalVoteDurationSeconds() {
            return paramProposalVoteDurationSeconds;
        }

        public void setParamProposalVoteDurationSeconds(BigInteger paramProposalVoteDurationSeconds) {
            this.paramProposalVoteDurationSeconds = paramProposalVoteDurationSeconds;
        }

        public BigDecimal getParamProposalVoteRate() {
            return paramProposalVoteRate;
        }

        public void setParamProposalVoteRate(BigDecimal paramProposalVoteRate) {
            this.paramProposalVoteRate = paramProposalVoteRate;
        }

        public BigDecimal getParamProposalSupportRate() {
            return paramProposalSupportRate;
        }

        public void setParamProposalSupportRate(BigDecimal paramProposalSupportRate) {
            this.paramProposalSupportRate = paramProposalSupportRate;
        }

        @Override
        public String toString() {
            return "Gov{" +
                    "versionProposalVoteDurationSeconds=" + versionProposalVoteDurationSeconds +
                    ", versionProposalSupportRate=" + versionProposalSupportRate +
                    ", textProposalVoteDurationSeconds=" + textProposalVoteDurationSeconds +
                    ", textProposalVoteRate=" + textProposalVoteRate +
                    ", textProposalSupportRate=" + textProposalSupportRate +
                    ", cancelProposalVoteRate=" + cancelProposalVoteRate +
                    ", cancelProposalSupportRate=" + cancelProposalSupportRate +
                    ", paramProposalVoteDurationSeconds=" + paramProposalVoteDurationSeconds +
                    ", paramProposalVoteRate=" + paramProposalVoteRate +
                    ", paramProposalSupportRate=" + paramProposalSupportRate +
                    '}';
        }
    }

    public class Reward {
        /**
         * 奖励池分配给出块奖励的比例，剩下的比例为分配给质押的奖励（%）
         */
        private BigInteger newBlockRate;
        /**
         * 基金会分配年，代表基金会每年边界的百分比
         */
        private BigInteger platonFoundationYear;

        public Reward() {
        }

        public BigInteger getNewBlockRate() {
            return newBlockRate;
        }

        public void setNewBlockRate(BigInteger newBlockRate) {
            this.newBlockRate = newBlockRate;
        }

        public BigInteger getPlatonFoundationYear() {
            return platonFoundationYear;
        }

        public void setPlatonFoundationYear(BigInteger platonFoundationYear) {
            this.platonFoundationYear = platonFoundationYear;
        }

        @Override
        public String toString() {
            return "Reward{" +
                    "newBlockRate=" + newBlockRate +
                    ", platonFoundationYear=" + platonFoundationYear +
                    '}';
        }
    }


    public class InnerAcc {

        /**
         * 基金会账号地址
         */
        private String platonFundAccount;
        /**
         * 基金会初始金额
         */
        private BigInteger platonFundBalance;
        /**
         * 社区开发者账户
         */
        private String cdfAccount;
        /**
         * 社区开发者初始金额
         */
        private BigInteger cdfBalance;

        public InnerAcc() {
        }

        public String getPlatonFundAccount() {
            return platonFundAccount;
        }

        public void setPlatonFundAccount(String platonFundAccount) {
            this.platonFundAccount = platonFundAccount;
        }

        public BigInteger getPlatonFundBalance() {
            return platonFundBalance;
        }

        public void setPlatonFundBalance(BigInteger platonFundBalance) {
            this.platonFundBalance = platonFundBalance;
        }

        public String getCdfAccount() {
            return cdfAccount;
        }

        public void setCdfAccount(String cdfAccount) {
            this.cdfAccount = cdfAccount;
        }

        public BigInteger getCdfBalance() {
            return cdfBalance;
        }

        public void setCdfBalance(BigInteger cdfBalance) {
            this.cdfBalance = cdfBalance;
        }

        @Override
        public String toString() {
            return "InnerAcc{" +
                    "platonFundAccount='" + platonFundAccount + '\'' +
                    ", platonFundBalance=" + platonFundBalance +
                    ", cdfAccount='" + cdfAccount + '\'' +
                    ", cdfBalance=" + cdfBalance +
                    '}';
        }
    }

    public class Restricting {
        /**
         * minimum of each releasing of restricting plan
         */
        private BigInteger minimumRelease;

        public BigInteger getMinimumRelease() {
            return minimumRelease;
        }

        public void setMinimumRelease(BigInteger minimumRelease) {
            this.minimumRelease = minimumRelease;
        }

        @Override
        public String toString() {
            return "Restricting{" +
                    "minimumRelease=" + minimumRelease +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "EconomicConfig{" +
                "common=" + common +
                ", staking=" + staking +
                ", slashing=" + slashing +
                ", gov=" + gov +
                ", reward=" + reward +
                ", innerAcc=" + innerAcc +
                '}';
    }
}
