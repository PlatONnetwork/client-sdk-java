# Java SDK开发指南

## 开发库导入

根据构建工具的不同，使用以下方式将相关依赖项添加到项目中：

- 使用要求jdk1.8以上.

### maven

> 项目配置:
```xml
<repository>
	<id>platon-public</id>
	<url>https://sdk.platon.network/nexus/content/groups/public/</url>
</repository>
```

> maven引用方式:
```xml
<dependency>
	<groupId>com.platon.client</groupId>
	<artifactId>core</artifactId>
	<version>0.8.0.0</version>
</dependency>
```

### gradle

> 项目配置:	
```
repositories {
	maven { url "https://sdk.platon.network/nexus/content/groups/public/" }
}
```

> gradle引用方式:
```
compile "com.platon.client:core:0.8.0.0"
```

## 系统合约调用

系统合约主要包含经济模型和治理相关的合约：
* 质押合约
* 委托合约
* 奖励合约
* 节点合约
* 治理合约
* 举报合约
* 锁仓合约

如上系统合约的介绍和使用，请参考如下合约接口说明。


### 质押相关接口

> PlatON经济模型中质押合约相关的接口

#### 加载质押合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
StakingContract contract = StakingContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **staking**

> 节点候选人申请质押

* **入参**

  - String：nodeId   节点id,16进制格式
  - BigInteger：amount   质押的VON，默认质押金额大于等于1000000LAT，根据治理参数调整
  - StakingAmountType：stakingAmountType   枚举,FREE_AMOUNT_TYPE表示使用账户自由金额,RESTRICTING_AMOUNT_TYPE表示使用锁仓金额做质押
  - String：benifitAddress   收益账户,用于接受出块奖励和质押奖励的收益账户
  - String：nodeName   被质押节点的名称
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥
  - String：webSite   节点的第三方主页(有长度限制，表示该节点的主页)
  - String：details   节点的描述(有长度限制，表示该节点的描述)
  - ProgramVersion：processVersion  程序的真实版本，治理rpc获取
  - String：blsPubKey   bls的公钥
  - String：blsProof    bls的证明，治理rpc获取
  - BigInteger：rewardPer   委托所得到的奖励分成比例，1=0.01%   10000=100%

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
BigDecimal stakingAmount = Convert.toVon("1000000", Unit.LAT);
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
String benifitAddress = "0x02c344817be3448c97a059473121a6679450b414";
String externalId = "";
String nodeName = "integration-node1";
String webSite = "https://www.platon.network/#/";
String details = "integration-node1-details";
String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
BigInteger rewardPer = BigInteger.valueOf(1000L);

PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
        .setNodeId(nodeId)
        .setAmount(stakingAmount.toBigInteger())
        .setStakingAmountType(stakingAmountType)
        .setBenifitAddress(benifitAddress)
        .setExternalId(externalId)
        .setNodeName(nodeName)
        .setWebSite(webSite)
        .setDetails(details)
        .setBlsPubKey(blsPubKey)
        .setProcessVersion(web3j.getProgramVersion().send().getAdminProgramVersion())
        .setBlsProof(web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve())
        .setRewardPer(rewardPer)
        .build()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **unStaking**

> 节点撤销质押(一次性发起全部撤销，多次到账)

* **入参**

  - String：nodeId   节点id，16进制格式

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";

PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **updateStaking**

> 修改质押信息

* **入参**

  - String：nodeId   节点id,16进制格式
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id),目前为keybase账户公钥
  - String：benifitAddress   收益账户
  - String：nodeName   被质押节点的名称
  - String：webSite   节点的第三方主页(有长度限制，表示该节点的主页)
  - String：details   节点的描述(有长度限制，表示该节点的描述)
  - BigInteger：rewardPer   委托所得到的奖励分成比例，1=0.01%   10000=100%

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
String benifitAddress = benefitCredentials.getAddress();
String externalId = "";
String nodeName = "integration-node1-u";
String webSite = "https://www.platon.network/#/";
String details = "integration-node1-details-u";
BigInteger rewardPer = BigInteger.valueOf(1000L);

PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
        .setBenifitAddress(benifitAddress)
        .setExternalId(externalId)
        .setNodeId(nodeId)
        .setNodeName(nodeName)
        .setWebSite(webSite)
        .setDetails(details)
        .setRewardPer(rewardPer)
        .build()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **addStaking**

> 增持质押，增加已质押节点质押金

* **入参**

    - String：nodeId   节点id，16进制格式
    - StakingAmountType：stakingAmountType  枚举,FREE_AMOUNT_TYPE表示使用账户自由金额,RESTRICTING_AMOUNT_TYPE表示使用锁仓金额做质押
    - BigInteger：addStakingAmount   增持的金额

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
BigDecimal addStakingAmount = Convert.toVon("4000000", Unit.LAT);

PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, stakingAmountType, addStakingAmount.toBigInteger()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getStakingInfo**

> 查询当前节点的质押信息

* **入参**

  - String：nodeId   节点id，16进制格式

* **返回值**

```java
CallResponse<Node> baseRespons
```

- CallResponse<Node>描述
	- int： code   结果标识，0为成功
	- Node：data   Node对象数据
	- String：errMsg   错误信息，失败时存在
	
* **Node**：保存当前节点质押信息的对象

  - String：BenefitAddress	用于接受出块奖励和质押奖励的收益账户

  - String：Details   节点的描述(有长度限制，表示该节点的描述) 

  - String：NodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：NodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - BigInteger：ProgramVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：Released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：ReleasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：RestrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：RestrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON

  - BigInteger：Shares   当前候选人总共质押加被委托的VON数目

  - String：StakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - BigInteger：StakingBlockNum    发起质押时的区块高度

  - BigInteger：StakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：StakingTxIndex   发起质押时的交易索引

  - BigInteger：Status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，4:节点的VON不足最低质押门槛(只有倒数第三bit为1)，8:节点被举报双签，16:节点出块率低且达到移除条件(倒数第五位bit为1); 32: 节点主动发起撤销

  - BigInteger：ValidatorTerm   验证人的任期

  - String：Website   节点的第三方主页(有长度限制，表示该节点的主页)
  
  - BigInteger：delegateEpoch  节点最后一次被委托的结算周期
  
  - BigInteger：delegateTotal  节点被委托的生效总数量
  
  - BigInteger：delegateTotalHes  节点被委托的未生效总数量
  
  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励
  
  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例
  
  - BigInteger：rewardPer 当前结算周期奖励分成比例

