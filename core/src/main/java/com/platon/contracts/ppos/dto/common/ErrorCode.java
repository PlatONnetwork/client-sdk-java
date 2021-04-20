package com.platon.contracts.ppos.dto.common;

public class ErrorCode {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 系统内部错误
     */
    public static final int SYSTEM_ERROR = 1;
    /**
     * 对象没有找到
     */
    public static final int OBJECT_NOT_FOUND = 2;
    /**
     * 参数错误
     */
    public static final int INVALID_PARAMETER = 3;

    /**
     * PlatON内置合约执行出错
     */
    public static final int PlatON_Precompiled_Contract_EXEC_FAILED = 4;
    /**
     * bls key 长度有误
     */
    public static final int WRONG_BLS_KEY_LENGTH = 301000;
    /**
     * bls key 证明有误
     */
    public static final int WRONG_BLS_KEY_PROOF = 301001;
    /**
     * 节点描述信息长度有误
     */
    public static final int WRONG_DESCRIPTION_LENGTH = 301002;
    /**
     * 程序版本签名有误
     */
    public static final int WRONG_PROGRAM_VERSION_SIGN = 301003;
    /**
     * 程序的版本太低
     */
    public static final int PROGRAM_VERSION_SIGN_TOO_LOW = 301004;
    /**
     * 版本声明失败
     */
    public static final int DELCARE_VERSION_FAILED = 301005;
    /**
     * 发起交易账户必须和发起质押账户是同一个
     */
    public static final int ADDRESS_MUST_SAME_AS_INITIATED_STAKING = 301006;

    /**
     * 质押的金额太低
     */
    public static final int STAKING_DEPOSIT_TOO_LOW = 301100;
    /**
     * 候选人信息已经存在
     */
    public static final int CANDIDATE_ALREADY_EXIST = 301101;
    /**
     * 候选人信息不存在
     */
    public static final int CANDIDATE_NOT_EXIST = 301102;
    /**
     * 候选人状态已失效
     */
    public static final int CANDIDATE_STATUS_INVALIDED = 301103;
    /**
     * 增持质押金额太低
     */
    public static final int INCREASE_STAKE_TOO_LOW = 301104;
    /**
     * 委托金额太低
     */
    public static final int DELEGATE_DEPOSIT_TOO_LOW = 301105;
    /**
     * 该账户不允许发起委托
     */
    public static final int ACCOUNT_NOT_ALLOWED_DELEGATING = 301106;
    /**
     * 该候选人不接受委托
     */
    public static final int CANDIDATE_NOT_ALLOWED_DELEGATE = 301107;
    /**
     * 撤销委托的金额太低
     */
    public static final int WITHDRAW_DELEGATE_NOT_EXIST = 301108;
    /**
     * 委托详情不存在
     */
    public static final int DELEGATE_NOT_EXIST = 301109;
    /**
     * von操作类型有误 (非自由金额或非锁仓金额)
     */
    public static final int WRONG_VON_OPERATION_TYPE = 301110;
    /**
     * 账户的余额不足
     */
    public static final int ACCOUNT_BALANCE_NOT_ENOUGH = 301111;
    /**
     * 区块高度和预期不匹配
     */
    public static final int BLOCKNUMBER_DISORDERED = 301112;
    /**
     * 委托信息中余额不足
     */
    public static final int DELEGATE_VON_NOT_ENOUGH = 301113;
    /**
     * 撤销委托时金额计算有误
     */
    public static final int WRONG_WITHDRAW_DELEGATE_VON = 301114;
    /**
     * 验证人信息不存在
     */
    public static final int VALIDATOR_NOT_EXIST = 301115;
    /**
     * 参数有误
     */
    public static final int WRONG_FUNCTION_PARAM = 301116;
    /**
     * 惩罚类型有误
     */
    public static final int WRONG_SLASH_TYPE = 301117;
    /**
     * 惩罚扣除的金额溢出
     */
    public static final int SLASH_AMOUNT_TOO_LARGE = 301118;
    /**
     * 惩罚削减质押信息时金额计算有误
     */
    public static final int WRONG_SLASH_CANDIDATE_VON = 301119;
    /**
     * 拉取结算周期验证人列表失败
     */
    public static final int GETTING_VERIFIERLIST_FAILED = 301200;
    /**
     * 拉取共识周期验证人列表失败
     */
    public static final int GETTING_VALIDATORLIST_FAILED = 301201;
    /**
     * 拉取候选人列表失败
     */
    public static final int GETTING_CANDIDATELIST_FAILED = 301202;
    /**
     * 拉取委托关联映射关系失败
     */
    public static final int GETTING_DELEGATE_FAILED = 301203;
    /**
     * 查询候选人详情失败
     */
    public static final int QUERY_CANDIDATE_INFO_FAILED = 301204;
    /**
     * 查询委托详情失败
     */
    public static final int QUERY_DELEGATE_INFO_FAILED = 301205;

