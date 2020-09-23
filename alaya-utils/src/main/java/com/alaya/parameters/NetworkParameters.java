package com.alaya.parameters;

public class NetworkParameters {

    public static NetworkParameters CurrentNetwork;
    public final static NetworkParameters MainNetParams;
    public final static NetworkParameters TestNetParams;

    static {
        MainNetParams = new NetworkParameters(Hrp.ATP.getHrp(),
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqp7pn3ep",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzsjx8h7",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqrdyjj2v",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqyva9ztf",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq93t3hkm",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqxlcypcy",
                100);
        TestNetParams = createTestNetParams(102);
        CurrentNetwork = MainNetParams;
    }

    public static void setCurrentNetwork(long chainId){
        CurrentNetwork =  createTestNetParams(chainId);
    }

    public static NetworkParameters createTestNetParams(long chainId){
        return new NetworkParameters(Hrp.ATX.getHrp() ,
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqp3yp7hw",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzlh5ge3",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqrzpqayr",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqyrchd9x",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq97wrcc5",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqxsakwkt",
                chainId);
    }

    public static String getHrp(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getHrp();
        }else {
            return NetworkParameters.TestNetParams.getHrp();
        }
    }

    public static String getPposContractAddressOfRestrctingPlan(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfRestrctingPlan();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfRestrctingPlan();
        }
    }

    public static String getPposContractAddressOfStaking(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfStaking();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfStaking();
        }
    }

    public static String getPposContractAddressOfIncentivePool(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfIncentivePool();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfIncentivePool();
        }
    }

    public static String getPposContractAddressOfSlash(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfSlash();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfSlash();
        }
    }

    public static String getPposContractAddressOfProposal(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfProposal();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfProposal();
        }
    }

    public static String getPposContractAddressOfReward(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return NetworkParameters.MainNetParams.getPposContractAddressOfReward();
        }else {
            return NetworkParameters.TestNetParams.getPposContractAddressOfReward();
        }
    }


    //bech32 hrp
    private final String hrp;
    //锁仓合约地址
    private final String pposContractAddressOfRestrctingPlan;
    //staking合约地址
    private final String pposContractAddressOfStaking;
    //激励池地址
    private final String pposContractAddressOfIncentivePool;
    //惩罚合约地址
    private final String pposContractAddressOfSlash;
    //治理合约地址
    private final String pposContractAddressOfProposal;
    //委托收益合约地址
    private final String pposContractAddressOfReward;
    private long chainId;

    private NetworkParameters(String hrp, String pposContractAddressOfRestrctingPlan, String pposContractAddressOfStaking, String pposContractAddressOfIncentivePool, String pposContractAddressOfSlash, String pposContractAddressOfProposal, String pposContractAddressOfReward, long chainId) {
        this.hrp = hrp;
        this.pposContractAddressOfRestrctingPlan = pposContractAddressOfRestrctingPlan;
        this.pposContractAddressOfStaking = pposContractAddressOfStaking;
        this.pposContractAddressOfIncentivePool = pposContractAddressOfIncentivePool;
        this.pposContractAddressOfSlash = pposContractAddressOfSlash;
        this.pposContractAddressOfProposal = pposContractAddressOfProposal;
        this.pposContractAddressOfReward = pposContractAddressOfReward;
        this.chainId = chainId;
    }

    public String getHrp(){
        return hrp;
    }

    public String getPposContractAddressOfRestrctingPlan() {
        return pposContractAddressOfRestrctingPlan;
    }

    public String getPposContractAddressOfStaking() {
        return pposContractAddressOfStaking;
    }

    public String getPposContractAddressOfIncentivePool() {
        return pposContractAddressOfIncentivePool;
    }

    public String getPposContractAddressOfSlash() {
        return pposContractAddressOfSlash;
    }

    public String getPposContractAddressOfProposal() {
        return pposContractAddressOfProposal;
    }

    public String getPposContractAddressOfReward() {
        return pposContractAddressOfReward;
    }

    public long getChainId() {
        return chainId;
    }

    public void setChainId(long chainId) {
        this.chainId = chainId;
    }

    public enum Hrp {
        ATP("atp"),
        ATX("atx");

        private String hrp;
        Hrp(String hrp){
            this.hrp = hrp;
        }
        public String getHrp(){
            return this.hrp;
        }
    }
}