* **Java SDK合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
CallResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
```

##### **getPackageReward**

> 查询当前结算周期的区块奖励

* **入参**

  无

* **返回值**

```java
CallResponse<BigInteger> baseResponse
```

- CallResponse<BigInteger>描述
	- int：code   结果标识，0为成功
	- BigInteger：reward   当前结算周期的区块奖励
	- String：errMsg   错误信息，失败时存在

* **Java SDK合约使用**

```java
CallResponse<BigInteger> response = stakingContract.getPackageReward().send();
```

##### **getStakingReward**

> 查询当前结算周期的质押奖励

* **入参**

  无

* **返回值**

```java
CallResponse<BigInteger> baseResponse
```

- CallResponse<List<Node>>描述
	- int：code   结果标识，0为成功
	- BigInteger：reward   当前结算周期的质押奖励
	- String：errMsg   错误信息，失败时存在

* **Java SDK合约使用**

```java
CallResponse<BigInteger> response = stakingContract.getStakingReward().send();
```

##### **getAvgPackTime**

> 查询打包区块的平均时间

* **入参**

  无

* **返回值**

```java
CallResponse<BigInteger> baseResponse
```

- CallResponse<BigInteger>描述
	- int：code   结果标识，0为成功
	- BigInteger：data   打包区块的平均时间
	- String：errMsg   错误信息，失败时存在

* **Java SDK合约使用**

```java
CallResponse<BigInteger> response = stakingContract.getAvgPackTime().send();
```

### 委托相关接口

> PlatON经济模型中委托人相关的合约接口

#### 加载委托合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
DelegateContract delegateContract = DelegateContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **delegate**

> 发起委托，委托已质押节点，委托给某个节点增加节点权重来获取收入 

* **入参**

  - String：nodeId   节点id，16进制格式，0x开头
  - StakingAmountType：stakingAmountType  枚举,FREE_AMOUNT_TYPE表示使用账户自由金额,RESTRICTING_AMOUNT_TYPE表示使用锁仓金额做质押
  - BigInteger：amount   委托的金额(按照最小单位算，1LAT = 10**18 VON)

* **返回值**

``` java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
BigDecimal amount = Convert.toVon("500000", Unit.LAT);

PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, stakingAmountType, amount.toBigInteger()).send();
TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getRelatedListByDelAddr**

> 查询当前账户地址所委托的节点的NodeID和质押Id

* **入参**

  - String：address   委托人的账户地址

* **返回值**

```java
CallResponse<List<DelegationIdInfo>> baseRespons
```

- CallResponse<List<DelegationIdInfo>>描述
	- int：code   结果标识，0为成功
	- List<DelegationIdInfo>：data   DelegationIdInfo对象列表
	- String：errMsg   错误信息，失败时存在

* **DelegationIdInfo**：保存当前账户地址所委托的节点的NodeID和质押区块高度的对象
  - String：address   委托人的账户地址
  - String：NodeId   验证人的节点Id
  - BigInteger：StakingBlockNum   发起质押时的区块高度

* **Java SDK合约使用**

```java
CallResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateCredentials.getAddress()).send();
```

##### **getDelegateInfo**

> 查询当前单个委托信息

* **入参**

  - String：address   委托人的账户地址
  - String：nodeId   节点id，16进制格式，0x开头
  - BigInteger：stakingBlockNum   发起质押时的区块高度

* **返回值**

```java
CallResponse<Delegation>
```

- CallResponse<Delegation>描述
	- int：code   结果标识，0为成功
	- Delegation：Data   Delegation对象数据
	- String：errMsg   错误信息，失败时存在

* **Delegation**：保存当前委托账户委托信息的对象
  - String：Address	委托人的账户地址
  - String：NodeId   验证人的节点Id
  - BigInteger：StakingBlockNum    发起质押时的区块高度
  - BigInteger：DelegateEpoch   最近一次对该候选人发起的委托时的结算周期
  - BigInteger：Released   发起委托账户的自由金额的锁定期委托的VON
  - BigInteger：ReleasedHes   发起委托账户的自由金额的犹豫期委托的VON
  - BigInteger：RestrictingPlan   发起委托账户的锁仓金额的锁定期委托的VON
  - BigInteger：RestrictingPlanHes   发起委托账户的锁仓金额的犹豫期质押的VON
  - BigInteger：Reduction   处于撤销计划中的VON
  - BigInteger：cumulativeIncome  待领取的委托收益

* **Java SDK合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
String address = "0xc1f330b214668beac2e6418dd651b09c759a4bf5";
BigInteger stakingBlockNum = new BigInteger("10888");

CallResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, address, stakingBlockNum).send();
```

##### **unDelegate**

> 减持/撤销委托(全部减持就是撤销)

* **入参**

  - String：nodeId   节点id，16进制格式，0x开头
  - BigInteger：stakingBlockNum   委托节点的质押块高，代表着某个node的某次质押的唯一标示
  - BigInteger：stakingAmount     减持的委托金额(按照最小单位算，1LAT = 10**18 VON)

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执
	
* **解交易回执**

   - BigInteger：reward   获得解除委托时所提取的委托收益

* **合约使用**
   
   ```java
   String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
   BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
   BigInteger stakingBlockNum = new BigInteger("12134");
   
   PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingBlockNum, stakingAmount.toBigInteger()).send();
   TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
   
   if(baseResponse.isStatusOk()){ 
       BigInteger reward = delegateContract.decodeUnDelegateLog(baseResponse.getTransactionReceipt());
   }
   ```

### 奖励相关接口

> PlatON经济模型中奖励相关的合约接口

#### 加载奖励合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RewardContract rewardContract = RewardContract.load(web3j, deleteCredentials, chainId);
```

#### 接口说明

##### **withdrawDelegateReward**

> 提取账户当前所有的可提取的委托奖励 

* **入参**

  无

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执
	
* **解交易回执**
   - String：nodeId    节点ID
   - BigInteger：stakingNum  节点的质押块高
   - BigInteger：reward  领取到的收益

* **合约使用**

```java
PlatonSendTransaction platonSendTransaction = rewardContract.withdrawDelegateRewardReturnTransaction().send();
TransactionResponse baseResponse = rewardContract.getTransactionResponse(platonSendTransaction).send();
if(baseResponse.isStatusOk()){
    List<Reward> rewardList = rewardContract.decodeWithdrawDelegateRewardLog(baseResponse.getTransactionReceipt());
}
```

##### **getDelegateReward**

> 查询当前账号可提取奖励明细

* **入参**
  - String：address   委托人的账户地址
  - List<String>： nodeList  节点列表，如果为空查全部

* **返回值**

```java
CallResponse<List<Reward>> baseRespons
```

- CallResponse<List<Reward>>描述
	- int：code   结果标识，0为成功
	- List<Reward>：data   Reward对象列表
	- String：errMsg   错误信息，失败时存在

* **Reward**：奖励明细
   - String：nodeId    节点ID
   - BigInteger：stakingNum  节点的质押块高
   - BigInteger：reward  领取到的收益

* **Java SDK合约使用**

```java
List<String> nodeList = new ArrayList<>();
nodeList.add(nodeId);
CallResponse<List<Reward>> baseResponse = rewardContract.getDelegateReward(delegateAddress, nodeList).send();
```

### 节点相关合约

> PlatON经济模型中委托人相关的合约接口

#### 加载节点合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
NodeContract contract = NodeContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **getVerifierList**

> 查询当前结算周期的验证人队列

* **入参**

  无

* **返回值**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node>>描述
	- int：code   结果标识，0为成功
	- List<Node>：data   nodeList对象数据
	- String：errMsg   错误信息，失败时存在

