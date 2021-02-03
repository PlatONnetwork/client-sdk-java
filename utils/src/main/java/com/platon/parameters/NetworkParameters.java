package com.platon.parameters;

import com.platon.bech32.Bech32;

import java.util.HashMap;
import java.util.Map;

public class NetworkParameters {
    protected long chainId;
    protected String hrp;

    protected static NetworkParameters currentNetwork;
    private static Map<String, NetworkParameters> networksContainer = new HashMap<>();
    private static String alayaNetworkKey = String.valueOf(ReservedChainId.Alaya.getChainId()) + ":" + ReservedHrp.Alaya.getHrp();
    private static String platonNetworkKey = String.valueOf(ReservedChainId.PlatON.getChainId()) + ":" + ReservedHrp.PlatON.getHrp();

    static {
        NetworkParameters alaya = new NetworkParameters(ReservedChainId.Alaya.getChainId(), ReservedHrp.Alaya.getHrp());
        networksContainer.put(alayaNetworkKey, alaya);
        //networksContainer.put(platonNetworkKey, new NetworkParameters(ReservedChainId.PlatON.getChainId(), ReservedHrp.PlatON.getHrp()));
        currentNetwork=alaya;
    }

    //锁仓合约地址
    protected String pposContractAddressOfRestrictingPlan;
    //staking合约地址
    protected String pposContractAddressOfStaking;
    //激励池地址
    protected String pposContractAddressOfIncentivePool;
    //惩罚合约地址
    protected String pposContractAddressOfSlash;
    //治理合约地址
    protected String pposContractAddressOfProposal;
    //委托收益合约地址
    protected String pposContractAddressOfReward;

    public static long getChainId() {
        return currentNetwork.chainId;
    }

    public static String getHrp() {
        return currentNetwork.hrp;
    }

    public static String getPposContractAddressOfRestrctingPlan() {
        return currentNetwork.pposContractAddressOfRestrictingPlan;
    }

    public static String getPposContractAddressOfStaking() {
        return currentNetwork.pposContractAddressOfStaking;
    }

    public static String getPposContractAddressOfIncentivePool() {
        return currentNetwork.pposContractAddressOfIncentivePool;
    }

    public static String getPposContractAddressOfSlash() {
        return currentNetwork.pposContractAddressOfSlash;
    }

    public static String getPposContractAddressOfProposal() {
        return currentNetwork.pposContractAddressOfProposal;
    }

    public static String getPposContractAddressOfReward() {
        return currentNetwork.pposContractAddressOfReward;
    }


    protected NetworkParameters(){
    }

    protected NetworkParameters(Long chainID, String hrp){
        this.chainId = chainID;
        this.hrp = hrp;

        this.pposContractAddressOfRestrictingPlan = Bech32.addressEncode(hrp, InnerContracts.getRestrictingAddr());
        this.pposContractAddressOfStaking = Bech32.addressEncode(hrp, InnerContracts.getStakingAddr());
        this.pposContractAddressOfIncentivePool = Bech32.addressEncode(hrp, InnerContracts.getRewardManagerPoolAddr());
        this.pposContractAddressOfSlash = Bech32.addressEncode(hrp, InnerContracts.getSlashingAddr());
        this.pposContractAddressOfProposal = Bech32.addressEncode(hrp, InnerContracts.getGovAddr());
        this.pposContractAddressOfReward = Bech32.addressEncode(hrp, InnerContracts.getDelegateRewardPoolAddr());
    }


    /**
     * init a custom network, and this network will be the current one.
     * @param chainId  chainId, it cannot be same as the Alaya or PlatON network's id.
     * @param hrp   hrp, , it cannot be same as the Alaya or PlatON network's hrp.
     */
    public static void init(long chainId, String hrp){
        if(networksContainer.containsKey(String.valueOf(chainId) + ":" + hrp)){
            return;
        }
        //if the chainID = 201018L, the hrp should be atp.
        if (chainId==ReservedChainId.Alaya.getChainId() && !ReservedHrp.Alaya.getHrp().equals(hrp)){
            throw new RuntimeException("hrp not match to chainID");
        }
        //todo: to add code to verify PlatON network

        if(Bech32.verifyHrp(hrp)){
            NetworkParameters network =  new NetworkParameters(chainId, hrp);
            networksContainer.put(String.valueOf(chainId) + ":" + hrp, network);
            currentNetwork = network;
        }else{
            throw new RuntimeException("hrp is invalid");
        }
    }

    /**
     * switch the current network
     * @param chainId the custom network's id
     * @param hrp the custom network's hrp
     */
    public static void selectNetwork(long chainId, String hrp){
        if(networksContainer.keySet().size()==1){
            //currentNetwork cannot switch to another if only one network has been initialized.
            return;
        }
        currentNetwork = networksContainer.get(String.valueOf(chainId) + ":" + hrp);
    }

    /**
     * switch to the Alaya network
     */
    public static void selectAlaya(){
        currentNetwork = networksContainer.get(alayaNetworkKey);
    }

    /**
     * switch to the PlatON network
     */
    public static void selectPlatON(){
        currentNetwork = networksContainer.get(platonNetworkKey);
    }

    public enum ReservedHrp {
        PlatON("lat"),
        Alaya("atp");

        private final String hrp;
        ReservedHrp(String hrp){
            this.hrp = hrp;
        }
        public String getHrp(){
            return this.hrp;
        }
    }

    public enum ReservedChainId {
        PlatON(103L),
        Alaya(201018L);

        private final long chainId;
        ReservedChainId(long chainId){
            this.chainId = chainId;
        }
        public long getChainId(){
            return this.chainId;
        }
    }
}