    /**
     * 链上生效版本没有找到
     */
    public static final int ACTIVE_VERSION_NOT_FOUND = 302001;
    /**
     * 投票选项错误
     */
    public static final int VOTE_OPTION_ERROR = 302002;
    /**
     * 提案类型错误
     */
    public static final int PROPOSAL_TYPE_ERROR = 302003;
    /**
     * 提案ID为空
     */
    public static final int PROPOSAL_ID_EMPTY = 302004;
    /**
     * 提案ID已经存在
     */
    public static final int PROPOSAL_ID_ALREADY_EXISTS = 302005;
    /**
     * 提案没有找到
     */
    public static final int PROPOSAL_NOT_FOUND = 302006;
    /**
     * PIPID为空
     */
    public static final int PIPID_EMPTY = 302007;
    /**
     * PIPID已经存在
     */
    public static final int PIPID_ALREADY_EXISTS = 302008;
    /**
     * 投票持续的共识轮数量太小
     */
    public static final int ENDVOTINGROUNDS_TOO_SMALL = 302009;
    /**
     * 投票持续的共识轮数量太大
     */
    public static final int ENDVOTINGROUNDS_TOO_LARGE = 302010;
    /**
     * 新版本的大版本应该大于当前生效版本的大版本
     */
    public static final int NEWVERSION_SHOULD_LARGE_CURRENT_ACTIVE_VERSION = 302011;
    /**
     * 有另一个在投票期的升级提案
     */
    public static final int ANOTHER_VERSION_PROPOSAL_AT_VOTING_STAGE = 302012;
    /**
     * 有另一个预生效的升级提案
     */
    public static final int ANOTHER_VERSION_PROPOSAL_AT_PRE_ACTIVE_STAGE = 302013;
    /**
     * 有另一个在投票期的取消提案
     */
    public static final int ANOTHER_CANCEL_PROPOSAL_AT_VOTING_STAGE = 302014;
    /**
     * 待取消的(升级)提案没有找到
     */
    public static final int CANCELED_PROPOSAL_NOT_FOUND = 302015;
    /**
     * 待取消的提案不是升级提案
     */
    public static final int CANCELED_PROPOSAL_NOT_VERSION_TYPE = 302016;
    /**
     * 待取消的(升级)提案不在投票期
     */
    public static final int CANCELED_PROPOSAL_NOT_AT_VOTING_STAGE = 302017;
    /**
     * 提案人NodeID为空
     */
    public static final int PROPOSER_EMPTY = 302018;
    /**
     * 验证人详情没有找到
     */
    public static final int VERIFIER_DETAIL_INFO_NOT_FOUND = 302019;
    /**
     * 验证人状态为无效状态
     */
    public static final int VERIFIER_STATUS_INVALID = 302020;
    /**
     * 发起交易账户和发起质押账户不是同一个
     */
    public static final int TX_CALLER_DIFFER_FROM_STAKING = 302021;
    /**
     * 发起交易的节点不是验证人
     */
    public static final int TX_CALLER_NOT_VERIFIER = 302022;
    /**
     * 发起交易的节点不是候选人
     */
    public static final int TX_CALLER_NOT_CANDIDATE = 302023;
    /**
     * 版本签名错误
     */
    public static final int VERSION_SIGN_ERROR = 302024;
    /**
     * 验证人没有升级到新版本
     */
    public static final int VERIFIER_NOT_UPGRADED = 302025;
    /**
     * 提案不在投票期
     */
    public static final int PROPOSAL_NOT_AT_VOTING_STAGE = 302026;
    /**
     * 投票重复
     */
    public static final int VOTE_DUPLICATED = 302027;
    /**
     * 声明的版本错误
     */
    public static final int DECLARE_VERSION_ERROR = 302028;
    /**
     * 把节点声明的版本通知Staking时出错
     */
    public static final int NOTIFY_STAKING_DECLARED_VERSION_ERROR = 302029;
    /**
     * 提案结果没有找到
     */
    public static final int TALLY_RESULT_NOT_FOUND = 302030;
    /**
     * 不支持的治理参数
     */
    public static final int UNSUPPORTED_GOVERN_PARAMETER = 302031;
    /**
     * 有另一个在投票期的参数提案
     */
    public static final int ANOTHER_PARAM_PROPOSAL_AT_VOTING_STAGE = 302032;
    /**
     * 参数提案的的参数值错误
     */
    public static final int GOVERN_PARAMETER_VALUE_ERROR = 302033;
    /**
     * 参数提案的值必须和旧值不同
     */
    public static final int PARAMETER_PROPOSAL_NEW_VALUE_SAME_AS_OLD_VALUE = 302034;
    /**
     * 双签证据校验失败
     */
    public static final int DUPLICATE_SIGNATURE_VERIFICATION_FAILED = 303000;
    /**
     * 已根据该证据执行过惩罚
     */
    public static final int PUNISHMENT_HAS_BEEN_IMPLEMENTED = 303001;
    /**
     * 举报的双签块高比当前区块高
     */
    public static final int BLOCKNUMBER_TOO_HIGH = 303002;
    /**
     * 举报的证据超过有效期
     */
    public static final int EVIDENCE_INTERVAL_TOO_LONG = 303003;
    /**
     * 获取举报的验证人信息失败
     */
    public static final int GET_CERTIFIER_INFOMATION_FAILED = 303004;
    /**
     * 证据的地址和验证人的地址不匹配
     */
    public static final int ADDRESS_NOT_MATCH = 303005;
    /**
     * 证据的节点ID和验证人的节点ID不匹配
     */
    public static final int NODEID_NOT_MATCH = 303006;
    /**
     * 证据的blsPubKey和验证人的blsPubKey不匹配
     */
    public static final int BLS_PUBKEY_NOT_MATCH = 303007;
    /**
     * 惩罚节点失败
     */
    public static final int SLASH_NODE_FAILED = 303008;
    /**
     * 创建锁仓计划数不能为0或者大于36
     */
    public static final int PARAM_EPOCH_CANNOT_BE_ZERO = 304001;
    /**
     * 创建锁仓计划数不能为0或者大于36
     */
    public static final int RESTRICTING_PLAN_NUMBER_CANNOT_BE_0_OR_MORE_THAN_36 = 304002;
    /**
     * 锁仓创建总金额不能小于1E18
     */
    public static final int TOTAL_RESTRICTING_AMOUNT_SHOULD_MORE_THAN_ONE = 304003;
    /**
     * 账户余额不够支付锁仓
     */
    public static final int BALANCE_NOT_ENOUGH_FOR_RESTRICT = 304004;
    /**
     * 没有在锁仓合约中找到该账户
     */
    public static final int RESTRICTING_CONTRACT_AMOUNT_NOT_FOUND = 304005;
    /**
     * 惩罚金额大于质押金额
     */
    public static final int SLASH_AMOUNT_LARGER_THAN_STAKING_AMOUNT = 304006;
    /**
     * 惩罚锁仓账户的质押金额不能为0
     */
    public static final int STAKING_AMOUNT_ZERO = 304007;
    /**
     * 锁仓转质押后回退的金额不能小于0
     */
    public static final int AMOUNT_CANNOT_LESS_THAN_ZERO = 304008;
    /**
     * 锁仓信息中的质押金额小于回退的金额
     */
    public static final int WRONG_STAKING_RETURN_AMOUNT = 304009;

