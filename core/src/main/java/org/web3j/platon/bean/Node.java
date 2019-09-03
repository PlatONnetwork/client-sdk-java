package org.web3j.platon.bean;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

import com.alibaba.fastjson.annotation.JSONField;

public class Node {

    @JSONField(name = "NodeId")
    private String nodeId;

    @JSONField(name = "StakingAddress")
    private String stakingAddress;

    @JSONField(name = "BenefitAddress")
    private String benifitAddress;

    @JSONField(name = "StakingTxIndex")
    private BigInteger stakingTxIndex;

    @JSONField(name = "ProgramVersion")
    private BigInteger programVersion;

    @JSONField(name = "Status")
    private BigInteger status;

    @JSONField(name = "StakingEpoch")
    private BigInteger stakingEpoch;

    @JSONField(name = "StakingBlockNum")
    private BigInteger stakingBlockNum;

    @JSONField(name = "Shares")
    private BigInteger shares;

    @JSONField(name = "Released")
    private BigInteger released;

    @JSONField(name = "ReleasedHes")
    private BigInteger releasedHes;

    @JSONField(name = "RestrictingPlan")
    private BigInteger restrictingPlan;

    @JSONField(name = "RestrictingPlanHes")
    private BigInteger restrictingPlanHes;

    @JSONField(name = "ExternalId")
    private String externalId;

    @JSONField(name = "NodeName")
    private String nodeName;

    @JSONField(name = "Website")
    private String website;

    @JSONField(name = "Details")
    private String details;

    @JSONField(name = "ValidatorTerm")
    private BigInteger validatorTerm;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getStakingAddress() {
        return stakingAddress;
    }

    public void setStakingAddress(String stakingAddress) {
        this.stakingAddress = stakingAddress;
    }

    public String getBenifitAddress() {
        return benifitAddress;
    }

    public void setBenifitAddress(String benifitAddress) {
        this.benifitAddress = benifitAddress;
    }

    public BigInteger getStakingTxIndex() {
        return stakingTxIndex;
    }

    public void setStakingTxIndex(BigInteger stakingTxIndex) {
        this.stakingTxIndex = stakingTxIndex;
    }

    public BigInteger getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(BigInteger programVersion) {
        this.programVersion = programVersion;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public BigInteger getStakingEpoch() {
        return stakingEpoch;
    }

    public void setStakingEpoch(BigInteger stakingEpoch) {
        this.stakingEpoch = stakingEpoch;
    }

    public BigInteger getStakingBlockNum() {
        return stakingBlockNum;
    }

    public void setStakingBlockNum(BigInteger stakingBlockNum) {
        this.stakingBlockNum = stakingBlockNum;
    }

    public BigInteger getShares() {
        return shares;
    }

    public void setShares(String shares) {
      	if(shares != null && shares.length()>0) {
      		this.shares = Numeric.decodeQuantity(shares);
    	}else {
    		this.shares = BigInteger.ZERO;
		}
    }

    public BigInteger getReleased() {
        return released;
    }

    public void setReleased(String released) {
      	if(released != null && released.length()>0) {
      		this.released = Numeric.decodeQuantity(released);
      	}else {
      		this.released = BigInteger.ZERO;
		}
    }

    public BigInteger getReleasedHes() {
        return releasedHes;
    }

    public void setReleasedHes(String releasedHes) {
      	if(releasedHes != null && releasedHes.length()>0) {
      		this.releasedHes = Numeric.decodeQuantity(releasedHes);
      	}else {
      		this.releasedHes = BigInteger.ZERO;
		}
    }

    public BigInteger getRestrictingPlan() {
        return restrictingPlan;
    }

    public void setRestrictingPlan(String restrictingPlan) {
      	if(restrictingPlan != null && restrictingPlan.length()>0) {
      		this.restrictingPlan = Numeric.decodeQuantity(restrictingPlan);
      	}else {
      		this.restrictingPlan = BigInteger.ZERO;
		}
    }

    public BigInteger getRestrictingPlanHes() {
        return restrictingPlanHes;
    }

    public void setRestrictingPlanHes(String restrictingPlanHes) {
      	if(restrictingPlanHes != null && restrictingPlanHes.length()>0) {
      		this.restrictingPlanHes = Numeric.decodeQuantity(restrictingPlanHes);
      	}else {
      		this.restrictingPlanHes = BigInteger.ZERO;
		}
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigInteger getValidatorTerm() {
        return validatorTerm;
    }

    public void setValidatorTerm(BigInteger validatorTerm) {
        this.validatorTerm = validatorTerm;
    }

    public Node() {
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId='" + nodeId + '\'' +
                ", stakingAddress='" + stakingAddress + '\'' +
                ", benifitAddress='" + benifitAddress + '\'' +
                ", stakingTxIndex=" + stakingTxIndex +
                ", programVersion=" + programVersion +
                ", status=" + status +
                ", stakingEpoch=" + stakingEpoch +
                ", stakingBlockNum=" + stakingBlockNum +
                ", shares=" + shares +
                ", released=" + released +
                ", releasedHes=" + releasedHes +
                ", restrictingPlan=" + restrictingPlan +
                ", restrictingPlanHes=" + restrictingPlanHes +
                ", externalId='" + externalId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", website='" + website + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
