package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.platon.FunctionType;
import org.web3j.platon.ProposalType;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Proposal {

    /**
     * 提案id
     */
    @JSONField(name = "ProposalId")
    protected String proposalId;
    /**
     * 提案节点ID
     */
    @JSONField(name = "Proposer")
    protected String proposer;
    /**
     * 提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案
     */
    @JSONField(name = "ProposalType")
    protected int proposalType;
    /**
     * 提交提案的块高
     */
    @JSONField(name = "SubmitBlock")
    protected BigInteger submitBlock;
    /**
     * 提案投票结束的块高
     */
    @JSONField(name = "EndVotingBlock")
    protected BigInteger endVotingBlock;
    /**
     * 升级版本
     */
    @JSONField(name = "NewVersion")
    private BigInteger newVersion;
    /**
     * （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
     */
    @JSONField(name = "ActiveBlock")
    private BigInteger activeBlock;
    /**
     * 提交提案的验证人
     */
    /**
     * 参数名称
     */
    @JSONField(name = "ParamName")
    private String paramName;
    /**
     * 当前值
     */
    @JSONField(name = "CurrentValue")
    private String currentValue;
    /**
     * 新的值
     */
    @JSONField(name = "NewValue")
    private String newValue;

    private String verifier;

    private String url;

    public Proposal() {
    }

    public Proposal(Builder builder) {
        this.proposalId = builder.proposalId;
        this.proposer = builder.proposer;
        this.proposalType = builder.proposalType;
        this.submitBlock = builder.submitBlock;
        this.endVotingBlock = builder.endVotingBlock;
        this.newVersion = builder.newVersion;
        this.activeBlock = builder.activeBlock;
        this.paramName = builder.paramName;
        this.currentValue = builder.currentValue;
        this.newValue = builder.newValue;
        this.verifier = builder.verifier;
        this.url = builder.url;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public int getProposalType() {
        return proposalType;
    }

    public void setProposalType(int proposalType) {
        this.proposalType = proposalType;
    }

    public BigInteger getSubmitBlock() {
        return submitBlock;
    }

    public void setSubmitBlock(BigInteger submitBlock) {
        this.submitBlock = submitBlock;
    }

    public BigInteger getEndVotingBlock() {
        return endVotingBlock;
    }

    public void setEndVotingBlock(BigInteger endVotingBlock) {
        this.endVotingBlock = endVotingBlock;
    }

    public BigInteger getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(BigInteger newVersion) {
        this.newVersion = newVersion;
    }

    public BigInteger getActiveBlock() {
        return activeBlock;
    }

    public void setActiveBlock(BigInteger activeBlock) {
        this.activeBlock = activeBlock;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Type> getSubmitInputParameters() {
        if (proposalType== ProposalType.TEXT_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.url),
                    new Uint64(this.endVotingBlock));
        } else if (proposalType == ProposalType.VERSION_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.url),
                    new Uint16(this.newVersion),
                    new Uint64(this.endVotingBlock),
                    new Uint64(this.activeBlock));
        } else {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.url),
                    new Uint64(this.endVotingBlock),
                    new Utf8String(this.paramName),
                    new Utf8String(this.currentValue),
                    new Utf8String(this.newValue));
        }
    }

    public int getSubmitFunctionType() {
        if (proposalType == ProposalType.TEXT_PROPOSAL) {
            return FunctionType.SUBMIT_TEXT_FUNC_TYPE;
        } else if (proposalType == ProposalType.VERSION_PROPOSAL) {
            return FunctionType.SUBMIT_VERSION_FUNC_TYPE;
        } else {
            return FunctionType.SUBMIR_PARAM_FUNCTION_TYPE;
        }
    }


    public static final class Builder {
        private String proposalId;
        private String proposer;
        private int proposalType;
        private BigInteger submitBlock;
        private BigInteger endVotingBlock;
        private BigInteger newVersion;
        private BigInteger activeBlock;
        private String paramName;
        private String currentValue;
        private String newValue;
        private String verifier;
        private String url;

        public Builder(int proposalType) {
            this.proposalType = proposalType;
        }

        public Builder setProposalId(String proposalId) {
            this.proposalId = proposalId;
            return this;
        }

        public Builder setProposer(String proposer) {
            this.proposer = proposer;
            return this;
        }

        public Builder setSubmitBlock(BigInteger submitBlock) {
            this.submitBlock = submitBlock;
            return this;
        }

        public Builder setEndVotingBlock(BigInteger endVotingBlock) {
            this.endVotingBlock = endVotingBlock;
            return this;
        }

        public Builder setNewVersion(BigInteger newVersion) {
            this.newVersion = newVersion;
            return this;
        }

        public Builder setActiveBlock(BigInteger activeBlock) {
            this.activeBlock = activeBlock;
            return this;
        }

        public Builder setParamName(String paramName) {
            this.paramName = paramName;
            return this;
        }

        public Builder setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public Builder setNewValue(String newValue) {
            this.newValue = newValue;
            return this;
        }

        public Builder setVerifier(String verifier) {
            this.verifier = verifier;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Proposal build() {
            return new Proposal(this);
        }
    }
}