* **Node**：保存单个当前结算周期验证节点信息的对象

  - String：BenefitAddress	用于接受出块奖励和质押奖励的收益账户

  - String：Details   节点的描述(有长度限制，表示该节点的描述) 

  - String：NodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：NodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - BigInteger：ProgramVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：Released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：ReleasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：RestrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：RestrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON

  - BigInteger：Shares   当前候选人总共质押加被委托的VON数目

  - String：StakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - BigInteger：StakingBlockNum    发起质押时的区块高度

  - BigInteger：StakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：StakingTxIndex   发起质押时的交易索引

  - BigInteger：Status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，          

    4:节点的VON不足最低质押门槛(只有倒数第三bit为1)，8:节点被举报双签，16:节点出块率低且达到移除条件(倒数第五位bit为1); 32: 节点主动发起撤销

  - BigInteger：ValidatorTerm   验证人的任期

  - String：Website   节点的第三方主页(有长度限制，表示该节点的主页)

  - BigInteger：delegateTotal  节点被委托的生效总数量

  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励

  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例

  - BigInteger：rewardPer 当前结算周期奖励分成比例

* **Java SDK合约使用**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
```

##### **getValidatorList**
> 查询当前共识周期的验证人列表

* **入参**

  无

* **返回值**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node>>描述
	- int：code   结果标识，0为成功
	- List<Node>：data   nodeList对象数据
	- String：errMsg   错误信息，失败时存在

* **Node**：保存单个当前共识周期验证节点信息的对象

  - String：BenefitAddress	用于接受出块奖励和质押奖励的收益账户

  - String：Details   节点的描述(有长度限制，表示该节点的描述) 

  - String：NodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：NodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - BigInteger：ProgramVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：Released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：ReleasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：RestrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：RestrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON

  - BigInteger：Shares   当前候选人总共质押加被委托的VON数目

  - String：StakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - BigInteger：StakingBlockNum    发起质押时的区块高度

  - BigInteger：StakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：StakingTxIndex   发起质押时的交易索引

  - BigInteger：Status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，4:节点的VON不足最低质押门槛(只有倒数第三bit为1)，8:节点被举报双签，16:节点出块率低且达到移除条件(倒数第五位bit为1); 32: 节点主动发起撤销

  - BigInteger：ValidatorTerm   验证人的任期

  - String：Website   节点的第三方主页(有长度限制，表示该节点的主页)

  - BigInteger：delegateTotal  节点被委托的生效总数量

  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励

  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例
 
  - BigInteger：rewardPer 当前结算周期奖励分成比例

* **Java SDK合约使用**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
```

##### **getCandidateList**

> 查询所有实时的候选人列表

* **入参**

  无

* **返回值**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node>>描述
	- int：code   结果标识，0为成功
	- List<Node>：data   nodeList对象数据
	- String：errMsg   错误信息，失败时存在

* **Node**：保存单个候选节点信息对象

  - String：BenefitAddress	用于接受出块奖励和质押奖励的收益账户

  - String：Details   节点的描述(有长度限制，表示该节点的描述) 

  - String：NodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：NodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - BigInteger：ProgramVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：Released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：ReleasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：RestrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：RestrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON

  - BigInteger：Shares   当前候选人总共质押加被委托的VON数目

  - String：StakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - BigInteger：StakingBlockNum    发起质押时的区块高度

  - BigInteger：StakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：StakingTxIndex   发起质押时的交易索引

  - BigInteger：Status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，          

    4:节点的VON不足最低质押门槛(只有倒数第三bit为1)，8:节点被举报双签，16:节点出块率低且达到移除条件(倒数第五位bit为1); 32: 节点主动发起撤销

  - BigInteger：ValidatorTerm   验证人的任期

  - String：Website   节点的第三方主页(有长度限制，表示该节点的主页)

  - BigInteger：delegateEpoch  节点最后一次被委托的结算周期
  
  - BigInteger：delegateTotal  节点被委托的生效总数量
  
  - BigInteger：delegateTotalHes  节点被委托的未生效总数量
  
  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励
  
  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例
  
  - BigInteger：rewardPer 当前结算周期奖励分成比例

* **Java SDK合约使用**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
```

###  治理相关合约

> PlatON治理相关的合约接口

#### 加载治理合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
ProposalContract contract = ProposalContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **submitProposal**

> 提交提案

* **入参**

  - Proposal：提案对象

* **文本提案 Proposal.createSubmitTextProposalParam()**
  - String：verifier 提交提案的验证人
  - String：pIDID  PIPID

* **升级提案 Proposal.createSubmitVersionProposalParam()**
  - String：verifier 提交提案的验证人
  - String：pIDID  PIPID
  - BigInteger：newVersion  升级版本
  - BigInteger：endVotingRounds   投票共识轮数量。说明：假设提交提案的交易，被打包进块时的共识轮序号时round1，则提案投票截止块高，就是round1 + endVotingRounds这个共识轮的第230个块高（假设一个共识轮出块250，ppos揭榜提前20个块高，250，20都是可配置的 ），其中0 < endVotingRounds <= 4840（约为2周，实际论述根据配置可计算），且为整数）

* **参数提案 Proposal.createSubmitParamProposalParam()**
  - String：verifier 提交提案的验证人
  - String：pIDID  PIPID
  - String：module  参数模块
  - String：name  参数名称
  - String：newValue 参数新值
  
* **取消提案 Proposal.createSubmitCancelProposalParam()**
  - String：verifier 提交提案的验证人
  - String：pIDID  PIPID
  - BigInteger：endVotingRounds  投票共识轮数量。参考提交升级提案的说明，同时，此接口中此参数的值不能大于对应升级提案中的
  - String：tobeCanceledProposalID  待取消的提案ID

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
Proposal proposal = Proposal.createSubmitTextProposalParam(proposalNodeId,"1");

PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **vote**

> 给提案投票

* **入参**

  - ProgramVersion:ProgramVersion 程序的真实版本，治理rpc接口admin_getProgramVersion获取
  - VoteOption：voteOption   投票类型，YEAS 赞成票，NAYS 反对票，ABSTENTIONS 弃权票
  - String：proposalID   提案ID
  - String：verifier   声明的节点，只能是验证人/候选人

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**：

```java
ProgramVersion programVersion = web3j.getProgramVersion().send().getAdminProgramVersion();
VoteOption voteOption =  VoteOption.YEAS;
String proposalID = "";
String verifier = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";

PlatonSendTransaction platonSendTransaction = proposalContract.voteReturnTransaction(programVersion, voteOption, proposalID, verifier).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getProposal**

>  查询提案

* **入参**

  - String：proposalID   提案id

* **返回值**

```java
CallResponse<Proposal>
```

- CallResponse<Proposal>描述
	- int：code   结果标识，0为成功
	- Proposal：data   Proposal对象数据
	- String：errMsg   错误信息，失败时存在

