

# 入门

​		根据构建工具的不同，使用以下方式将相关依赖项添加到项目中：

- 使用要求jdk1.8以上

## maven

> 项目配置:	
```
<repository>
	 <id>platon-public</id>
	 <url>https://sdk.platon.network/nexus/content/groups/public/</url>
</repository>
```


> maven引用方式:
```
<dependency>
      <groupId>com.platon.client</groupId>
      <artifactId>core</artifactId>
      <version>0.7.3.4</version>
</dependency>
```

## gradle

> 项目配置:	
```
repositories {
     maven { url "https://sdk.platon.network/nexus/content/groups/public/" }
}
```

>gradle引用方式:
```
compile "com.platon.client:core:0.7.3.4"
```

# 使用API

封装了一些API提供给开发者使用，包括以下两个部分：
- 系统合约：包括经济模型和治理等相关的合约接口
- 基础API：包括网络，交易，查询，节点信息，经济模型参数配置等相关的接口

## 系统合约 

### 质押相关接口
> PlatON经济模型中质押合约相关的接口

#### 加载质押合约
```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
StakingContract contract = StakingContract.load(web3j, credentials, chainId)
```

#### 接口说明

##### **StakingReturnTransaction**

> 节点候选人申请质押

* **入参**

  - String：nodeId   节点id,16进制格式，0x开头
  - BigInteger：amount   质押的von，质押金额必须大于等于1000000LAT
  - Enum：stakingAmountType   表示使用账户自由金额还是锁仓金额做质押，0: 自由金额，1: 锁仓金额
  - String：benifitAddress   收益账户
  - String：nodeName   被质押节点的名称
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥
  - String：webSite   节点的第三方主页(有长度限制，表示该节点的主页)
  - String：details   节点的描述(有长度限制，表示该节点的描述)

* **返回值**

```
BaseRespons
```

* **BaseResponse描述**

```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
//表示使用账户自由金额
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
		//20bytes 用于接受出块奖励和质押奖励的收益账户
    	String benifitAddress = "0xXXXXXXXXX";
    	//外部Id(有长度限制，给第三方拉取节点描述的Id)目前为用户keybase账户公钥
    	String externalId = "";
    	//自定义节点名称
        String nodeName = "integration-node1";
        //节点的第三方主页(有长度限制，表示该节点的主页)
        String webSite = "https://www.platon.network/#/";
        //节点的描述(有长度限制，表示该节点的描述)
        String details = "integration-node1-details";
        //质押的von
        BigDecimal stakingAmount = Convert.toVon("1000000", Unit.LAT).add(BigDecimal.valueOf(1L));
    	
        PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
                    .setNodeId(nodeId)
                    .setAmount(new BigInteger(stakingAmount))
                    .setStakingAmountType(stakingAmountType)
                    .setBenifitAddress(benifitAddress)
                    .setExternalId(externalId)
                    .setNodeName(nodeName)
                    .setWebSite(webSite)
                    .setDetails(details)
                    .setBlsPubKey(blsPubKey)
                    .setProcessVersion(stakingContract.getProgramVersion())
                    .setBlsProof(stakingContract.getAdminSchnorrNIZKProve())
                    .build()).send(); 
        BaseResponse baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
        return baseResponse;
```

##### **UnStakingReturnTransaction**
> 节点撤销质押(一次性发起全部撤销，多次到账)

* **入参**
  - String：nodeId   节点id，16进制格式，0x开头

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**
```java
//节点id
String nodeId = "0x6bad331aa2ec6096b2b6034570e1761d687575b38c3afc3a3b5f892dac4c86d0fc59ead0f0933ae041c0b6b43a7261f1529bad5189be4fba343875548dc9efd3"; 

//调用接口
PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();

BaseResponse baseResponse = stakingContract.getUnStakingResult(platonSendTransaction).send();
```

##### **UpdateStakingInfoReturnTransaction**
> 修改质押信息

* **入参**
  - String：nodeId   节点id,16进制格式,0x开头
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id),目前为keybase账户公钥
  - String：benifitAddress   收益账户
  - String：nodeName   被质押节点的名称
  - String：webSite   节点的第三方主页(有长度限制，表示该节点的主页)
  - String：details   节点的描述(有长度限制，表示该节点的描述)

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
//修改后的收益地址
String benifitAddress = benefitCredentials.getAddress();
		//外部Id(有长度限制，给第三方拉取节点描述的Id)目前为用户keybase账户公钥
    	String externalId = "";
    	//被质押节点的名称
        String nodeName = "integration-node1-u";
        //节点的第三方主页
        String webSite = "https://www.platon.network/#/";
        //节点的描述
        String details = "integration-node1-details-u";
    
PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
        		.setBenifitAddress(benifitAddress)
        		.setExternalId(externalId)
        		.setNodeId(nodeId)
        		.setNodeName(nodeName)
        		.setWebSite(webSite)
        		.setDetails(details)
        		.build()).send();

BaseResponse baseResponse = stakingContract.getUpdateStakingInfoResult(platonSendTransaction).send();
```

##### **AddStakingReturnTransaction**
> 增持质押，增加已质押节点质押金

* **入参**
    - String：nodeId   节点id，16进制格式，0x开头
    - Enum：stakingAmountType   表示使用账户自由金额还是锁仓金额做质押，0: 自由金额，1: 锁仓金额
    - BigInteger：addStakingAmount   增持的金额

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**
```java
//节点id
String nodeId = "0x6bad331aa2ec6096b2b6034570e1761d687575b38c3afc3a3b5f892dac4c86d0fc59ead0f0933ae041c0b6b43a7261f1529bad5189be4fba343875548dc9efd3"; 
//表示使用账户自由金额还是账户的锁仓金额做质押
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
        BigDecimal addStakingAmount = Convert.toVon("4000000", Unit.LAT)
//接口调用    	
        PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, addStakingAmount.toBigInteger()).send();
BaseResponse baseResponse = stakingContract.getAddStakingResult(platonSendTransaction).send();
```
##### **GetStakingInfo**

> 查询当前节点的质押信息

* **入参**

  - String：nodeId   节点id，16进制格式，0x开头

* **返回值**
```
BaseResponse<Node> baseRespons
```

* **BaseResponse<Node>描述**

```java
{
	"Code":int,                             //是否成功 1:成功  0:失败
	"Data":Node                          	//返回数据Node对象
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **Node**

  node对象，表示：**{查询当前节点的质押信息}**

```json
 {
 		"BenefitAddress": "0x1000000000000000000000000000000000000003",
 		"Details": "The PlatON Node",
 		"ExternalId": "",
 		"NodeId": "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f",
 		"NodeName": "platon.node.1",
 		"ProgramVersion": 1792,
 		"Released": 1500000000000000000000000,
 		"ReleasedHes": 0,
 		"RestrictingPlan": 0,
 		"RestrictingPlanHes": 0,
 		"Shares": 1500000000000000000000000,
 		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
 		"StakingBlockNum": 0,
 		"StakingEpoch": 0,
 		"StakingTxIndex": 0,
 		"Status": 0,
 		"ValidatorTerm": 0,
 		"Website": "www.platon.network"
 	}
```

* **Java SDK合约使用**

```java
//节点id
String nodeId = "0x6bad331aa2ec6096b2b6034570e1761d687575b38c3afc3a3b5f892dac4c86d0fc59ead0f0933ae041c0b6b43a7261f1529bad5189be4fba343875548dc9efd3"; 
BaseResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
Node node = baseResponse.data;
```

### 委托相关接口

> PlatON经济模型中委托人相关的合约接口

#### 加载委托合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
delegateContract contract = DelegateContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **DelegateReturnTransaction**

> 发起委托，委托已质押节点，委托给某个节点增加节点权重来获取收入 

* **入参**
  - String：nodeId   节点id，16进制格式，0x开头
  - Enum：stakingAmountType   表示使用账户自由金额还是锁仓金额做质押，0: 自由金额，1: 锁仓金额
  - BigInteger：amount   委托的金额(按照最小单位算，1LAT = 10**18 von)

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
//节点id
String nodeId = "0x6bad331aa2ec6096b2b6034570e1761d687575b38c3afc3a3b5f892dac4c86d0fc59ead0f0933ae041c0b6b43a7261f1529bad5189be4fba343875548dc9efd3"; 
 //表示使用账户自由金额还是账户的锁仓金额做质押， 
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
		//委托的金额(按照最小单位算，1LAT = 10**18 von)
        BigDecimal amount = Convert.toVon("500000", Unit.LAT);
    	
        PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, stakingAmountType, amount.toBigInteger()).send();
        BaseResponse baseResponse = delegateContract.getDelegateResult(platonSendTransaction).send(); 
