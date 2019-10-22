package org.web3j.platon.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.alibaba.fastjson.annotation.JSONField;

public class EconomicConfig {
    @JSONField(name = "Common")
    private Common common;
    @JSONField(name = "Staking")
    private Staking staking;
    @JSONField(name = "Slashing")
    private Slashing slashing;
    @JSONField(name = "Gov")
    private Gov gov;	
    @JSONField(name = "Reward")
    private Reward reward;
    @JSONField(name = "InnerAcc")
    private InnerAcc innerAcc;
    
    
    
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


    public class Common {
        // 结算周期规定的分钟数（整数）
        @JSONField(name = "ExpectedMinutes")
        private BigInteger expectedMinutes;
        // 系统分配的节点出块时间窗口
        @JSONField(name = "NodeBlockTimeWindow")
        private BigInteger nodeBlockTimeWindow;
        // 每个验证人每个view出块数量目标值
        @JSONField(name = "PerRoundBlocks")
        private BigInteger perRoundBlocks;
        // 当前共识轮验证节点数量
        @JSONField(name = "ValidatorCount")
        private BigInteger validatorCount;
        // 增发周期的分钟数
        @JSONField(name = "AdditionalCycleTime")
        private BigInteger additionalCycleTime;
        
		public BigInteger getExpectedMinutes() {
			return expectedMinutes;
		}
		public void setExpectedMinutes(BigInteger expectedMinutes) {
			this.expectedMinutes = expectedMinutes;
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
		public BigInteger getValidatorCount() {
			return validatorCount;
		}
		public void setValidatorCount(BigInteger validatorCount) {
			this.validatorCount = validatorCount;
		}
		public BigInteger getAdditionalCycleTime() {
			return additionalCycleTime;
		}
		public void setAdditionalCycleTime(BigInteger additionalCycleTime) {
			this.additionalCycleTime = additionalCycleTime;
		}
    }
    
    public class Staking {
        // 验证人最低的质押Token数(VON)
        @JSONField(name = "StakeThreshold")
        private BigDecimal stakeThreshold;
        // 委托人每次委托及赎回的最低Token数(VON)
        @JSONField(name = "MinimumThreshold")
        private BigDecimal minimumThreshold;
        // 每个结算周期内验证人数（24或101）
        @JSONField(name = "EpochValidatorNum")
        private BigDecimal epochValidatorNum;
        // 犹豫期是几个结算周期v
        @JSONField(name = "HesitateRatio")
        private BigDecimal hesitateRatio;
        // 节点质押退回锁定周期
        @JSONField(name = "UnStakeFreezeRatio")
        private BigInteger unStakeFreezeRatio;
        // 解除委托锁定的结算周期
        @JSONField(name = "ActiveUnDelegateFreezeRatio")
        private BigDecimal activeUnDelegateFreezeRatio;
		public BigDecimal getStakeThreshold() {
			return stakeThreshold;
		}
		public void setStakeThreshold(BigDecimal stakeThreshold) {
			this.stakeThreshold = stakeThreshold;
		}
		public BigDecimal getMinimumThreshold() {
			return minimumThreshold;
		}
		public void setMinimumThreshold(BigDecimal minimumThreshold) {
			this.minimumThreshold = minimumThreshold;
		}
		public BigDecimal getEpochValidatorNum() {
			return epochValidatorNum;
		}
		public void setEpochValidatorNum(BigDecimal epochValidatorNum) {
			this.epochValidatorNum = epochValidatorNum;
		}
		public BigDecimal getHesitateRatio() {
			return hesitateRatio;
		}
		public void setHesitateRatio(BigDecimal hesitateRatio) {
			this.hesitateRatio = hesitateRatio;
		}
		public BigInteger getUnStakeFreezeRatio() {
			return unStakeFreezeRatio;
		}
		public void setUnStakeFreezeRatio(BigInteger unStakeFreezeRatio) {
			this.unStakeFreezeRatio = unStakeFreezeRatio;
		}
		public BigDecimal getActiveUnDelegateFreezeRatio() {
			return activeUnDelegateFreezeRatio;
		}
		public void setActiveUnDelegateFreezeRatio(BigDecimal activeUnDelegateFreezeRatio) {
			this.activeUnDelegateFreezeRatio = activeUnDelegateFreezeRatio;
		}
    }
    
    public class Slashing {