* **Proposal**：保存单个提案信息的对象
  - String:	   proposalId	提案ID
  - String:    proposer   提案节点ID
  - int:    proposalType   提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案  0x04取消提案
  - String:    piPid   提案PIPID
  - BigInteger:   submitBlock   提交提案的块高
  - BigInteger:   endVotingBlock   提案投票结束的块高
  - BigInteger:   newVersion   升级版本
  - BigInteger:   toBeCanceled   提案要取消的升级提案ID
  - BigInteger:   activeBlock   （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
  - String:   verifier     提交提案的验证人

* **合约使用**

```java
//提案id
String proposalID = "";
CallResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
```

##### **getTallyResult**

> 查询提案结果

* **入参**

  - String：proposalID   提案ID

* **返回值**

```java
CallResponse<TallyResult>
```

- CallResponse<TallyResult>描述
  - int：code   结果标识，0为成功
  - TallyResult：data   TallyResult对象数据
  - String：errMsg   错误信息，失败时存在

* **TallyResult**：保存单个提案结果的对象
  - String:   proposalID   提案ID
  - BigInteger:   yeas   赞成票票数
  - BigInteger:   nays   反对票票数
  - BigInteger:   abstentions   弃权票票数
  - BigInteger:   accuVerifiers   在整个投票期内有投票资格的验证人总数
  - int:   status   提案状态  
  
* **status**
  - Voting：0x01，投票中
  - Pass：0x02，投票通过
  - Failed：0x03，投票失败
  - PreActive：0x04，（升级提案）预生效
  - Active：0x05，（升级提案）生效
  - Canceled：0x06，被取消

* **合约使用**

```java
//提案id
String proposalID ="";
CallResponse<TallyResult> baseResponse = proposalContract.getTallyResult(proposalID).send();
```

##### **getProposalList**

> 查询提案列表

* **入参**

  无

* **返回值**

```java
CallResponse<List<Proposal>>
```

- CallResponse<List<Proposal>>描述
	- int：code   结果标识，0为成功
	- List<Proposal>：data   ProposalList对象数据
	- String：errMsg   错误信息，失败时存在

* **Proposal**：保存单个提案的对象
  - String:	   proposalId	提案ID
  - String:    proposer   提案节点ID
  - int:    proposalType   提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案
  - String:    piPid   提案PIPID
  - BigInteger:   submitBlock   提交提案的块高
  - BigInteger:   endVotingBlock   提案投票结束的块高
  - BigInteger:   newVersion   升级版本
  - String:   toBeCanceled   提案要取消的升级提案ID
  - BigInteger:   activeBlock   （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
  - String:   verifier     提交提案的验证人

* **合约使用**

```java
CallResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
```

##### **declareVersion**

> 版本声明

* **入参**

  - ProgramVersion:ProgramVersion 程序的真实版本，治理rpc接口admin_getProgramVersion获取
  - String：verifier   声明的节点，只能是验证人/候选人

* **返回值**
```
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
ProgramVersion programVersion = web3j.getProgramVersion().send().getAdminProgramVersion();
String verifier = "";

PlatonSendTransaction platonSendTransaction = proposalContract.declareVersionReturnTransaction(programVersion,verifier).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getActiveVersion**

> 查询节点的链生效版本

* **入参**

  无

* **返回值**
```
CallResponse
```

- CallResponse<BigInteger>： 通用应答包
	- int：code   结果标识，0为成功
	- BigInteger：data   版本信息
	- String：errMsg   错误信息，失败时存在

* **合约使用**

```java
CallResponse<BigInteger> baseResponse = proposalContract.getActiveVersion().send();
ProposalUtils.versionInterToStr(baseResponse.getData());
```

###  双签举报相关接口

> PlatON举报惩罚相关的合约接口

#### 加载举报合约

```
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "103";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
SlashContract contract = SlashContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **reportDoubleSign**

> 举报双签

* **入参**

  - DuplicateSignType：DuplicateSignType   枚举，代表双签类型：PREPARE_BLOCK，PREPARE_VOTE，VIEW_CHANGE
  - String：data   单个证据的json值，格式参照[RPC接口Evidences](#evidences_interface)

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
String data = "";	//举报证据
PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, data).send();

TransactionResponse baseResponse = slashContract.getTransactionResponse(platonSendTransaction).send();
```

##### **checkDuplicateSign**

> 查询节点是否已被举报过多签

* **入参**

  - DuplicateSignType：DuplicateSignType   枚举，代表双签类型：prepareBlock，EprepareVote，viewChange
  - String：address   举报的节点地址
  - BigInteger：blockNumber   多签的块高 

* **返回值**

```java
CallResponse
```

- CallResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：data   Y, 可能为零交易Hash，即: 0x000...000
	- String：errMsg   错误信息，失败时存在

* **合约使用**

```java
CallResponse<String> baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0x4F8eb0B21eb8F16C80A9B7D728EA473b8676Cbb3", BigInteger.valueOf(500L)).send();
```

###  锁仓相关接口

> PlatON举报惩罚相关的合约接口

#### 加载锁仓合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RestrictingPlanContract contract = RestrictingPlanContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **createRestrictingPlan**

> 创建锁仓计划

* **入参**

  - String：address   锁仓释放到账账户
  - List<RestrictingPlan>：plan   锁仓计划列表（数组）
    - epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。如果 account 是激励池地址， 那么 period 值是 120（即，30*4） 的倍数。另外，period,每周期的区块数至少要大于最高不可逆区块高度。
    - amount：表示目标区块上待释放的金额。

* **返回值**

```java
TransactionResponse
```

- TransactionResponse： 通用应答包
	- int：code   结果标识，0为成功
	- String：errMsg   错误信息，失败时存在
	- TransactionReceipt：transactionReceipt  交易的回执

* **合约使用**

```java
List<RestrictingPlan> restrictingPlans = new ArrayList<>();
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("100000000000000000000")));
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000000")));

PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(restrictingRecvCredentials.getAddress(), restrictingPlans).send();
TransactionResponse baseResponse = restrictingPlanContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getRestrictingInfo**

> 获取锁仓计划

* **入参**
  - String：address   锁仓释放到账账户

* **返回值**

```java
CallResponse<RestrictingItem> baseResponse
```

- CallResponse<RestrictingItem>描述
	- int：code   结果标识，0为成功
	- RestrictingItem：Data   RestrictingItem对象数据
	- String：errMsg   错误信息，失败时存在

* **RestrictingItem**：保存锁仓信息对象
  - BigInteger：balance    锁仓余额
  - BigInteger：pledge   质押/抵押金额
  - BigInteger：debt   欠释放金额
  - List<RestrictingInfo>：info   锁仓分录信息
* **RestrictingInfo**：保存单个锁仓分录信息的对象
  - BigInteger：blockNumber    释放区块高度
  - BigInteger：amount   释放金额

* **合约使用**

```java
CallResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(restrictingRecvCredentials.getAddress()).send();
```

## 基础API使用

基础`API`包括网络，交易，查询，节点信息，经济模型参数配置等相关的接口，具体参考如下`API`使用说明。

### web3ClientVersion

> 返回当前客户端版本

* **参数**

  无