    public static String getErrorMsg(int errorCode) {
        switch (errorCode) {
            case SUCCESS:
                return "成功";
            case SYSTEM_ERROR:
                return "系统内部错误";
            case OBJECT_NOT_FOUND:
                return "对象没有找到";
            case INVALID_PARAMETER:
                return "参数错误";
            case WRONG_BLS_KEY_LENGTH:
                return "bls key 长度有误";
            case WRONG_BLS_KEY_PROOF:
                return "bls key 证明有误";
            case WRONG_DESCRIPTION_LENGTH:
                return "节点描述信息长度有误";
            case WRONG_PROGRAM_VERSION_SIGN:
                return "程序版本签名有误";
            case PROGRAM_VERSION_SIGN_TOO_LOW:
                return "程序的版本太低";
            case DELCARE_VERSION_FAILED:
                return "版本声明失败";
            case ADDRESS_MUST_SAME_AS_INITIATED_STAKING:
                return "发起交易账户必须和发起质押账户是同一个";
            case STAKING_DEPOSIT_TOO_LOW:
                return "质押的金额太低";
            case CANDIDATE_ALREADY_EXIST:
                return "候选人信息已经存在";
            case CANDIDATE_NOT_EXIST:
                return "候选人信息不存在";
            case CANDIDATE_STATUS_INVALIDED:
                return "候选人状态已失效";
            case INCREASE_STAKE_TOO_LOW:
                return "增持质押金额太低";
            case DELEGATE_DEPOSIT_TOO_LOW:
                return "委托金额太低";
            case ACCOUNT_NOT_ALLOWED_DELEGATING:
                return "该候选人不接受委托";
            case CANDIDATE_NOT_ALLOWED_DELEGATE:
                return "撤销委托的金额太低";
            case WITHDRAW_DELEGATE_NOT_EXIST:
                return "撤销委托的金额太低";
            case DELEGATE_NOT_EXIST:
                return "委托详情不存在";
            case WRONG_VON_OPERATION_TYPE:
                return "von操作类型有误 (非自由金额或非锁仓金额)";
            case ACCOUNT_BALANCE_NOT_ENOUGH:
                return "账户的余额不足";
            case BLOCKNUMBER_DISORDERED:
                return "区块高度和预期不匹配";
            case DELEGATE_VON_NOT_ENOUGH:
                return "委托信息中余额不足";
            case WRONG_WITHDRAW_DELEGATE_VON:
                return "撤销委托时金额计算有误";
            case VALIDATOR_NOT_EXIST:
                return "验证人信息不存在";
            case WRONG_FUNCTION_PARAM:
                return "参数有误";
            case WRONG_SLASH_TYPE:
                return "惩罚类型有误";
            case SLASH_AMOUNT_TOO_LARGE:
                return "惩罚扣除的金额溢出";
            case WRONG_SLASH_CANDIDATE_VON:
                return "惩罚削减质押信息时金额计算有误";
            case GETTING_VERIFIERLIST_FAILED:
                return "拉取结算周期验证人列表失败";
            case GETTING_VALIDATORLIST_FAILED:
                return "拉取共识周期验证人列表失败";
            case GETTING_CANDIDATELIST_FAILED:
                return "拉取候选人列表失败";
            case GETTING_DELEGATE_FAILED:
                return "拉取委托关联映射关系失败";
            case QUERY_CANDIDATE_INFO_FAILED:
                return "查询候选人详情失败";
            case QUERY_DELEGATE_INFO_FAILED:
                return "查询委托详情失败";
            case ACTIVE_VERSION_NOT_FOUND:
                return "链上生效版本没有找到";
            case VOTE_OPTION_ERROR:
                return "投票选项错误";
            case PROPOSAL_TYPE_ERROR:
                return "提案类型错误";
            case PROPOSAL_ID_EMPTY:
                return "提案ID为空";
            case PROPOSAL_ID_ALREADY_EXISTS:
                return "提案ID已经存在";
            case PROPOSAL_NOT_FOUND:
                return "提案没有找到";
            case PIPID_EMPTY:
                return "PIPID为空";
            case PIPID_ALREADY_EXISTS:
                return "PIPID已经存在";
            case ENDVOTINGROUNDS_TOO_SMALL:
                return "投票持续的共识轮数量太小";
            case ENDVOTINGROUNDS_TOO_LARGE:
                return "投票持续的共识轮数量太大";
            case NEWVERSION_SHOULD_LARGE_CURRENT_ACTIVE_VERSION:
                return "新版本的大版本应该大于当前生效版本的大版本";
            case ANOTHER_VERSION_PROPOSAL_AT_VOTING_STAGE:
                return "有另一个在投票期的升级提案";
            case ANOTHER_VERSION_PROPOSAL_AT_PRE_ACTIVE_STAGE:
                return "有另一个预生效的升级提案";
            case ANOTHER_CANCEL_PROPOSAL_AT_VOTING_STAGE:
                return "有另一个在投票期的取消提案";
            case CANCELED_PROPOSAL_NOT_FOUND:
                return "待取消的(升级)提案没有找到";
            case CANCELED_PROPOSAL_NOT_VERSION_TYPE:
                return "待取消的提案不是升级提案";
            case CANCELED_PROPOSAL_NOT_AT_VOTING_STAGE:
                return "待取消的(升级)提案不在投票期";
            case PROPOSER_EMPTY:
                return "提案人NodeID为空";
            case VERIFIER_DETAIL_INFO_NOT_FOUND:
                return "验证人详情没有找到";
            case VERIFIER_STATUS_INVALID:
                return "验证人状态为无效状态";
            case TX_CALLER_DIFFER_FROM_STAKING:
                return "发起交易账户和发起质押账户不是同一个";
            case TX_CALLER_NOT_VERIFIER:
                return "发起交易的节点不是验证人";
            case TX_CALLER_NOT_CANDIDATE:
                return "发起交易的节点不是候选人";
            case VERSION_SIGN_ERROR:
                return "版本签名错误";
            case VERIFIER_NOT_UPGRADED:
                return "验证人没有升级到新版本";
            case PROPOSAL_NOT_AT_VOTING_STAGE:
                return "提案不在投票期";
            case VOTE_DUPLICATED:
                return "投票重复";
            case DECLARE_VERSION_ERROR:
                return "声明的版本错误";
            case NOTIFY_STAKING_DECLARED_VERSION_ERROR:
                return "把节点声明的版本通知Staking时出错";
            case TALLY_RESULT_NOT_FOUND:
                return "提案结果没有找到";
            case UNSUPPORTED_GOVERN_PARAMETER:
                return "不支持的治理参数";
            case ANOTHER_PARAM_PROPOSAL_AT_VOTING_STAGE:
                return "有另一个在投票期的参数提案";
            case GOVERN_PARAMETER_VALUE_ERROR:
                return "参数提案的的参数值错误";
            case PARAMETER_PROPOSAL_NEW_VALUE_SAME_AS_OLD_VALUE:
                return "参数提案的值必须和旧值不同";
            case DUPLICATE_SIGNATURE_VERIFICATION_FAILED:
                return "双签证据校验失败";
            case PUNISHMENT_HAS_BEEN_IMPLEMENTED:
                return "已根据该证据执行过惩罚";
            case BLOCKNUMBER_TOO_HIGH:
                return "举报的双签块高比当前区块高";
            case EVIDENCE_INTERVAL_TOO_LONG:
                return "举报的证据超过有效期";
            case GET_CERTIFIER_INFOMATION_FAILED:
                return "获取举报的验证人信息失败";
            case ADDRESS_NOT_MATCH:
                return "证据的地址和验证人的地址不匹配";
            case NODEID_NOT_MATCH:
                return "证据的节点ID和验证人的节点ID不匹配";
            case BLS_PUBKEY_NOT_MATCH:
                return "证据的blsPubKey和验证人的blsPubKey不匹配";
            case SLASH_NODE_FAILED:
                return "惩罚节点失败";
            case PARAM_EPOCH_CANNOT_BE_ZERO:
                return "创建锁仓计划票龄不能为0";
            case RESTRICTING_PLAN_NUMBER_CANNOT_BE_0_OR_MORE_THAN_36:
                return "创建锁仓计划数不能为0或者大于36";
            case TOTAL_RESTRICTING_AMOUNT_SHOULD_MORE_THAN_ONE:
                return "锁仓创建总金额不能小于1E18";
            case BALANCE_NOT_ENOUGH_FOR_RESTRICT:
                return "账户余额不够支付锁仓";
            case RESTRICTING_CONTRACT_AMOUNT_NOT_FOUND:
                return "没有在锁仓合约中找到该账户";
            case SLASH_AMOUNT_LARGER_THAN_STAKING_AMOUNT:
                return "惩罚金额大于质押金额";
            case STAKING_AMOUNT_ZERO:
                return "惩罚锁仓账户的质押金额不能为0";
            case AMOUNT_CANNOT_LESS_THAN_ZERO:
                return "锁仓转质押后回退的金额不能小于0";
            case WRONG_STAKING_RETURN_AMOUNT:
                return "锁仓信息中的质押金额小于回退的金额";
            default:
                return "";

        }
    }

}
