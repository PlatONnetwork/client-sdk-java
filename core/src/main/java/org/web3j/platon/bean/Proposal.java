package org.web3j.platon.bean;

import org.web3j.abi.datatypes.Type;

import java.math.BigInteger;
import java.util.List;

public abstract class Proposal {

    /**
     * 提交提案的验证人
     */
    protected String verifier;
    /**
     * 提案URL，长度不超过512
     */
    protected String url;
    /**
     * 提案投票截止块高（250N-20），不超过2周的块高
     */
    protected BigInteger endVoltingBlock;

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

    public BigInteger getEndVoltingBlock() {
        return endVoltingBlock;
    }

    public void setEndVoltingBlock(BigInteger endVoltingBlock) {
        this.endVoltingBlock = endVoltingBlock;
    }

    public abstract List<Type> getSubmitInputParameters();

    public abstract int getSubmitFunctionType();

}