* **返回值**

```java
Request<?, Web3ClientVersion>
```

Web3ClientVersion属性中的string即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, Web3ClientVersion> request = currentValidWeb3j.web3ClientVersion();
String version = request.send().getWeb3ClientVersion();
```

### web3Sha3

> 返回给定数据的keccak-256（不是标准sha3-256）

* **参数**

  String ：加密前的数据

* **返回值**

```java
Request<?, Web3Sha3>
```

Web3Sha3属性中的string即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String date = "";
Request <?, Web3Sha3> request = currentValidWeb3j.web3Sha3(date);
String resDate = request.send().getWeb3ClientVersion();
```

### netVersion

> 返回当前网络ID

* **参数**

  无

* **返回值**

```java
Request<?, NetVersion>
```

NetVersion属性中的string即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetVersion> request = currentValidWeb3j.netVersion();
String version = request.send().getNetVersion();
```

### netListening

> 如果客户端正在积极侦听网络连接，则返回true

* **参数**

  无

* **返回值**

```java
Request<?, NetListening>
```

NetListening属性中的boolean即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetListening> request = currentValidWeb3j.netListening();
boolean req = request.send().isListening();
```

### netPeerCount

> 返回当前连接到客户端的对等体的数量

* **参数**

  ​	无

* **返回值**

```java
Request<?, NetPeerCount>
```

NetPeerCount属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetPeerCount> request = currentValidWeb3j.netPeerCount();
BigInteger req = request.send().getQuantity();
```

### platonProtocolVersion

> 返回当前platon协议版本

* **参数**

  无

* **返回值**

```java
Request<?, PlatonProtocolVersion>
```

PlatonProtocolVersion属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonProtocolVersion> request = currentValidWeb3j.platonProtocolVersion();
String req = request.send().getProtocolVersion();
```

### platonSyncing

> 返回一个对象，其中包含有关同步状态的数据或false

* **参数**

  无

* **返回值**

```java
Request<?, PlatonSyncing>
```

PlatonSyncing属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonSyncing> request = currentValidWeb3j.platonSyncing();
boolean req = request.send().isSyncing();
```

### platonGasPrice

> 返回gas当前价格

* **参数**

  无

* **返回值**

```java
Request<?, PlatonGasPrice>
```

PlatonGasPrice属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonGasPrice> request = currentValidWeb3j.platonGasPrice();
BigInteger req = request.send().getGasPrice();
```

### platonAccounts

> 返回客户端拥有的地址列表

* **参数**

  无

* **返回值**

```java
Request<?, PlatonAccounts>
```

PlatonAccounts属性中的String数组即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonAccounts> request = currentValidWeb3j.platonAccounts();
List<String> req = request.send().getAccounts();
```

### platonBlockNumber

> 返回当前最高块高

* **参数**

  无

* **返回值**

```java
Request<?, PlatonBlockNumber>
```

PlatonBlockNumber属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonBlockNumber> request = currentValidWeb3j.platonBlockNumber();
BigInteger req = request.send().getBlockNumber();
```

### platonGetBalance

> 返回查询地址余额

* **参数**
    - String ： address 需要查询的地址
    - DefaultBlockParameter: 
      - DefaultBlockParameterName.LATEST  最新块高(默认)
      - DefaultBlockParameterName.EARLIEST 最低块高
      - DefaultBlockParameterName.PENDING 未打包交易
      - DefaultBlockParameter.valueOf(BigInteger  blockNumber) 指定块高

* **返回值**

```java
Request<?, PlatonGetBalance>
```