        @JSONField(name = "DuplicateSignReportReward")
        private BigDecimal duplicateSignReportReward;
        // 区块双签扣除验证人自有质押金比例 100%
        @JSONField(name = "DuplicateSignHighSlashing")
        private BigDecimal duplicateSignHighSlashing;
        // 低出块率处罚多少个区块奖励
        @JSONField(name = "NumberOfBlockRewardForSlashing")
        private BigDecimal numberOfBlockRewardForSlashing;
        // EvidenceValidEpoch
        @JSONField(name = "EvidenceValidEpoch")
        private BigDecimal evidenceValidEpoch;
		public BigDecimal getDuplicateSignReportReward() {
			return duplicateSignReportReward;
		}
		public void setDuplicateSignReportReward(BigDecimal duplicateSignReportReward) {
			this.duplicateSignReportReward = duplicateSignReportReward;
		}
		public BigDecimal getDuplicateSignHighSlashing() {
			return duplicateSignHighSlashing;
		}
		public void setDuplicateSignHighSlashing(BigDecimal duplicateSignHighSlashing) {
			this.duplicateSignHighSlashing = duplicateSignHighSlashing;
		}
		public BigDecimal getNumberOfBlockRewardForSlashing() {
			return numberOfBlockRewardForSlashing;
		}
		public void setNumberOfBlockRewardForSlashing(BigDecimal numberOfBlockRewardForSlashing) {
			this.numberOfBlockRewardForSlashing = numberOfBlockRewardForSlashing;
		}
		public BigDecimal getEvidenceValidEpoch() {
			return evidenceValidEpoch;
		}
		public void setEvidenceValidEpoch(BigDecimal evidenceValidEpoch) {
			this.evidenceValidEpoch = evidenceValidEpoch;
		}
    }
    
    public class Gov {
        //
        @JSONField(name = "VersionProposalVote_DurationSeconds")
        private BigDecimal versionProposalVoteDurationSeconds;
        // 升级提案通过率
        @JSONField(name = "VersionProposal_SupportRate")
        private BigDecimal versionProposalSupportRate;
        //
        @JSONField(name = "VersionProposalActive_ConsensusRounds")
        private BigDecimal versionProposalActiveConsensusRounds;
        //
        @JSONField(name = "TextProposalVote_DurationSeconds")
        private BigDecimal textProposalVoteDurationSeconds;
        // 文本提案参与率
        @JSONField(name = "TextProposal_VoteRate")
        private BigDecimal textProposalVoteRate;
        // 文本提案支持率
        @JSONField(name = "TextProposal_SupportRate")
        private BigDecimal textProposalSupportRate;
        // 取消提案参与率
        @JSONField(name = "CancelProposal_VoteRate")
        private BigDecimal cancelProposalVoteRate;
        // 取消提案支持率
        @JSONField(name = "CancelProposal_SupportRate")
        private BigDecimal cancelProposalSupportRate;
		public BigDecimal getVersionProposalVoteDurationSeconds() {
			return versionProposalVoteDurationSeconds;
		}
		public void setVersionProposalVoteDurationSeconds(BigDecimal versionProposalVoteDurationSeconds) {
			this.versionProposalVoteDurationSeconds = versionProposalVoteDurationSeconds;
		}
		public BigDecimal getVersionProposalSupportRate() {
			return versionProposalSupportRate;
		}
		public void setVersionProposalSupportRate(BigDecimal versionProposalSupportRate) {
			this.versionProposalSupportRate = versionProposalSupportRate;
		}
		public BigDecimal getVersionProposalActiveConsensusRounds() {
			return versionProposalActiveConsensusRounds;
		}
		public void setVersionProposalActiveConsensusRounds(BigDecimal versionProposalActiveConsensusRounds) {
			this.versionProposalActiveConsensusRounds = versionProposalActiveConsensusRounds;
		}
		public BigDecimal getTextProposalVoteDurationSeconds() {
			return textProposalVoteDurationSeconds;
		}
		public void setTextProposalVoteDurationSeconds(BigDecimal textProposalVoteDurationSeconds) {
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
    }
    
	public class Reward {
	    // 激励池分配给出块激励的比例 50%
	    @JSONField(name = "NewBlockRate")
	    private BigDecimal newBlockRate;
	    // 一个参数，单位是年， 这个参数时间到达后，按NewBlockRate分配奖励给矿工
	    @JSONField(name = "PlatONFoundationYear")
	    private BigInteger platONFoundationYear;
		
	    public BigDecimal getNewBlockRate() {
			return newBlockRate;
		}
		public void setNewBlockRate(BigDecimal newBlockRate) {
			this.newBlockRate = newBlockRate;
		}
		public BigInteger getPlatONFoundationYear() {
			return platONFoundationYear;
		}
		public void setPlatONFoundationYear(BigInteger platONFoundationYear) {
			this.platONFoundationYear = platONFoundationYear;
		}
	}
    
    
	public class InnerAcc {
	    @JSONField(name = "PlatONFundAccount")
	    private String platONFundAccount;
	    @JSONField(name = "PlatONFundBalance")
	    private BigDecimal platONFundBalance;
	    @JSONField(name = "CDFAccount")
	    private String CDFAccount;
	    @JSONField(name = "CDFBalance")
	    private BigDecimal CDFBalance;
	    
		public String getPlatONFundAccount() {
			return platONFundAccount;
		}
		public void setPlatONFundAccount(String platONFundAccount) {
			this.platONFundAccount = platONFundAccount;
		}
		public BigDecimal getPlatONFundBalance() {
			return platONFundBalance;
		}
		public void setPlatONFundBalance(BigDecimal platONFundBalance) {
			this.platONFundBalance = platONFundBalance;
		}
		public String getCDFAccount() {
			return CDFAccount;
		}
		public void setCDFAccount(String cDFAccount) {
			CDFAccount = cDFAccount;
		}
		public BigDecimal getCDFBalance() {
			return CDFBalance;
		}
		public void setCDFBalance(BigDecimal cDFBalance) {
			CDFBalance = cDFBalance;
		}
	}

}
