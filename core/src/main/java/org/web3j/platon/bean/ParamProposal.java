package org.web3j.platon.bean;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.platon.FunctionType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class ParamProposal extends Proposal {
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 当前值
     */
    private String currentValue;
    /**
     * 新的值
     */
    private String newValue;

    public ParamProposal(ParamProposal.Builder builder) {
        this.proposalId = builder.proposalId;
        this.verifier = builder.verifier;
        this.githubId = builder.githubId;
        this.topic = builder.topic;
        this.desc = builder.desc;
        this.url = builder.url;
        this.endVoltingBlock = builder.endVoltingBlock;
        this.paramName = builder.paramName;
        this.currentValue = builder.currentValue;
        this.newValue = builder.newValue;
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

    @Override
    public List<Type> getSubmitInputParameters() {
        return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                new Utf8String(this.githubId),
                new Utf8String(this.topic),
                new Utf8String(this.desc),
                new Utf8String(this.url),
                new Uint64(this.endVoltingBlock),
                new Utf8String(this.paramName),
                new Utf8String(this.currentValue),
                new Utf8String(this.newValue));
    }

    @Override
    public int getSubmitFunctionType() {
        return FunctionType.SUBMIR_PARAM_FUNCTION_TYPE;
    }

    public static final class Builder {
        private String proposalId;
        private String verifier;
        private String githubId;
        private String topic;
        private String desc;
        private String url;
        private BigInteger endVoltingBlock;
        private String paramName;
        private String currentValue;
        private String newValue;

        public ParamProposal.Builder setProposalId(String proposalId) {
            this.proposalId = proposalId;
            return this;
        }

        public ParamProposal.Builder setVerifier(String verifier) {
            this.verifier = verifier;
            return this;
        }

        public ParamProposal.Builder setGithubId(String githubId) {
            this.githubId = githubId;
            return this;
        }

        public ParamProposal.Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public ParamProposal.Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public ParamProposal.Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ParamProposal.Builder setEndVoltingBlock(BigInteger endVoltingBlock) {
            this.endVoltingBlock = endVoltingBlock;
            return this;
        }

        public ParamProposal.Builder setParamName(String paramName) {
            this.paramName = paramName;
            return this;
        }

        public ParamProposal.Builder setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public ParamProposal.Builder setNewValue(String newValue) {
            this.newValue = newValue;
            return this;
        }


        public ParamProposal build() {
            return new ParamProposal(this);
        }
    }


}