PlatonGetBalance属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String address = "";
Request<?, PlatonGetBalance> request = web3j.platonGetBalance(address,DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getBalance();
```

### platonGetStorageAt

> 从给定地址的存储位置返回值

* **参数**
    - String : address  存储地址
    - BigInteger: position 存储器中位置的整数
    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高

* **返回值**

```java
Request<?, PlatonGetStorageAt>
```

PlatonGetStorageAt属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetStorageAt> request = currentValidWeb3j.platonGetStorageAt(address ,BigInteger.ZERO,DefaultBlockParameterName.LATEST );
String req = request.send().getData();
```

### platonGetBlockTransactionCountByHash

> 根据区块hash查询区块中交易个数

* **参数**
	- String : blockHash 区块hash

* **返回值**

```java
Request<?, PlatonGetBlockTransactionCountByHash>
```

PlatonGetBlockTransactionCountByHash属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockhash = "";
Request <?, PlatonGetBlockTransactionCountByHash> request = currentValidWeb3j.platonGetBlockTransactionCountByHash(blockhash);
BigInteger req = request.send().getTransactionCount();
```

### platonGetTransactionCount

> 根据地址查询该地址发送的交易个数

* **参数**
    - String : address 查询地址
    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高

* **返回值**

```java
Request<?, PlatonGetTransactionCount>
```

PlatonGetTransactionCount属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetTransactionCount> request = currentValidWeb3j.platonGetTransactionCount(address,DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getTransactionCount();
```

### platonGetBlockTransactionCountByNumber

> 根据区块块高，返回块高中的交易总数

* **参数**
    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高

* **返回值**

```java
Request<?, PlatonGetBlockTransactionCountByNumber>
```

PlatonGetBlockTransactionCountByNumber属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonGetBlockTransactionCountByNumber> request = currentValidWeb3j.platonGetBlockTransactionCountByNumber(DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getTransactionCount();
```

### platonGetCode

>  返回给定地址的代码

* **参数**
    - String ： address 地址/合约

    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高

* **返回值**

```java
Request<?, PlatonGetCode>
```

PlatonGetCode属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetCode> request = currentValidWeb3j.platonGetCode(address,DefaultBlockParameterName.LATEST);
String req = request.send().getCode();
```

### platonSign

>  数据签名

* **参数**
    - String ： address   地址
    - String :  sha3HashOfDataToSign  待签名数据

* **返回值**

```java
Request<?, PlatonSign>
```

PlatonSign属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
String sha3HashOfDataToSign   = "";
Request <?, PlatonSign> request = currentValidWeb3j.platonSign(address,DefaultBlockParameterName.LATEST);
String req = request.send().getSignature();
```

备注：地址必须提前先解锁

### platonSendTransaction

>  发送服务代签名交易

* **参数**
    - Transaction : Transaction: 交易结构
      - String : from : 交易发送地址
      - String : to : 交易接收方地址
      - BigInteger ： gas ：  本次交易gas用量上限 
      - BigInteger ： gasPrice ： gas价格
      - BigInteger ：value ： 转账金额
      - String ：data ： 上链数据
      - BigInteger ：nonce ： 交易唯一性标识
        - 调用platonGetTransactionCount，获取from地址作为参数，获取到该地址的已发送交易总数
        - 每次使用该地址nonce +1 

* **返回值**

```java
Request<?, PlatonSendTransaction>
```

PlatonSendTransaction属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonSendTransaction(transaction);
String req = request.send().getTransactionHash();
```

### platonSendRawTransaction

>  发送交易

* **参数**
	- String : data : 钱包签名后数据

* **返回值**

```java
Request<?, PlatonSendTransaction>
```

PlatonSendTransaction属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String  data = "";
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonSendRawTransaction(data);
String req = request.send().getTransactionHash();
```

### platonCall

>   执行一个消息调用交易，消息调用交易直接在节点旳VM中执行而 不需要通过区块链的挖矿来执行 

* **参数**
    - Transaction : Transaction: 交易结构
      - String : from : 交易发送地址
      - String : to : 交易接收方地址
      - BigInteger ： gas ：  本次交易gas用量上限 
      - BigInteger ： gasPrice ： gas价格
      - BigInteger ：value ： 转账金额
      - String ：data ： 上链数据
      - BigInteger ：nonce ： 交易唯一性标识
        - 调用platonGetTransactionCount，获取from地址作为参数，获取到该地址的已发送交易总数
        - 每次使用该地址nonce +1 

* **返回值**

```java
Request<?, PlatonCall>
```

PlatonCall属性中的String即为对应存储数据

* **示例**

```javas
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonCall(transaction);
String req = request.send().getValue();
```

### platonEstimateGas

>   估算合约方法gas用量 

* **参数**
    - Transaction : Transaction: 交易结构
      - String : from : 交易发送地址
      - String : to : 交易接收方地址
      - BigInteger ： gas ：  本次交易gas用量上限 
      - BigInteger ： gasPrice ： gas价格
      - BigInteger ：value ： 转账金额
      - String ：data ： 上链数据
      - BigInteger ：nonce ： 交易唯一性标识
        - 调用platonGetTransactionCount，获取from地址作为参数，获取到该地址的已发送交易总数
        - 每次使用该地址nonce +1 

* **返回值**

```java
Request<?, PlatonEstimateGas>
```

PlatonEstimateGas属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonEstimateGas> request = currentValidWeb3j.platonEstimateGas(transaction);
BigInteger req = request.send().getAmountUsed();
```

### platonGetBlockByHash

>  根据区块hash查询区块信息

* **参数**
    - String ： blockHash  区块hash
    - boolean : 
      -  true ： 区块中带有完整的交易列表
      -  false： 区块中只带交易hash列表

* **返回值**

```java
Request<?, PlatonBlock>
```

PlatonBlock属性中的Block即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash  = "";

Request <?, PlatonBlock> request = currentValidWeb3j.platonGetBlockByHash(blockHash,true);
Block req = request.send().getBlock();
```

### platonGetBlockByNumber

>  根据区块高度查询区块信息

* **参数**
    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高
    - boolean : 
      -  true ： 区块中带有完整的交易列表
      -  false： 区块中只带交易hash列表

* **返回值**

```java
Request<?, PlatonBlock>
```

PlatonBlock属性中的Block即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonBlock> request = currentValidWeb3j.platonGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.ZERO) ,true);
Block req = request.send().getBlock();
```

### platonGetTransactionByBlockHashAndIndex

>  根据区块hash查询区块中指定序号的交易

* **参数**
    - String : blockHash  区块hash
    - BigInteger ： transactionIndex  交易在区块中的序号

* **返回值**

```java
Request<?, PlatonTransaction>
```

PlatonTransaction属性中的Transaction即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash    = "";
Request <?, PlatonTransaction> request = currentValidWeb3j.platonGetTransactionByHash(blockHash,BigInteger.ZERO);
Optional<Transaction> req = request.send().getTransaction();
```

### platonGetTransactionByBlockNumberAndIndex

>  根据区块高度查询区块中指定序号的交易

* **参数**
    - DefaultBlockParameter: 
      -  DefaultBlockParameterName.LATEST  最新块高(默认)
      -  DefaultBlockParameterName.EARLIEST 最低块高
      -  DefaultBlockParameterName.PENDING 未打包交易
      -  DefaultBlockParameter.valueOf(BigInteger blockNumber) 指定块高
    - BigInteger ： transactionIndex  交易在区块中的序号

* **返回值**

```java
Request<?, PlatonTransaction>
```

PlatonTransaction属性中的Transaction即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash    = "";
Request <?, PlatonTransaction> request = currentValidWeb3j.platonGetTransactionByHash(DefaultBlockParameter.valueOf(BigInteger.ZERO) ,BigInteger.ZERO);
Optional<Transaction> req = request.send().getTransaction();
```

### platonGetTransactionReceipt

>  根据交易hash查询交易回执

* **参数**
	- String : transactionHash  交易hash

* **返回值**

```java
Request<?, PlatonGetTransactionReceipt>
```

PlatonGetTransactionReceipt属性中的Transaction即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash    = "";
Request <?, PlatonGetTransactionReceipt> request = currentValidWeb3j.platonGetTransactionReceipt(DefaultBlockParameter.valueOf(BigInteger.ZERO) ,BigInteger.ZERO);
Optional<TransactionReceipt> req = request.send().getTransactionReceipt();
```

### platonNewFilter

>   创建一个过滤器，以便在客户端接收到匹配的whisper消息时进行通知 

* **参数**
    - PlatonFilter:  PlatonFilter :
      - SingleTopic : 

* **返回值**

```java
Request<?, PlatonFilter>
```

PlatonFilter属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
org.web3j.protocol.core.methods.request.PlatonFilter filter = new org.web3j.protocol.core.methods.request.PlatonFilter();
filter.addSingleTopic("");
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewFilter(filter);
BigInteger req = request.send().getFilterId();
```

### platonNewBlockFilter

>   在节点中创建一个过滤器，以便当新块生成时进行通知。要检查状态是否变化 

* **参数**

  无

* **返回值**

```java
Request<?, PlatonFilter>
```

PlatonFilter属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewBlockFilter();
BigInteger req = request.send().getFilterId();
```

### platonNewPendingTransactionFilter

>    在节点中创建一个过滤器，以便当产生挂起交易时进行通知。 要检查状态是否发生变化 

* **参数**

  无

* **返回值**

```java
Request<?, PlatonFilter>
```

PlatonFilter属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter();
BigInteger req = request.send().getFilterId();
```

### platonNewPendingTransactionFilter

>  写在具有指定编号的过滤器。当不在需要监听时，总是需要执行该调用  

* **参数**

  无

* **返回值**

```java
Request<?, PlatonFilter>
```

PlatonFilter属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter();
BigInteger req = request.send().getFilterId();
```

### platonUninstallFilter

>     写在具有指定编号的过滤器。当不在需要监听时，总是需要执行该调用 

* **参数**
	- BigInteger  : filterId :  过滤器编号 

* **返回值**

```java
Request<?, PlatonUninstallFilter>
```

PlatonUninstallFilter属性中的boolean即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonUninstallFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter(BigInteger.ZERO);
boolean req = request.send().isUninstalled();
```

### platonGetFilterChanges

>    轮询指定的过滤器，并返回自上次轮询之后新生成的日志数组 

* **参数**
	- BigInteger  : filterId :  过滤器编号 

* **返回值**

```java
Request<?, PlatonLog>
```

PlatonLog属性中的LogResult数组即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonLog> request = currentValidWeb3j.platonGetFilterChanges(BigInteger.ZERO);
List<PlatonLog.LogResult> req = request.send().getLogs();
```

### platonGetFilterLogs

>     轮询指定的过滤器，并返回自上次轮询之后新生成的日志数组。

* **参数**
	- BigInteger  : filterId :  过滤器编号 

* **返回值**

```java
Request<?, PlatonLog>
```

PlatonLog属性中的LogResult数组即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonLog> request = currentValidWeb3j.platonGetFilterLogs(BigInteger.ZERO);
List<PlatonLog.LogResult> req = request.send().getLogs();
```

### platonGetLogs

>    返回指定过滤器中的所有日志 

* **参数**
    - PlatonFilter:  PlatonFilter :
      - SingleTopic : 

* **返回值**

```java
Request<?, PlatonLog>
```

PlatonLog属性中的BigInteger即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
org.web3j.protocol.core.methods.request.PlatonFilter filter = new org.web3j.protocol.core.methods.request.PlatonFilter();
filter.addSingleTopic("");
Request <?, PlatonLog> request = currentValidWeb3j.platonGetLogs(filter);
List<LogResult> = request.send().getLogs();
```

### platonPendingTransactions
>查询待处理交易

* **参数**

   无

* **返回值**

```java
Request<?, PlatonPendingTransactions>
```

PlatonPendingTransactions属性中的transactions即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, PlatonPendingTransactions> req = currentValidWeb3j.platonPendingTx();
EthPendingTransactions res = req.send();
List<Transaction> transactions = res.getTransactions();
```

### dbPutString

>   在本地数据库中存入字符串。

* **参数**
    - String :  databaseName :   数据库名称 
    - String : keyName :  键名 
    - String : stringToStore :   要存入的字符串 

* **返回值**

```java
Request<?, DbPutString>
```

DbPutString属性中的boolean即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
String stringToStore;
Request <?, DbPutString> request = currentValidWeb3j.dbPutString(databaseName,keyName,stringToStore);
List<DbPutString> = request.send().valueStored();
```

### dbGetString

>    从本地数据库读取字符串 

* **参数**
    - String :  databaseName :   数据库名称 
    - String : keyName :  键名 

* **返回值**

```java
Request<?, DbGetString>
```

DbGetString属性中的String即为对应存储数据

**示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
Request <?, DbGetString> request = currentValidWeb3j.dbGetString(databaseName,keyName);
String req  = request.send().getStoredValue();
```

### dbPutHex

>     将二进制数据写入本地数据库 

* **参数**
    - String :  databaseName :   数据库名称 
    - String : keyName :  键名 
    - String : dataToStore :   要存入的二进制数据 

* **返回值**

```java
Request<?, DbPutHex>
```

DbPutHex属性中的boolean即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
String dataToStore;
Request <?, DbPutHex> request = currentValidWeb3j.dbPutHex(databaseName,keyName,dataToStore);
boolean req  = request.send().valueStored();
```

### dbGetHex

>     从本地数据库中读取二进制数据  

* **参数**
    - String :  databaseName :   数据库名称 
    - String : keyName :  键名 

* **返回值**

```java
Request<?, DbGetHex>
```

DbGetHex属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
Request <?, DbGetHex> request = currentValidWeb3j.dbGetHex(databaseName,keyName);
String req  = request.send().getStoredValue();
```

### platonEvidences

>    返回双签举报数据

* **参数**

  无

* **出参**

| 参数    | 类型   | 描述       |
| ------- | ------ | ---------- |
| jsonrpc | string | rpc版本号  |
| id      | int    | id序号     |
| result  | string | 证据字符串 |

result为证据字符串，包含3种证据类型，分别是：duplicatePrepare、duplicateVote、duplicateViewchange
每种类型包含多个证据，所以是数组结构，解析时需注意。

* **duplicatePrepare**

```text
{
    "prepare_a":{
        "epoch":0, 			//共识轮epoch值
        "view_number":0,	//共识轮view值
        "block_hash":"0xf41006b64e9109098723a37f9246a76c236cd97c67a334cfb4d54bc36a3f1306",
        //区块hash
        "block_number":500,		//区块number
        "block_index":0,		//区块在一轮view中的索引值
        "validate_node":{
            "index":0,			//验证人在一轮epoch中的索引值
            "address":"0x0550184a50db8162c0cfe9296f06b2b1db019331",		//验证人地址
            "NodeID":"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050",		//验证人nodeID
            "blsPubKey":"5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811"		//验证人bls公钥
        },
        "signature":"0xa7205d571b16696b3a9b68e4b9ccef001c751d860d0427760f650853fe563f5191f2292dd67ccd6c89ed050182f19b9200000000000000000000000000000000"	//消息签名
    }
 }
```

* **duplicateVote**

```text 
{
    "voteA":{
        "epoch":0, 			//共识轮epoch值
        "view_number":0,	//共识轮view值
        "block_hash":"0xf41006b64e9109098723a37f9246a76c236cd97c67a334cfb4d54bc36a3f1306",
        //区块hash
        "block_number":500,		//区块number
        "block_index":0,		//区块在一轮view中的索引值
        "validate_node":{
            "index":0,			//验证人在一轮epoch中的索引值
            "address":"0x0550184a50db8162c0cfe9296f06b2b1db019331",		//验证人地址
            "NodeID":"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050",		//验证人nodeID
            "blsPubKey":"5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811"		//验证人bls公钥
        },
        "signature":"0xa7205d571b16696b3a9b68e4b9ccef001c751d860d0427760f650853fe563f5191f2292dd67ccd6c89ed050182f19b9200000000000000000000000000000000"	//消息签名
    }
 }
```

* **duplicateViewchange**

```text
{
    "viewA":{
        "epoch":0, 			//共识轮epoch值
        "view_number":0,	//共识轮view值
        "block_hash":"0xf41006b64e9109098723a37f9246a76c236cd97c67a334cfb4d54bc36a3f1306",
        //区块hash
        "block_number":500,		//区块number
        "block_index":0,		//区块在一轮view中的索引值
        "validate_node":{
            "index":0,			//验证人在一轮epoch中的索引值
            "address":"0x0550184a50db8162c0cfe9296f06b2b1db019331",		//验证人地址
            "NodeID":"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050",		//验证人nodeID
            "blsPubKey":"5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811"		//验证人bls公钥
        },
        "signature":"0xa7205d571b16696b3a9b68e4b9ccef001c751d860d0427760f650853fe563f5191f2292dd67ccd6c89ed050182f19b9200000000000000000000000000000000"	//消息签名
    }
 }
```

* **返回值**

```java
Request<?, PlatonEvidences>
```

PlatonEvidences属性中的Evidences对象即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, PlatonEvidences> req = currentValidWeb3j.platonEvidences();
Evidences evidences = req.send().getEvidences();
```

### getProgramVersion

>    获取代码版本

* **参数**

  无

* **返回值**

```java
Request<?, AdminProgramVersion>
```

AdminProgramVersion属性中的ProgramVersion对象即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, AdminProgramVersion> req = currentValidWeb3j.getProgramVersion();
ProgramVersion programVersion = req.send().getAdminProgramVersion();
```

* **ProgramVersion 对象解析**
    - BigInteger ： version ： 代码版本
    - String ： sign ： 代码版本签名

### getSchnorrNIZKProve

>    获取bls的证明

* **参数**

  无

* **返回值**

```java
Request<?, AdminSchnorrNIZKProve>
```

AdminSchnorrNIZKProve属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, AdminProgramVersion> req = currentValidWeb3j.getSchnorrNIZKProve();
String res = req.send().getAdminSchnorrNIZKProve();
```

### getEconomicConfig

>    获取PlatON参数配置

* **参数**

  无

* **返回值**

```java
Request<?, DebugEconomicConfig>
```

DebugEconomicConfig属性中的String即为对应存储数据

* **示例**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, DebugEconomicConfig> req = currentValidWeb3j.getEconomicConfig();
String debugEconomicConfig = req.send().getEconomicConfigStr();
```

## Solidity合约调用

将Solidity智能合约部署到区块链上时，必须先将其编译成字节码的格式，然后将其作为交易的一部分发送。Java SDK 将帮你生成Solidity智能合约对应的Java包装类，可以方便的部署Solidity智能合约以及调用Solidity智能合约中的交易方法、事件和常量方法。

### 编译solidity源代码

* 通过`solc`编译器编译solidity源代码([solc下载](https://github.com/PlatONnetwork/solidity/releases))：

```shell
$ solc <contract>.sol --bin --abi --optimize -o <output-dir>/
```

`bin`，输出包含十六进制编码的solidity二进制文件以提供交易请求。
`abi`，输出一个solidity的应用程序二进制接口（`ABI`）文件，它详细描述了所有可公开访问的合约方法及其相关参数。`abi`文件也用于生成solidity智能合约对应的Java包装类。

* 使用`platon-truffle`编译solidity源代码([platon-truffle开发工具安装参考](https://github.com/PlatONnetwork/platon-truffle/tree/feature/evm)|[platon-truffle开发工具使用手册](https://platon-truffle.readthedocs.io/en/v0.1.0/index.html))：

> **step1.** 使用platon-truffle初始化项目

```
在安装有platon-truffle的服务器上面先初始化一个工程。
mkdir HelloWorld
cd HelloWorld
truffle init
提示如下表示成功：

  ✔ Preparing to download
  ✔ Downloading
  ✔ Cleaning up temporary files
  ✔ Setting up box

  Unbox successful. Sweet!

  Commands:

    Compile:        truffle compile
    Migrate:        truffle migrate
    Test contracts: truffle test
```

> **step2.** 将HelloWorld.sol放入HelloWorld/contracts目录下

```
guest@guest:~/HelloWorld/contracts$ ls
HelloWorld.sol  Migrations.sol
```

> **step3.** 修改truffle-config.js文件，将编译器版本修改成“^0.5.13”

```
compilers: {
    solc: {
       version: "^0.5.13",    // Fetch exact version from solc-bin (default: truffle's version)
      // docker: true,        // Use "0.5.1" you've installed locally with docker (default: false)
      // settings: {          // See the solidity docs for advice about optimization and evmVersion
      //  optimizer: {
      //    enabled: false,
      //    runs: 200
      //  },
      //  evmVersion: "byzantium"
       }
    }
}
```

> **step4.** 执行truffle compile编译合约

```
guest@guest:~/HelloWorld$ truffle compile

Compiling your contracts...

 Compiling ./contracts/HelloWorld.sol
 Compiling ./contracts/Migrations.sol

 compilation warnings encountered:

Warning: This is a pre-release compiler version, please do not use it in production.

 Artifacts written to /home/guest/hudenian/HelloWorld/build/contracts
 Compiled successfully using:
    solc: 0.5.13-develop.2020.1.2+commit.9ff23752.mod.Emscripten.clang
```

### Solidity智能合约Java包装类

Java SDK支持从`abi`文件中自动生成Solidity智能合约对应的Java包装类。

* 通过命令行工具生成Java包装类：

```shell
$ platon-web3j solidity generate [--javaTypes|--solidityTypes] /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name
```

* 直接调用Java SDK中的工具类生成Java包装类：

```java
String args[] = {"generate", "/path/to/<smart-contract>.bin", "/path/to/<smart-contract>.abi", "-o", "/path/to/src/main/java", "-p" , "com.your.organisation.name"};
org.web3j.codegen.SolidityFunctionWrapperGenerator.run(args);
```

其中`bin`和`abi`文件是编译solidity源代码以后生成的。

Solidity智能合约对应的Java包装类支持的主要功能：
- 构建与部署
- 确定合约有效性
- 调用交易和事件
- 调用常量方法

#### 构建与部署智能合约

智能合约的构建和部署使用包装类中的deploy方法：

```java
YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <transactionManager>, contractGasProvider,
        [<initialValue>,] <param1>, ..., <paramN>).send();
```

这个方法将在区块链上部署智能合约。部署成功以后，它将会返回一个智能合约的包装类实例，包含智能合约的地址。

如果你的智能合约在构造上接受LAT转账，则需要初始化参数值<initialValue>。

通过智能合约的地址也可以创建智能合约对应的Java包装类的实例：

```java
YourSmartContract contract = YourSmartContract.load(
        "0x<address>|<ensName>", web3j, transactionManager, contractGasProvider);
```

#### 智能合约有效性

使用此方法，可以验证智能合约的有效性。只有在合约地址中部署的字节码与智能合约封装包中的字节码匹配时才会返回`true`。

```java
contract.isValid();  // returns false if the contract bytecode does not match what's deployed
                     // at the provided address
```

#### 交易管理器
Java SDK提供了一个交易管理器`TransactionManager`来控制你连接到PlatON客户端的方式。默认采用`RawTransactionManager`。
`RawTransactionManager`需要指定链ID。防止一个链的交易被重新广播到另一个链上：

```java
TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 100L);
```

你可以通过以下请求来获得链ID：

```java
web3j.netVersion().send().getNetVersion();
```

除了`RawTransactionManager`之外，Java SDK还提供了一个客户端交易管理器`ClientTransactionManager`，它将你的交易签名工作交给你正在连接的PlatON客户端。
此外，还有一个`ReadonlyTransactionManager`，用于只从智能合约中查询数据，而不与它进行交易。

#### 调用交易和事件
对于所有交易的方法，只返回与交易关联的交易收据。

```java
TransactionReceipt transactionReceipt = contract.someMethod(<param1>, ...).send();
```

通过交易收据，可以提取索引和非索引的事件参数。

```java
List<SomeEventResponse> events = contract.getSomeEvents(transactionReceipt);
```

或者，你可以使用可观察的过滤器Observable filter，监听与智能合约相关联的事件：

```java
contract.someEventObservable(startBlock, endBlock).subscribe(event -> ...);
```

#### 调用常量方法

常量方法只做查询，而不改变智能合约的状态。

```java
Type result = contract.someMethod(<param1>, ...).send();
```