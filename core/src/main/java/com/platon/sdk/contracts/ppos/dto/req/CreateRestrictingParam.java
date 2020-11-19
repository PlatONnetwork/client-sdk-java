package com.platon.sdk.contracts.ppos.dto.req;

import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
import com.platon.sdk.contracts.ppos.dto.RestrictingPlan;
import com.platon.sdk.utlis.Bech32;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;

import java.util.Arrays;
import java.util.List;

public class CreateRestrictingParam implements Cloneable {
    private String account;
    RestrictingPlan[] plans;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public RestrictingPlan[] getPlans() {
        return plans;
    }

    public void setPlans(RestrictingPlan[] plans) {
        this.plans = plans;
    }

    public List<Type> getSubmitInputParameters() {
        CustomStaticArray<RestrictingPlan> dynamicArray = new CustomStaticArray<>(Arrays.asList(plans));
        return Arrays.asList(
                new BytesType(Bech32.addressDecode(account)),
                dynamicArray);
    }

}