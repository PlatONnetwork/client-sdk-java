package org.web3j;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.platon.VoteOption;
import org.web3j.platon.contracts.DelegateContract;
import org.web3j.platon.contracts.NodeContract;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.platon.contracts.RestrictingPlanContract;
import org.web3j.platon.contracts.SlashContract;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


public abstract class Scenario {
	
	protected Logger logger = LoggerFactory.getLogger(Scenario.class);

	protected Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));
	protected Web3j proposalWeb3j = Web3j.build(new HttpService("http://192.168.120.144:6789"));
	protected Web3j slashWeb3j = proposalWeb3j;
	protected String chainId = "103";
	protected String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
	protected String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
	protected String proposalNodeId = "fb886b3da4cf875f7d85e820a9b39df2170fd1966ffa0ddbcd738027f6f8e0256204e4873a2569ef299b324da3d0ed1afebb160d8ff401c2f09e20fb699e4005";


	protected Credentials superCredentials  = Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
	protected Credentials stakingCredentials = Credentials.create("0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
	protected Credentials benefitCredentials =  Credentials.create("0x3581985348bffd03b286b37712165f7addf3a8d907b25efc44addf54117e9b91");
	protected Credentials delegateCredentials = Credentials.create("0xda8e68e664b8cfb6cdf1a4609eea0452d717bc7f1a48b52bb5b94453877ee8bb");
	protected Credentials restrictingSendCredentials = Credentials.create("0xb497206fa3a26df12b976fa05401ea51472d8bb8bdb4da1d4aa1e428ba4c93c0");
	protected Credentials restrictingRecvCredentials = Credentials.create("0x418c6e7be993e8b19831ad10397968596c2c614bc98a185a3d3776b53a50f079");
	protected Credentials proposalCredentials =  Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
	protected Credentials voteCredentials =  Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
	protected Credentials slashCredentials = proposalCredentials;

	protected StakingContract stakingContract = StakingContract.load( web3j,stakingCredentials, chainId);
	protected DelegateContract delegateContract = DelegateContract.load( web3j,delegateCredentials, chainId);
	protected RestrictingPlanContract restrictingPlanContract = RestrictingPlanContract.load(web3j, restrictingSendCredentials, chainId);
	protected NodeContract nodeContract = NodeContract.load(web3j);
	protected ProposalContract proposalContract = ProposalContract.load(proposalWeb3j,proposalCredentials, chainId);
	protected SlashContract slashContract = SlashContract.load(slashWeb3j, slashCredentials, chainId);

	protected List<VoteInfo> voteInfos = new ArrayList<>();

	{
		voteInfos.add(createVoteInfo("http://192.168.120.141:6789", "0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47", VoteOption.YEAS));
		voteInfos.add(createVoteInfo("http://192.168.120.142:6789", "e0b6af6cc2e10b2b74540b87098083d48343805a3ff09c655eab0b20dba2b2851aea79ee75b6e150bde58ead0be03ee4a8619ea1dfaf529cbb8ff55ca23531ed", VoteOption.NAYS));
	}
    
    private VoteInfo createVoteInfo(String nodeHost, String nodeId, VoteOption voteOption) {
      	Web3j web3j =  Web3j.build(new HttpService(nodeHost));
      	ProposalContract voteContract = ProposalContract.load(web3j, voteCredentials, chainId);
      	return new VoteInfo(voteContract, nodeId, voteOption);
    }
    
    protected class VoteInfo{
    	private ProposalContract voteContract;
    	private String nodeId;
    	private VoteOption voteOption;
		
    	public VoteInfo() {
    		
    	}
    	
    	public VoteInfo(ProposalContract voteContract, String nodeId, VoteOption voteOption) {
			super();
			this.voteContract = voteContract;
			this.nodeId = nodeId;
			this.voteOption = voteOption;
		}
    	
		public ProposalContract getVoteContract() {
			return voteContract;
		}
		public void setVoteContract(ProposalContract voteContract) {
			this.voteContract = voteContract;
		}
		public String getNodeId() {
			return nodeId;
		}
		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}
		public VoteOption getVoteOption() {
			return voteOption;
		}
		public void setVoteOption(VoteOption voteOption) {
			this.voteOption = voteOption;
		}
    }
}
