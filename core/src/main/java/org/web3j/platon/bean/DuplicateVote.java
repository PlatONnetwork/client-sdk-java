package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class DuplicateVote {

    @JSONField(name = "VoteA")
    private DuplicateView voteA;
    @JSONField(name = "VoteB")
    private DuplicateView voteB;

    public DuplicateVote() {
    }

    public DuplicateView getVoteA() {
        return voteA;
    }

    public void setVoteA(DuplicateView voteA) {
        this.voteA = voteA;
    }

    public DuplicateView getVoteB() {
        return voteB;
    }

    public void setVoteB(DuplicateView voteB) {
        this.voteB = voteB;
    }
}
