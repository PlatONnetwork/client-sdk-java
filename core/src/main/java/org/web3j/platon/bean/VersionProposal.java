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

public class VersionProposal extends Proposal {

    /**
     * 升级版本
     */
    private BigInteger newVersion;
    /**
     * （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
     */
    private BigInteger activeBlock;

    public VersionProposal(Builder builder) {
        this.verifier = builder.verifier;
        this.url = builder.url;
        this.newVersion = builder.newVersion;
        this.endVoltingBlock = builder.endVoltingBlock;
        this.activeBlock = builder.activeBlock;
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

    @Override
    public List<Type> getSubmitInputParameters() {

        return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                new Utf8String(this.url),
                new Uint16(this.newVersion),
                new Uint64(this.endVoltingBlock),
                new Uint64(this.activeBlock));
    }

    @Override
    public int getSubmitFunctionType() {
        return FunctionType.SUBMIT_VERSION_FUNC_TYPE;
    }

    public static final class Builder {
        private String verifier;
        private String url;
        private BigInteger newVersion;
        private BigInteger endVoltingBlock;
        private BigInteger activeBlock;

        public Builder setVerifier(String verifier) {
            this.verifier = verifier;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setNewVersion(BigInteger newVersion) {
            this.newVersion = newVersion;
            return this;
        }

        public Builder setEndVoltingBlock(BigInteger endVoltingBlock) {
            this.endVoltingBlock = endVoltingBlock;
            return this;
        }

        public Builder setActiveBlock(BigInteger activeBlock) {
            this.activeBlock = activeBlock;
            return this;
        }

        public VersionProposal build() {
            return new VersionProposal(this);
        }
    }
}
