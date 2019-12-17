package com.platon.sdk.contracts.ppos.dto.resp;

import java.math.BigInteger;

/**
 * 投票结果
 */
public class TallyResult {
    /**
     * 提案ID
     */
    private String proposalID;
    /**
     * 赞成票
     */
    private BigInteger yeas;
    /**
     * 反对票
     */
    private BigInteger nays;
    /**
     * 弃权票
     */
    private BigInteger abstentions;
    /**
     * 在整个投票期内有投票资格的验证人总数
     */
    private BigInteger accuVerifiers;
    /**
     * 状态
     */
    private int status;

    public TallyResult() {
    }

    public String getProposalID() {
        return proposalID;
    }

    public void setProposalID(String proposalID) {
        this.proposalID = proposalID;
    }

    public BigInteger getYeas() {
        return yeas;
    }

    public void setYeas(BigInteger yeas) {
        this.yeas = yeas;
    }

    public BigInteger getNays() {
        return nays;
    }

    public void setNays(BigInteger nays) {
        this.nays = nays;
    }

    public BigInteger getAbstentions() {
        return abstentions;
    }

    public void setAbstentions(BigInteger abstentions) {
        this.abstentions = abstentions;
    }

    public BigInteger getAccuVerifiers() {
        return accuVerifiers;
    }

    public void setAccuVerifiers(BigInteger accuVerifiers) {
        this.accuVerifiers = accuVerifiers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TallyResult{" +
                "proposalID='" + proposalID + '\'' +
                ", yeas=" + yeas +
                ", nays=" + nays +
                ", abstentions=" + abstentions +
                ", accuVerifiers=" + accuVerifiers +
                ", status=" + status +
                '}';
    }
}
