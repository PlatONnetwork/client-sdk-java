package org.web3j.platon.bean;

import org.web3j.abi.datatypes.Type;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpType;

import java.math.BigInteger;
import java.util.List;

public abstract class Proposal {

    /**
     * 提案id
     */
    protected String proposalId;
    /**
     * 提交提案的验证人
     */
    protected String verifier;
    /**
     * 提案在github上的id
     */
    protected String githubId;
    /**
     * 提案主题，长度不超过128
     */
    protected String topic;
    /**
     * 提案描述，长度不超过512
     */
    protected String desc;
    /**
     * 提案URL，长度不超过512
     */
    protected String url;
    /**
     * 提案投票截止块高（250N-20），不超过2周的块高
     */
    protected BigInteger endVoltingBlock;

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigInteger getEndVoltingBlock() {
        return endVoltingBlock;
    }

    public void setEndVoltingBlock(BigInteger endVoltingBlock) {
        this.endVoltingBlock = endVoltingBlock;
    }

    public abstract List<Type> getSubmitInputParameters();

    public abstract int getSubmitFunctionType();

}
