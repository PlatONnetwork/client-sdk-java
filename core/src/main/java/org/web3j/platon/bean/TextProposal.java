package org.web3j.platon.bean;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.platon.FunctionType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 文本提案
 */
public class TextProposal extends Proposal {

    public TextProposal(Builder builder) {
        this.proposalId = builder.proposalId;
        this.verifier = builder.verifier;
        this.githubId = builder.githubId;
        this.topic = builder.topic;
        this.desc = builder.desc;
        this.url = builder.url;
        this.endVoltingBlock = builder.endVoltingBlock;
    }

    @Override
    public List<Type> getSubmitInputParameters() {
        return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                new Utf8String(this.githubId),
                new Utf8String(this.topic),
                new Utf8String(this.desc),
                new Utf8String(this.url),
                new Uint64(this.endVoltingBlock));
    }

    @Override
    public int getSubmitFunctionType() {
        return FunctionType.SUBMIT_TEXT_FUNC_TYPE;
    }

    public static final class Builder {
        private String proposalId;
        private String verifier;
        private String githubId;
        private String topic;
        private String desc;
        private String url;
        private BigInteger endVoltingBlock;

        public Builder setProposalId(String proposalId) {
            this.proposalId = proposalId;
            return this;
        }

        public Builder setVerifier(String verifier) {
            this.verifier = verifier;
            return this;
        }

        public Builder setGithubId(String githubId) {
            this.githubId = githubId;
            return this;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setEndVoltingBlock(BigInteger endVoltingBlock) {
            this.endVoltingBlock = endVoltingBlock;
            return this;
        }

        public TextProposal build() {
            return new TextProposal(this);
        }
    }
}