```

##### **GetRelatedListByDelAddr**

> 查询当前账户地址所委托的节点的NodeID和质押Id

* **入参**
  - String：address   委托人的账户地址

* **返回值**
```
BaseResponse<List<DelegationIdInfo>> baseRespons
```

* **BaseResponse<List<DelegationIdInfo>>描述**

```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":List<DelegationIdInfo>          //返回数据DelegationIdInfoList数组
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **List<DelegationIdInfo>描述**

委托信息列表，表示：**[{查询当前账户地址所委托的节点的NodeID和质押Id}]**

```json
 [{
 		"BenefitAddress": "0x1000000000000000000000000000000000000003",
 		"Details": "The PlatON Node",
 		"ExternalId": "",
 		"NodeId": "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f",
 		"NodeName": "platon.node.1",
 		"ProgramVersion": 1792,
 		"Released": 1500000000000000000000000,
 		"ReleasedHes": 0,
 		"RestrictingPlan": 0,
 		"RestrictingPlanHes": 0,
 		"Shares": 1500000000000000000000000,
 		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
 		"StakingBlockNum": 0,
 		"StakingEpoch": 0,
 		"StakingTxIndex": 0,
 		"Status": 0,
 		"Website": "www.platon.network"
 	}
 ]
```

* **Java SDK合约使用**

```java
BaseResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateCredentials.getAddress()).send();
List<DelegationIdInfo> DelegationIdInfoList = baseResponse.data;
```

##### **GetDelegateInfo**

> 查询当前单个委托信息

* **入参**

  - String：address   委托人的账户地址

  - String：nodeId   节点id，16进制格式，0x开头
  - BigInteger：stakingBlockNum   发起质押时的区块高度

* **返回值**
```
BaseResponse<Delegation>
```

* **BaseResponse<Delegation>描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":Delegation                      //返回数据Delegation对象
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **Delegation描述**

json格式字符串，内部结构为一个数组，表示：**[{查询当前单个委托信息}]**

```json
 [{
 		"delegateAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
 		"stakingBlockNum": "",
 		"NodeId": "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f",
 		"delegateEpoch": 12,
 		"delegateReleased": 1500000000000000000000000,
 		"delegateReleasedHes": 1500000000000000000000000,
 		"delegateLocked": 1500000000000000000000000,
 		"delegateLockedHes": 1500000000000000000000000,
		"delegateReduction": 1500000000000000000000000
 	}
 ]
```

* **Java SDK合约使用**

```java
//节点id
String nodeId = "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f";
//委托地址
String address = "0xc1f330b214668beac2e6418dd651b09c759a4bf5";
//质押区块高度
BigInteger stakingNumber = new BigInteger("10888");
BaseResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, address, stakingBlockNum).send();
Delegation delegation = baseResponse.data;
```

##### **UnDelegateReturnTransaction**

> 减持/撤销委托(全部减持就是撤销)

* **入参**
  - String：nodeId   节点id，16进制格式，0x开头
  - BigInteger：stakingBlockNum   委托节点的质押块高，代表着某个node的某次质押的唯一标示
  - BigInteger：stakingAmount     减持的委托金额(按照最小单位算，1LAT = 10**18 von)

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
//节点id
String nodeId = "0x6bad331aa2ec6096b2b6034570e1761d687575b38c3afc3a3b5f892dac4c86d0fc59ead0f0933ae041c0b6b43a7261f1529bad5189be4fba343875548dc9efd3"; 
		//减持的金额
        BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
        //委托节点的质押块高，代表着某个node的某次质押的唯一标示
        BigInteger stakingBlockNum = new BigInteger("12134");
        PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingBlockNum, stakingAmount.toBigInteger()).send();
BaseResponse baseResponse = delegateContract.getUnDelegateResult(platonSendTransaction).send();
```

### 节点相关合约

> PlatON经济模型中委托人相关的合约接口

#### 加载节点合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
nodeContract contract = NodeContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **GetVerifierList**

> 查询当前结算周期的验证人队列

* **入参**

  无

* **返回值**

```
BaseResponse<List<Node>> baseResponse
```

**BaseResponse<List<Node>>描述**


```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":List<Node>                      //返回数据nodeList数组
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **List<Node>描述**

内部节点node数组列表，表示：**[{当前结算周期的验证人队列}]**

```json
[ {
		"BenefitAddress": "0x1000000000000000000000000000000000000003",
		"Details": "The PlatON Node",
		"ExternalId": "",
		"NodeId": "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f",
		"NodeName": "platon.node.1",
		"ProgramVersion": 1792,
		"Shares": 1500000000000000000000000,
		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
		"StakingBlockNum": 0,
		"StakingTxIndex": 0,
		"ValidatorTerm": 0,
		"Website": "www.platon.network"
	}
]
```

