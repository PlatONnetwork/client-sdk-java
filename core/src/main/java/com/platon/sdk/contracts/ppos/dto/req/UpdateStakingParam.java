package com.platon.sdk.contracts.ppos.dto.req;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class UpdateStakingParam {

    /**
     * 64bytes 被质押的节点Id(也叫候选人的节点Id)
     */
    private String nodeId;
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
     * 奖励分成比例，采用BasePoint 1BP=0.01%
     */
    private BigInteger rewardPer;

    public UpdateStakingParam(Builder builder) {
        this.nodeId = builder.nodeId;
        this.benifitAddress = builder.benifitAddress;
        this.externalId = builder.externalId;
        this.nodeName = builder.nodeName;
        this.webSite = builder.webSite;
        this.details = builder.details;
        this.rewardPer = builder.rewardPer;
    }

    public List<Type> getSubmitInputParameters() {
        return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(benifitAddress)),
                new BytesType(Numeric.hexStringToByteArray(nodeId)),
                new Uint16(rewardPer),
                new Utf8String(externalId),
                new Utf8String(nodeName),
                new Utf8String(webSite),
                new Utf8String(details));
    }


    public static final class Builder {
        private String nodeId;
        private String benifitAddress;
        private String externalId;
        private String nodeName;
        private String webSite;
        private String details;
        private BigInteger rewardPer;

        public Builder setNodeId(String nodeId) {
            this.nodeId = nodeId;
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

        public Builder setRewardPer(BigInteger rewardPer) {
            this.rewardPer = rewardPer;
            return this;
        }

        public UpdateStakingParam build() {
            return new UpdateStakingParam(this);
        }
    }
}
