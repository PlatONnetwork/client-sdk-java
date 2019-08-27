package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Evidences {

    @JSONField(name = "duplicate_prepare")
    private List<DuplicatePrepare> duplicatePrepare;
    @JSONField(name = "duplicate_vote")
    private List<DuplicateVote> duplicateVote;
    @JSONField(name = "duplicate_viewchange")
    private List<DulicateViewChange> duplicateViewChange;

    public Evidences() {
    }

    public List<DuplicatePrepare> getDuplicatePrepare() {
        return duplicatePrepare;
    }

    public void setDuplicatePrepare(List<DuplicatePrepare> duplicatePrepare) {
        this.duplicatePrepare = duplicatePrepare;
    }

    public List<DuplicateVote> getDuplicateVote() {
        return duplicateVote;
    }

    public void setDuplicateVote(List<DuplicateVote> duplicateVote) {
        this.duplicateVote = duplicateVote;
    }

    public List<DulicateViewChange> getDuplicateViewChange() {
        return duplicateViewChange;
    }

    public void setDuplicateViewChange(List<DulicateViewChange> duplicateViewChange) {
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