* **Java SDK合约使用**

```java
BaseResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
List<Node> nodeList = baseResponse.data;
```

##### **GetValidatorList**
> 查询当前共识周期的验证人列表

* **入参**

  无

* **返回值**

```
BaseResponse<List<Node>> baseResponse
```

**BaseResponse<List<Node>>描述**


```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":List<Node>                      //返回数据nodeList数组
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **List<Node>描述**

内部节点node数组列表，内部结构为一个数组，表示：**[{当前共识周期的验证人列表}]**

```java
[{
		"BenefitAddress": "0x1000000000000000000000000000000000000003",
		"Details": "The PlatON Node",
		"ExternalId": "",
		"NodeId": "53242dec8799f3f4f8882b109e1a3ebb4aa8c2082d000937d5876365414150c5337aa3d3d41ead1ac873f4e0b19cb9238d2995598207e8d571f0bd5dd843cdf3",
		"NodeName": "platon.node.3",
		"ProgramVersion": 1792,
		"Shares": 1500000000000000000000000,
		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
		"StakingBlockNum": 0,
		"StakingTxIndex": 2,
		"ValidatorTerm": 3,
		"Website": "www.platon.network"
	}, {
		"BenefitAddress": "0x1000000000000000000000000000000000000003",
		"Details": "The PlatON Node",
		"ExternalId": "",
		"NodeId": "459d199acb83bfe08c26d5c484cbe36755b53b7ae2ea5f7a5f0a8f4c08e843b51c4661f3faa57b03b710b48a9e17118c2659c5307af0cc5329726c13119a6b85",
		"NodeName": "platon.node.2",
		"ProgramVersion": 1792,
		"Shares": 1500000000000000000000000,
		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
		"StakingBlockNum": 0,
		"StakingTxIndex": 1,
		"ValidatorTerm": 2,
		"Website": "www.platon.network"
	}
]
```

* **Java SDK合约使用**

```java
BaseResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
List<Node> nodeList = baseResponse.data;
```

##### **GetCandidateList**

> 查询所有实时的候选人列表

* **入参**

  无

* **返回值**

```
BaseResponse<List<Node>> baseResponse
```

**BaseResponse<List<Node>>描述**


```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":List<Node>                      //返回数据nodeList数组
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **List<Node>描述**

内部节点node数组列表，内部结构为一个数组，表示：**[{实时的候选人列表}]**

```json
 [{
 		"BenefitAddress": "0x1000000000000000000000000000000000000003",
 		"Details": "The PlatON Node",
 		"ExternalId": "",
 		"NodeId": "4fcc251cf6bf3ea53a748971a223f5676225ee4380b65c7889a2b491e1551d45fe9fcc19c6af54dcf0d5323b5aa8ee1d919791695082bae1f86dd282dba4150f",
 		"NodeName": "platon.node.1",
 		"ProgramVersion": 1792,
 		"Released": 1500000000000000000000000,
 		"ReleasedHes": 0,
 		"RestrictingPlan": 0,
 		"RestrictingPlanHes": 0,
 		"Shares": 1500000000000000000000000,
 		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
 		"StakingBlockNum": 0,
 		"StakingEpoch": 0,
 		"StakingTxIndex": 0,
 		"Status": 0,
 		"Website": "www.platon.network"
 	}, {
 		"BenefitAddress": "0x1000000000000000000000000000000000000003",
 		"Details": "The PlatON Node",
 		"ExternalId": "",
 		"NodeId": "459d199acb83bfe08c26d5c484cbe36755b53b7ae2ea5f7a5f0a8f4c08e843b51c4661f3faa57b03b710b48a9e17118c2659c5307af0cc5329726c13119a6b85",
 		"NodeName": "platon.node.2",
 		"ProgramVersion": 1792,
 		"Released": 1500000000000000000000000,
 		"ReleasedHes": 0,
 		"RestrictingPlan": 0,
 		"RestrictingPlanHes": 0,
 		"Shares": 1500000000000000000000000,
 		"StakingAddress": "0xc1f330b214668beac2e6418dd651b09c759a4bf5",
 		"StakingBlockNum": 0,
 		"StakingEpoch": 0,
 		"StakingTxIndex": 1,
 		"Status": 0,
 		"Website": "www.platon.network"
 	}
 ]
```

* **Java SDK合约使用**

