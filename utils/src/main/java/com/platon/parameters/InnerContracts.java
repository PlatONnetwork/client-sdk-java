package com.platon.parameters;

import com.platon.bech32.Bech32;

import java.util.Arrays;
import java.util.List;

public class InnerContracts {
    private static String RestrictingAddr = "0x1000000000000000000000000000000000000001";
    private static String StakingAddr = "0x1000000000000000000000000000000000000002";
    private static String RewardManagerPoolAddr = "0x1000000000000000000000000000000000000003";
    private static String SlashingAddr = "0x1000000000000000000000000000000000000004";
    private static String GovAddr = "0x1000000000000000000000000000000000000005";
    private static String DelegateRewardPoolAddr = "0x1000000000000000000000000000000000000006";

    private static List<String> InnerAddrList = Arrays.asList(RestrictingAddr, StakingAddr, RewardManagerPoolAddr, SlashingAddr, GovAddr, DelegateRewardPoolAddr);


    public static String getRestrictingAddr() {
        return RestrictingAddr;
    }

    public static String getStakingAddr() {
        return StakingAddr;
    }

    public static String getRewardManagerPoolAddr() {
        return RewardManagerPoolAddr;
    }

    public static String getSlashingAddr() {
        return SlashingAddr;
    }

    public static String getGovAddr() {
        return GovAddr;
    }

    public static String getDelegateRewardPoolAddr() {
        return DelegateRewardPoolAddr;
    }

    public static List<String> getInnerAddrList() {
        return InnerAddrList;
    }

    public static boolean isInnerAddr(String address){
        if(InnerAddrList.contains(address)){
            return true;
        }else{
            return InnerAddrList.contains(Bech32.addressDecodeHex(address));
        }
    }
}
