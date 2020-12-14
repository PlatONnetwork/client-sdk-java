package com.alaya.contracts.ppos.dto.req;

import com.alaya.abi.solidity.datatypes.BytesType;
import com.alaya.abi.solidity.datatypes.Type;
import com.alaya.abi.solidity.datatypes.Utf8String;
import com.alaya.abi.solidity.datatypes.generated.Int256;
import com.alaya.abi.solidity.datatypes.generated.Uint16;
import com.alaya.abi.solidity.datatypes.generated.Uint32;
import com.alaya.bech32.Bech32;
import com.alaya.contracts.ppos.dto.enums.StakingAmountType;
import com.alaya.protocol.core.methods.response.bean.ProgramVersion;
import com.alaya.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.alaya.contracts.ppos.dto.enums.StakingAmountType.DEFAULT_TYPE;

public class StakingParam implements Cloneable {

    /**
     * 64bytes 被质押的节点Id(也叫候选人的节点Id)
     */
    private String nodeId;
    /**
     * 质押的von
     */
    private BigInteger amount;
    /**
     * 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额; 2: 默认优先使用锁仓金额，当质押金额大于锁仓金额时才使用自由金额
     */
    private StakingAmountType stakingAmountType = DEFAULT_TYPE;
    /**
     * 20bytes 用于接受出块奖励和质押奖励的收益账户
     */
    private String benifitAddress;
    /**
     * 外部Id(有长度限制，给第三方拉取节点描述的Id)
     */
    private String externalId;
    /**
     * 被质押节点的名称(有长度限制，表示该节点的名称)
     */
    private String nodeName;
    /**
     * 节点的第三方主页(有长度限制，表示该节点的主页)
     */
    private String webSite;
    /**
     * 节点的描述(有长度限制，表示该节点的描述)
     */
    private String details;
    /**
     * 程序的真实版本，治理rpc获取
     */
    private ProgramVersion processVersion;
    /**
     * bls的公钥
     */
    private String blsPubKey;
    
    /**
     * bls的证明
     */
    private String blsProof;

    /**
     * 奖励分成比例，采用BasePoint 1BP=0.01%
     */
    private BigInteger rewardPer;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public StakingAmountType getStakingAmountType() {
        return stakingAmountType;
    }

    public void setStakingAmountType(StakingAmountType stakingAmountType) {
        this.stakingAmountType = stakingAmountType;
    }

    public String getBenifitAddress() {
        return benifitAddress;
    }

    public void setBenifitAddress(String benifitAddress) {
        this.benifitAddress = benifitAddress;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ProgramVersion getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(ProgramVersion processVersion) {
        this.processVersion = processVersion;
    }

    public String getBlsPubKey() {
        return blsPubKey;
    }

    public void setBlsPubKey(String blsPubKey) {
        this.blsPubKey = blsPubKey;
    }

    public String getBlsProof() {
        return blsProof;
    }

    public void setBlsProof(String blsProof) {
        this.blsProof = blsProof;
    }

    public BigInteger getRewardPer() {
        return rewardPer;
    }

    public void setRewardPer(BigInteger rewardPer) {
        this.rewardPer = rewardPer;
    }
    
    public StakingParam(Builder builder) {
        this.nodeId = builder.nodeId;
        this.amount = builder.amount;
        this.stakingAmountType = builder.stakingAmountType;
        this.benifitAddress = builder.benifitAddress;
        this.externalId = builder.externalId;
        this.nodeName = builder.nodeName;
        this.webSite = builder.webSite;
        this.details = builder.details;
        this.processVersion = builder.processVersion;
        this.blsPubKey = builder.blsPubKey;
        this.blsProof = builder.blsProof;
        this.rewardPer = builder.rewardPer;
    }

    public List<Type> getSubmitInputParameters() {
        return Arrays.<Type>asList(new Uint16(stakingAmountType.getValue())
                , new BytesType(Bech32.addressDecode(benifitAddress))
                , new BytesType(Numeric.hexStringToByteArray(nodeId))
                , externalId == null ? null : new Utf8String(externalId)
                , nodeName == null ? null : new Utf8String(nodeName)
                , webSite == null ? null : new Utf8String(webSite)
                , details == null ? null : new Utf8String(details)
                , new Int256(amount)
                , new Uint16(rewardPer)
                , new Uint32(processVersion.getProgramVersion())
                , new BytesType(Numeric.hexStringToByteArray(processVersion.getProgramVersionSign()))
                , new BytesType(Numeric.hexStringToByteArray(blsPubKey))
                , new BytesType(Numeric.hexStringToByteArray(blsProof))
        );
    }

    @Override
    public StakingParam clone() {
        StakingParam stakingParam = null;
        try {
            stakingParam = (StakingParam) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stakingParam;
    }

    public static final class Builder {
        private String nodeId;
        private BigInteger amount;
        private StakingAmountType stakingAmountType;
        private String benifitAddress;
        private String externalId;
        private String nodeName;
        private String webSite;
        private String details;
        private ProgramVersion processVersion;
        private String blsPubKey;
        private String blsProof;
        private BigInteger rewardPer;

        public Builder setNodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public Builder setAmount(BigInteger amount) {
            this.amount = amount;
            return this;
        }

        public Builder setStakingAmountType(StakingAmountType stakingAmountType) {
            this.stakingAmountType = stakingAmountType;
            return this;
        }

        public Builder setBenifitAddress(String benifitAddress) {
            this.benifitAddress = benifitAddress;
            return this;
        }

        public Builder setExternalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder setNodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder setWebSite(String webSite) {
            this.webSite = webSite;
            return this;
        }

        public Builder setDetails(String details) {
            this.details = details;
            return this;
        }

        public Builder setProcessVersion(ProgramVersion processVersion) {
            this.processVersion = processVersion;
            return this;
        }

        public Builder setBlsPubKey(String blsPubKey) {
            this.blsPubKey = blsPubKey;
            return this;
        }
        
        public Builder setBlsProof(String blsProof) {
            this.blsProof = blsProof;
            return this;
        }

        public Builder setRewardPer(BigInteger rewardPer) {
            this.rewardPer = rewardPer;
            return this;
        }

        public StakingParam build() {
            return new StakingParam(this);
        }
    }
}