```java
BaseResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
List<Node> nodeList = baseResponse.data;
```

###  治理相关合约

> PlatON治理相关的合约接口

#### 加载治理合约
```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
ProposalContract contract = ProposalContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **SubmitProposalReturnTransaction**

> 提交提案

* **入参**
  - Proposal：提交提案对象

* **提案类型描述**
  - TextProposal：0x01，文本提案
  - VersionProposal：0x02，升级提案
  - CancelProposal：0x04，取消提案

* **提案状态定义**
  - 对文本提案来说，有：0x01,0x02,0x03三种状态；
  - 对升级提案来说，有：0x01,0x03,0x04,0x05,0x06四种状态。
  - 对取消提案来说，有：0x01,0x02,0x03三种状态；

* **状态说明**
  - Voting：0x01，投票中
  - Pass：0x02，投票通过
  - Failed：0x03，投票失败
  - PreActive：0x04，（升级提案）预生效
  - Active：0x05，（升级提案）生效
  - Canceled：0x06，（升级提案）取消

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
//创建提案
Proposal proposal = Proposal.createSubmitTextProposalParam(proposalNodeId,String.valueOf(pid));
//发送文本提案交易
PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//查询文本提案交易结果
BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_TEXT_FUNC_TYPE).send();
```

##### **VoteReturnTransaction**
> 给提案投票

* **入参**
  - ProgramVersion:ProgramVersion 程序的真实版本，治理rpc接口admin_getProgramVersion获取
  - VoteOption：voteOption   投票类型，YEAS 赞成票，NAYS 反对票，ABSTENTIONS 弃权票
  - String：proposalID   提案ID
  - String：verifier   声明的节点，只能是验证人/候选人

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**：

```java
//节点ID
String proposalID = "";
//声明的节点
String verifier ="";
PlatonSendTransaction platonSendTransaction = voteInfo.getVoteContract().voteReturnTransaction(proposalID, verifier, VoteOption.YEAS).send();
BaseResponse baseResponse = voteInfo.getVoteContract().getVoteResult(platonSendTransaction).send();
```

##### **GetProposal**
>  查询提案

* **入参**
  - String：proposalID   提案id

* **返回值**
```
BaseResponse<Proposal>
```

* **Proposal描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":Proposal                        //返回Proposal对象
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **Proposal描述**

提案对象，表示：**{提案结构}**

```json
{
	"proposalId":"111",		//提案id
	"proposer":"0x.....",	//提案节点ID
	"proposalType":"0x01",	//提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案
	"piPid":"asx123",		//提案PIPID
	"submitBlock":111,		//提交提案的块高
	"endVotingBlock":111,	//提案投票结束的块高
	"newVersion":1,			//升级版本
	"toBeCanceled":"",		//提案要取消的升级提案ID
	"activeBlock":"",		//（如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
}
```

* **合约使用**

```java
//提案id
String proposalID = "";
BaseResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
Proposal proposal = baseResponse.data;
```

##### **GetTallyResult**
> 查询提案结果

* **入参**
  - String：proposalID   提案ID

* **返回值**
```
BaseResponse<Proposal>
```

* **Proposal描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":Proposal                        //返回Proposal对象
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **Proposal描述**

提案对象，表示：**{查询返回提案结构}**

```json
{
	"proposalID":"sd",		//提案ID
	"yeas":10,				//赞成票
	"nays":11,				//反对票
	"abstentions":123,		//弃权票
	"accuVerifiers":1111,	//在整个投票期内有投票资格的验证人总数
	"status":1,				//提案状态
}
```

* **合约使用**

```java
//提案id
String proposalID ="";
BaseResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
Proposal proposal = baseResponse.data;
```

##### **GetProposalList**
> 查询提案列表

* **入参**

  无


* **返回值**
```
BaseResponse<List<Proposal>>
```

* **BaseResponse<List<Proposal>>描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":List<Proposal>                  //返回ProposalList数组
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **List<Proposal>描述**

提案对象数组，表示：**[{提案列表}]**

```json
[
	{
	"proposalID":"sd",		//提案ID
	"yeas":10,				//赞成票
	"nays":11,				//反对票
	"abstentions":123,		//弃权票
	"accuVerifiers":1111,	//在整个投票期内有投票资格的验证人总数
	"status":1,				//提案状态
	}
]
```

* **合约使用**

```java
BaseResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
List<Proposal> proposalList = baseResponse.data;
```
##### **DeclareVersionReturnTransaction**

> 版本声明

