package com.platon.sdk.utlis;

public class NetworkParameters {

    public final static NetworkParameters MainNetParams;
    public final static NetworkParameters TestNetParams;

    static {

        MainNetParams = new NetworkParameters("lat" ,
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqp7pn3ep",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzsjx8h7",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqrdyjj2v",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqyva9ztf",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq93t3hkm",
                "lat1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqxlcypcy",
                100);
        TestNetParams = createTestNetParams(102);
    }

    public static NetworkParameters createTestNetParams(long chainId){
        return new NetworkParameters("lax" ,
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqp3yp7hw",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzlh5ge3",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqrzpqayr",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqyrchd9x",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq97wrcc5",
                "lax1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqxsakwkt",
                chainId);
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
    //委托收益合约地址
    private final long chainId;

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
}
