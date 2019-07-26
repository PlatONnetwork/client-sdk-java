package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

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

    public void setShares(BigInteger shares) {
        this.shares = shares;
    }

    public BigInteger getReleased() {
        return released;
    }

    public void setReleased(BigInteger released) {
        this.released = released;
    }

    public BigInteger getReleasedHes() {
        return releasedHes;
    }

    public void setReleasedHes(BigInteger releasedHes) {
        this.releasedHes = releasedHes;
    }

    public BigInteger getRestrictingPlan() {
        return restrictingPlan;
    }

    public void setRestrictingPlan(BigInteger restrictingPlan) {
        this.restrictingPlan = restrictingPlan;
    }

    public BigInteger getRestrictingPlanHes() {
        return restrictingPlanHes;
    }

    public void setRestrictingPlanHes(BigInteger restrictingPlanHes) {
        this.restrictingPlanHes = restrictingPlanHes;
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