* **入参**
  - ProgramVersion:ProgramVersion 程序的真实版本，治理rpc接口admin_getProgramVersion获取
  - String：verifier   声明的节点，只能是验证人/候选人

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
String proposalNodeId = "";
PlatonSendTransaction platonSendTransaction = proposalContract.declareVersionReturnTransaction(proposalNodeId).send();
BaseResponse baseResponse = proposalContract.getDeclareVersionResult(platonSendTransaction).send();
```

##### **GetActiveVersion**

> 查询节点的链生效版本

* **入参**

  无

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
BaseResponse baseResponse = proposalContract.getActiveVersion().send();
baseResponse.data;
```

###  双签举报相关接口

> PlatON举报惩罚相关的合约接口

#### 加载举报合约

```
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "103"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
SlashContract contract = SlashContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **ReportDoubleSignReturnTransaction**

> 提交提案

* **入参**
  - Enum：DuplicateSignType   代表双签类型：prepareBlock，EprepareVote，viewChange
  - String：data   单个证据的json值，格式参照[RPC接口Evidences](#evidences_interface)

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
String data = "";	//举报证据
PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, data).send();
BaseResponse baseResponse = slashContract.getReportDoubleSignResult(platonSendTransaction).send();
```


##### **GetReportDoubleSignResult**

> 查询节点是否已被举报过多签

* **入参**
  - Enum：DuplicateSignType   代表双签类型：prepareBlock，EprepareVote，viewChange
  - String：address   举报的节点地址
  - BigInteger：blockNumber   多签的块高 

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

合约使用：

```java
BaseResponse baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0x4F8eb0B21eb8F16C80A9B7D728EA473b8676Cbb3", BigInteger.valueOf(500L)).send();
String hash = baseResponse.data;	//举报的交易Hash,可能为零交易Hash，即: 0x000...000
```

###  锁仓相关接口

> PlatON举报惩罚相关的合约接口

#### 加载锁仓合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100"
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RestrictingPlanContract contract = RestrictingPlanContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **GetCreateRestrictingPlanResult**

> 创建锁仓计划

* **入参**
  - String：address   锁仓释放到账账户
  - List<RestrictingPlan>：plan   锁仓计划列表（数组）
    - epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。如果 account 是激励池地址， 那么 period 值是 120（即，30*4） 的倍数。另外，period,每周期的区块数至少要大于最高不可逆区块高度。
    - amount：表示目标区块上待释放的金额。

* **返回值**
```
BaseRespons
```

* **BaseResponse描述**
```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":string                          //返回数据
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **合约使用**

```java
List<RestrictingPlan> restrictingPlans = new ArrayList<>();
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("100000000000000000000")));
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000000")));
 PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(restrictingRecvCredentials.getAddress(), restrictingPlans).send();
BaseResponse baseResponse = restrictingPlanContract.getCreateRestrictingPlanResult(platonSendTransaction).send();
```

##### **GetRestrictingInfo**

> 获取锁仓计划

* **入参**
  - String：address   锁仓释放到账账户

* **返回值**

```
BaseResponse<RestrictingItem> baseResponse
```

**BaseResponse<RestrictingItem>描述**


```java
{
	"Code":int,                            //是否成功 1:成功  0:失败
	"Data":RestrictingItem                 //返回数据RestrictingItem对象
	"ErrMsg":string                        //错误信息，失败时存在
}
```

* **RestrictingItem描述**

锁仓信息，表示：**[{锁仓信息}]**

```json
{
	"balance":111,	//锁仓余额
	"pledge":222,	//质押/抵押金额
	"debt":333,		//欠释放金额
	info:[
		{
			"blockNumber":444,	//释放区块高度
			"amount":555		//释放金额
		},
		{
			"blockNumber":666,	//释放区块高度
			"amount":666		//释放金额
		}
	]
}
```

* **其中info:为内部结构数组**

表示**[{锁仓分录信息}]**

```json
info:[
		{
			"blockNumber":444,	//释放区块高度
			"amount":555		//释放金额
		},
		{
			"blockNumber":666,	//释放区块高度
			"amount":666		//释放金额
		}
	]
```

* **合约使用**

```java
BaseResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(restrictingRecvCredentials.getAddress()).send();
RestrictingItem restrictingItem = baseResponse.data;
```

## 基础API

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

### PlatonSyncing

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
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetBalance> request = currentValidWeb3j.platonGetBalance(address,DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getBlockNumber();
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

```json
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

```json 
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

```json
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
