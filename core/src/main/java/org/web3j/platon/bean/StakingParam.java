package org.web3j.platon.bean;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.platon.StakingAmountType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

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
     * 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     */
    private StakingAmountType stakingAmountType;
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
    private BigInteger processVersion;

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

    public BigInteger getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(BigInteger processVersion) {
        this.processVersion = processVersion;
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
    }

    public List<Type> getSubmitInputParameters() {
        return Arrays.<Type>asList(new Uint16(stakingAmountType.getValue())
                , new BytesType(Numeric.hexStringToByteArray(benifitAddress))
                , new BytesType(Numeric.hexStringToByteArray(nodeId))
                , new Utf8String(externalId)
                , new Utf8String(nodeName)
                , new Utf8String(webSite)
                , new Utf8String(details)
                , new Int256(amount)
                , new Uint32(processVersion));
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
        private BigInteger processVersion;

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

        public Builder setProcessVersion(BigInteger processVersion) {
            this.processVersion = processVersion;
            return this;
        }

        public StakingParam build() {
            return new StakingParam(this);
        }
    }
}
