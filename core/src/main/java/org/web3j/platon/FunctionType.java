package org.web3j.platon;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.web3j.abi.datatypes.generated.Uint16;

import java.math.BigInteger;

public class FunctionType {

    /**
     * 发起质押
     */
    public static final int STAKING_FUNC_TYPE = 1000;
    /**
     * 修改质押信息
     */
    public static final int UPDATE_STAKING_INFO_FUNC_TYPE = 1001;
    /**
     * 增持质押
     */
    public static final int ADD_STAKING_FUNC_TYPE = 1002;
    /**
     * 撤销质押(一次性发起全部撤销，多次到账)
     */
    public static final int WITHDREW_STAKING_FUNC_TYPE = 1003;
    /**
     * 发起委托
     */
    public static final int DELEGATE_FUNC_TYPE = 1004;
    /**
     * 减持/撤销委托(全部减持就是撤销)
     */
    public static final int WITHDREW_DELEGATE_FUNC_TYPE = 1005;
    /**
     * 查询当前结算周期的验证人队列
     */
    public static final int GET_VERIFIERLIST_FUNC_TYPE = 1100;
    /**
     * 查询当前共识周期的验证人列表
     */
    public static final int GET_VALIDATORLIST_FUNC_TYPE = 1101;
    /**
     * 查询所有实时的候选人列表
     */
    public static final int GET_CANDIDATELIST_FUNC_TYPE = 1102;
    /**
     * 查询当前账户地址所委托的节点的NodeID和质押Id
     */
    public static final int GET_DELEGATELIST_BYADDR_FUNC_TYPE = 1103;
    /**
     * 查询当前单个委托信息
     */
    public static final int GET_DELEGATEINFO_FUNC_TYPE = 1104;
    /**
     * 查询当前节点的质押信息
     */
    public static final int GET_STAKINGINFO_FUNC_TYPE = 1105;
    /**
     * 提交文本提案
     */
    public static final int SUBMIT_TEXT_FUNC_TYPE = 2000;
    /**
     * 提交升级提案
     */
    public static final int SUBMIT_VERSION_FUNC_TYPE = 2001;
    /**
     * 提交参数提案
     */
    public static final int SUBMIR_PARAM_FUNCTION_TYPE = 2002;
    /**
     * 给提案投票
     */
    public static final int VOTE_FUNC_TYPE = 2003;
    /**
     * 版本声明
     */
    public static final int DECLARE_VERSION_FUNC_TYPE = 2004;
    /**
     * 提交取消提案
     */
    public static final int SUBMIT_CANCEL_FUNC_TYPE = 2005;
    /**
     * 查询提案
     */
    public static final int GET_PROPOSAL_FUNC_TYPE = 2100;
    /**
     * 查询提案结果
     */
    public static final int GET_TALLY_RESULT_FUNC_TYPE = 2101;
    /**
     * 查询提案列表
     */
    public static final int GET_PROPOSAL_LIST_FUNC_TYPE = 2102;
    /**
     * 查询提案生效版本
     */
    public static final int GET_ACTIVE_VERSION = 2103;
    /**
     * 查询节点代码版本
     */
    public static final int GET_PROGRAM_VERSION = 2104;
    /**
     * 查询可治理列表
     */
    public static final int GET_PARAM_LIST = 2105;
    /**
     * 举报双签
     */
    public static final int REPORT_DOUBLESIGN_FUNC_TYPE = 3000;
    /**
     * 查询节点是否已被举报过多签
     */
    public static final int CHECK_DOUBLESIGN_FUNC_TYPE = 3001;
    /**
     * 创建锁仓计划
     */
    public static final int CREATE_RESTRICTINGPLAN_FUNC_TYPE = 4000;
    /**
     * 获取锁仓信息
     */
    public static final int GET_RESTRICTINGINFO_FUNC_TYPE = 4100;

}
