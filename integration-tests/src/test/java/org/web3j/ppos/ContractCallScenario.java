package org.web3j.ppos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.web3j.Scenario;

import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Node;

public class ContractCallScenario extends Scenario {
	
    @Test
    public void getValidatorList() throws Exception {
    	CallResponse<List<Node>> response = nodeContract.getValidatorList().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }
    
    @Test
    public void getVerifierList() throws Exception {
    	CallResponse<List<Node>> response = nodeContract.getVerifierList().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }    	
    
    @Test
    public void getCandidateList() throws Exception {
    	CallResponse<List<Node>> response = nodeContract.getCandidateList().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }    
    
    @Test
    public void getPackageReward() throws Exception {
    	CallResponse<BigInteger> response = stakingContract.getPackageReward().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }   
    
    @Test
    public void getStakingReward() throws Exception {
    	CallResponse<BigInteger> response = stakingContract.getStakingReward().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }   
    
    @Test
    public void getAvgPackTime() throws Exception {
    	CallResponse<BigInteger> response = stakingContract.getAvgPackTime().send();
    	assertThat(response.isStatusOk(), equalTo(true));
    	System.out.println(response);
    }   
}
