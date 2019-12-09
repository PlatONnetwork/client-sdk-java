package com.platon.sdk.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.common.ProposalType;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Proposal {

    /**
     * 提案id
     */
    @JSONField(name = "ProposalID")
    private String proposalId;
    /**
     * 提案节点ID
     */
    @JSONField(name = "Proposer")
    private String proposer;
    /**
     * 提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案
     */
    @JSONField(name = "ProposalType")
    private int proposalType;
    /**
     * 提案PIPID
     */
    @JSONField(name = "PIPID")
    private String piPid;
    /**
     * 提交提案的块高
     */
    @JSONField(name = "SubmitBlock")
    private BigInteger submitBlock;
    /**
     * 提案投票结束的块高
     */
    @JSONField(name = "EndVotingBlock")
    private BigInteger endVotingBlock;
    /**
     * 升级版本
     */
    @JSONField(name = "NewVersion")
    private BigInteger newVersion;
    /**
     * 提案要取消的升级提案ID
     */
    @JSONField(name = "TobeCanceled")
    private String toBeCanceled;
    /**
     * （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
     */
    @JSONField(name = "ActiveBlock")
    private BigInteger activeBlock;

    /**
     * 提交提案的验证人
     */
    private String verifier;
    /**
     * 参数模块
     */
    private String module;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数新值
     */
    private String newValue;

    public Proposal() {

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

    public String getPiPid() {
        return piPid;
    }

    public void setPiPid(String piPid) {
        this.piPid = piPid;
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

    public String getToBeCanceled() {
        return toBeCanceled;
    }

    public void setToBeCanceled(String toBeCanceled) {
        this.toBeCanceled = toBeCanceled;
    }

    public BigInteger getActiveBlock() {
        return activeBlock;
    }

    public void setActiveBlock(BigInteger activeBlock) {
        this.activeBlock = activeBlock;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Proposal(Builder builder) {
        this.proposalId = builder.proposalId;
        this.proposer = builder.proposer;
        this.proposalType = builder.proposalType;
        this.piPid = builder.piPid;
        this.submitBlock = builder.submitBlock;
        this.endVotingBlock = builder.endVotingBlock;
        this.newVersion = builder.newVersion;
        this.toBeCanceled = builder.toBeCanceled;
        this.activeBlock = builder.activeBlock;
        this.verifier = builder.verifier;
        this.module = builder.module;
        this.name = builder.name;
        this.newValue = builder.newValue;
    }


    public List<Type> getSubmitInputParameters() {
        if (proposalType == ProposalType.TEXT_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.piPid));
        } else if (proposalType == ProposalType.VERSION_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.piPid),
                    new Uint32(this.newVersion),
                    new Uint64(this.endVotingBlock));
        } else if (proposalType == ProposalType.CANCEL_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.piPid),
                    new Uint64(this.endVotingBlock),
                    new BytesType(Numeric.hexStringToByteArray(this.toBeCanceled)));
        } else if (proposalType == ProposalType.PARAM_PROPOSAL) {
            return Arrays.asList(new BytesType(Numeric.hexStringToByteArray(this.verifier)),
                    new Utf8String(this.piPid),
                    new Utf8String(this.module),
                    new Utf8String(this.name),
                    new Utf8String(this.newValue));
        }

        return new ArrayList<>();
    }

    public int getSubmitFunctionType() {
        if (proposalType == ProposalType.TEXT_PROPOSAL) {
            return FunctionType.SUBMIT_TEXT_FUNC_TYPE;
        } else if (proposalType == ProposalType.VERSION_PROPOSAL) {
            return FunctionType.SUBMIT_VERSION_FUNC_TYPE;
        } else if (proposalType == ProposalType.CANCEL_PROPOSAL) {
            return FunctionType.SUBMIT_CANCEL_FUNC_TYPE;
        } else {
            return FunctionType.SUBMIR_PARAM_FUNCTION_TYPE;
        }
    }

    public static Proposal createSubmitTextProposalParam(String verifier, String pIDID) {
        return new Proposal.Builder()
                .setProposalType(ProposalType.TEXT_PROPOSAL)
                .setVerifier(verifier)
                .setPiPid(pIDID)
                .build();
    }

    public static Proposal createSubmitVersionProposalParam(String verifier, String pIDID, BigInteger newVersion, BigInteger endVotingRounds) {
        return new Proposal.Builder()
                .setProposalType(ProposalType.VERSION_PROPOSAL)
                .setVerifier(verifier)
                .setPiPid(pIDID)
                .setNewVersion(newVersion)
                .setEndVotingBlock(endVotingRounds)
                .build();
    }

    public static Proposal createSubmitCancelProposalParam(String verifier, String pIDID, BigInteger endVotingRounds, String tobeCanceledProposalID) {
        return new Proposal.Builder()
                .setProposalType(ProposalType.CANCEL_PROPOSAL)
                .setVerifier(verifier)
                .setPiPid(pIDID)
                .setEndVotingBlock(endVotingRounds)
                .setToBeCanceled(tobeCanceledProposalID)
                .build();
    }

    public static Proposal createSubmitParamProposalParam(String verifier, String pIDID, String module, String name, String newValue) {

        return new Proposal.Builder()
                .setProposalType(ProposalType.PARAM_PROPOSAL)
                .setVerifier(verifier)
                .setPiPid(pIDID)
                .setModule(module)
                .setName(name)
                .setNewValue(newValue)
                .build();
    }

    static final class Builder {
        private String proposalId;
        private String proposer;
        private int proposalType;
        private String piPid;
        private BigInteger submitBlock;
        private BigInteger endVotingBlock;
        private BigInteger newVersion;
        private String toBeCanceled;
        private BigInteger activeBlock;
        private String verifier;
        private String module;
        private String name;
        private String newValue;

        public Builder setProposalId(String proposalId) {
            this.proposalId = proposalId;
            return this;
        }

        public Builder setProposer(String proposer) {
            this.proposer = proposer;
            return this;
        }

        public Builder setProposalType(int proposalType) {
            this.proposalType = proposalType;
            return this;
        }

        public Builder setPiPid(String piPid) {
            this.piPid = piPid;
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

        public Builder setToBeCanceled(String toBeCanceled) {
            this.toBeCanceled = toBeCanceled;
            return this;
        }

        public Builder setActiveBlock(BigInteger activeBlock) {
            this.activeBlock = activeBlock;
            return this;
        }

        public Builder setVerifier(String verifier) {
            this.verifier = verifier;
            return this;
        }

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNewValue(String newValue) {
            this.newValue = newValue;
            return this;
        }

        public Proposal build() {
            return new Proposal(this);
        }
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "proposalId='" + proposalId + '\'' +
                ", proposer='" + proposer + '\'' +
                ", proposalType=" + proposalType +
                ", piPid='" + piPid + '\'' +
                ", submitBlock=" + submitBlock +
                ", endVotingBlock=" + endVotingBlock +
                ", newVersion=" + newVersion +
                ", toBeCanceled='" + toBeCanceled + '\'' +
                ", activeBlock=" + activeBlock +
                ", verifier='" + verifier + '\'' +
                '}';
    }
}
