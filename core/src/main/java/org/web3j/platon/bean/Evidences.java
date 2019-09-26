package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Evidences {

    @JSONField(name = "duplicatePrepare")
    private List<String> duplicatePrepare;
    @JSONField(name = "duplicateVote")
    private List<String> duplicateVote;
    @JSONField(name = "duplicateViewchange")
    private List<String> duplicateViewChange;

    public Evidences() {
    }

    public List<String> getDuplicatePrepare() {
        return duplicatePrepare;
    }

    public void setDuplicatePrepare(List<String> duplicatePrepare) {
        this.duplicatePrepare = duplicatePrepare;
    }

    public List<String> getDuplicateVote() {
        return duplicateVote;
    }

    public void setDuplicateVote(List<String> duplicateVote) {
        this.duplicateVote = duplicateVote;
    }

    public List<String> getDuplicateViewChange() {
        return duplicateViewChange;
    }

    public void setDuplicateViewChange(List<String> duplicateViewChange) {
        this.duplicateViewChange = duplicateViewChange;
    }

    public int getEvidencesSize() {
        int duplicatePrepareSize = duplicatePrepare != null ? duplicatePrepare.size() : 0;
        int duplicateVoteSize = duplicateVote != null ? duplicateVote.size() : 0;
        int dulicateViewChangeSize = duplicateViewChange != null ? duplicateViewChange.size() : 0;
        return duplicatePrepareSize + duplicateVoteSize + dulicateViewChangeSize;
    }

    @Override
    public String toString() {
        return "Evidences{" +
                "duplicatePrepare=" + duplicatePrepare +
                ", duplicateVote=" + duplicateVote +
                ", duplicateViewChange=" + duplicateViewChange +
                '}';
    }
}
