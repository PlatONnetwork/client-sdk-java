---
id: Java_SDK
title: Java SDK
sidebar_label: Java SDK
---


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
    <groupId>com.alaya.client</groupId>
    <artifactId>alaya-core</artifactId>
    <version>0.13.2.1</version>
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
compile "com.alaya.client:alaya-core:0.13.2.1"
```

## 基础api使用

### Bech32地址

* **0x地址转bech32地址**
```java
String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
String bech32Address = Bech32.addressEncode(NetworkParameters.TestNetParams.getHrp(), hex);
assertThat(bech32Address, is("atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89"));

bech32Address = Bech32.addressEncode(NetworkParameters.MainNetParams.getHrp(), hex);
assertThat(bech32Address, is("atp1f7wp58h65lvphgw2hurl9sa943w0f7qc879x50"));
```

* **bech32地址转0x地址**
```java
String bech32Address = "atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89";
String hex =  Bech32.addressDecodeHex(bech32Address);
assertThat(hex, is("0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818"));
```

### 网络参数

* **设置当前网络参数**

> 因为加入了bech32格式地址支持，为了兼容旧的api，加入了全局设置网络参数的功能，旧的api根据当前网络参数输出相应格式地址

```java
NetworkParameters.setCurrentNetwork(101L);  // default mainnet 201018L
```

### 钱包相关

* **生成一个PlatON标准的钱包 n=16384 p=1 r=8**
```java
String fileName = WalletUtils.generatePlatONWalletFile(PASSWORD, tempDir);
```

* **生成一个标准的钱包 n=262144 p=1 r=8**
```java
String fileName = WalletUtils.generateNewWalletFile(PASSWORD, tempDir);
```

* **生成一个轻量的钱包 n=4096 p=6 r=8**
```java
String fileName = WalletUtils.generateLightNewWalletFile(PASSWORD, tempDir);
```

* **加载钱包**
```java
Credentials credentials = WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
```

### 凭证相关
* **从钱包文件创建凭证**
```java
Credentials credentials = WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
```

* **从私钥创建凭证**
```java
Credentials credentials = Credentials.create("0xXXXXXXXXXXXXXX...");
```

* **获取不同网络的地址**
```java
long chainId = 100L;
String bech32Address = credentials.getAddress(chainId);
```

* **获取当前网络参数的地址**
```java
String bech32Address = credentials.getAddress();  // NetworkParameters.CurrentNetwork
```

## 基础RPC接口

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
PlatonFilter filter = new PlatonFilter();
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
PlatonFilter filter = new PlatonFilter();
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
  "prepareA": {
    "epoch": 0,            //共识轮epoch值
    "viewNumber": 0,       //共识轮view值
    "blockHash": "0x06abdbaf7a0a5cb1deddf69de5b23d6bc3506fdadbdcfc32333a1220da1361ba",    //区块hash
    "blockNumber": 16013,       //区块number
    "blockIndex": 0,        //区块在一轮view中的索引值
    "blockData": "0xe1a507a57c1e9d8cade361fefa725d7a271869aea7fd923165c872e7c0c2b3f2",     //区块rlp编码值
    "validateNode": {            
      "index": 0,     //验证人在一轮epoch中的索引值
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",    //验证人地址
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",    //验证人nodeID
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"    //验证人bls公钥
    },
    "signature": "0x1afdf43596e07d0f5b59ae8f45d30d21a9c5ac793071bfb6382ae151081a901fd3215e0b9645040c9071d0be08eb200900000000000000000000000000000000"     //消息签名
  },
  "prepareB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x74e3744545e95f4defc82d731504a39994b8013575491f83f7520cf796347b8f",
    "blockNumber": 16013,
    "blockIndex": 0,
    "blockData": "0xb11be0a3634e29281403d690c1a0bc38e96ea34b3aea0b0da2883800f610c3b7",
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x16795379ca8e28953e74b23d1c384dda760579ad70c5e490225403664a8d4490cabb1dc64a2e0967b5f0c1e9dbd6578c00000000000000000000000000000000"
  }
}
```

* **duplicateVote**

```text 
{
  "voteA": {
    "epoch": 0,   //共识轮epoch值
    "viewNumber": 0,    //共识轮view值
    "blockHash": "0x58b5976a471f86c4bd198984827bd594dce6ac861ef15bbbb1555e7b2edc2fc9",   //区块hash
    "blockNumber": 16013,   //区块number
    "blockIndex": 0,    //区块在一轮view中的索引值
    "validateNode": { 
      "index": 0,    //验证人在一轮epoch中的索引值
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",  //验证人地址
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",   //验证人nodeID
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"    //验证人bls公钥
    },
    "signature": "0x071350aed09f226e218715357ffb7523ba41271dd1d82d4dded451ee6509cd71f6888263b0b14bdfb33f88c04f76790d00000000000000000000000000000000"    //消息签名
  },
  "voteB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x422515ca50b9aa01c46dffee53f3bef0ef29884bfd014c3b6170c05d5cf67696",
    "blockNumber": 16013,
    "blockIndex": 0,
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x9bf6c01643058c0c828c35dc3277666edd087cb439c5f6a78ba065d619f812fb42c5ee881400a7a42dd8366bc0c5c88100000000000000000000000000000000"
  }
}
```

* **duplicateViewchange**

```text
{
  "viewA": {
    "epoch": 0,  
    "viewNumber": 0, 
    "blockHash": "0xb84a40bb954e579716e7a6b9021618f6b25cdb0e0dd3d8c2c0419fe835640f36",  //区块hash
    "blockNumber": 16013, 
    "validateNode": {
      "index": 0,  
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4", 
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x9c8ba2654c6b8334b1b94d3b421c5901242973afcb9d87c4ab6d82c2aee8e212a08f2ae000c9203f05f414ca578cda9000000000000000000000000000000000",
    "blockEpoch": 0,
    "blockView": 0
  },
  "viewB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x2a60ed6f04ccb9e468fbbfdda98b535653c42a16f1d7ccdfbd5d73ae1a2f4bf1",
    "blockNumber": 16013,
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0xed69663fb943ce0e0dd90df1b65e96514051e82df48b3867516cc7e505234b9ca707fe43651870d9141354a7a993e09000000000000000000000000000000000",
    "blockEpoch": 0,
    "blockView": 0
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

## 系统合约调用

系统接口主要包含经济模型和治理相关的合约接口：
* 质押相关接口
* 委托相关接口
* 奖励相关接口
* 节点相关接口
* 治理相关接口
* 举报相关接口
* 锁仓相关接口

如上接口的介绍和使用，请参考如下接口说明。


### 质押相关接口

> 质押相关的接口：主要包括节点质押、修改质押信息、解除质押、查询质押信息等接口

#### 加载质押合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
StakingContract stakingContract = StakingContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **staking**

> 节点质押：适用于矿工，节点质押后才有机会参与共识，获得收益。质押前需要自己的节点接入网络，质押时rpc链接地址必须是当前需要质押的节点。如果质押成功，节点会出现在候选人列表中。

* **入参**

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。
  - BigInteger：stakingAmount  质押的金额，单位VON，默认质押金额必须大于等于1000000LAT，该大小限制可以通过治理参数动态调整，可通过治理接口获得当前值（proposalContract.getGovernParamValue("staking", "stakeThreshold")）。
  - StakingAmountType：stakingAmountType  表示使用账户自由金额还是账户的锁仓金额做质押，StakingAmountType.FREE_AMOUNT_TYPE：自由金额，StakingAmountType.RESTRICTING_AMOUNT_TYPE：锁仓金额
  - String：benifitAddress   收益账户，用于接收出块奖励和质押奖励的收益账户。
  - String：nodeName   节点的名称
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥，节点图标是通过该公钥获取。
  - String：webSite   节点的第三方主页(有长度限制，表示该节点的主页)
  - String：details   节点的描述(有长度限制，表示该节点的描述)
  - ProgramVersion：processVersion  程序的真实版本，通过治理rpc获取
  - String：blsPubKey   bls的公钥
  - String：blsProof    bls的证明，通过治理rpc获取
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
BigDecimal stakingAmount = Convert.toVon("1000000", Convert.Unit.ATP);
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
String benifitAddress = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
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

> 节点撤销质押(一次性发起全部撤销，多次到账)，成功后节点会从候选人列表移除。只能由该节点的质押钱包地址发起交易。

* **入参**

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。

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

> 修改质押信息，只能由该节点的质押钱包地址发起交易。

* **入参**

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。
  - String：benifitAddress   收益账户，用于接收出块奖励和质押奖励的收益账户。
  - String：nodeName   节点的名称
  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥，节点图标是通过该公钥获取。
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
String benifitAddress = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
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

> 增持质押，增加已质押节点质押金，只能由该节点的质押钱包地址发起交易。

* **入参**

    - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。
    - StakingAmountType：stakingAmountType  表示使用账户自由金额还是账户的锁仓金额做质押，StakingAmountType.FREE_AMOUNT_TYPE：自由金额，StakingAmountType.RESTRICTING_AMOUNT_TYPE：锁仓金额
    - BigInteger：addStakingAmount   增持的金额，单位VON

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
BigDecimal addStakingAmount = Convert.toVon("4000000", Convert.Unit.ATP);

PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, stakingAmountType, addStakingAmount.toBigInteger()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getStakingInfo**

> 查询当前节点的质押信息

* **入参**

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。

* **返回值**

```java
CallResponse<Node> baseRespons
```

- CallResponse<Node>描述
	- int： code   结果标识，0为成功
	- Node：data   Node对象数据
	- String：errMsg   错误信息，失败时存在
	
* **Node**：保存当前节点质押信息的对象

  - String：benifitAddress	收益账户，用于接收出块奖励和质押奖励的收益账户。

  - String：details  节点的描述(有长度限制，表示该节点的描述)

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。

  - String：nodeName  节点的名称

  - BigInteger：programVersion  程序的真实版本，通过治理rpc获取

  - BigInteger：released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：releasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：restrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：restrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON

  - BigInteger：shares   当前候选人总共质押加被委托的VON数目

  - String：stakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - BigInteger：stakingBlockNum    发起质押时的区块高度

  - BigInteger：stakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：stakingTxIndex   发起质押时的交易索引

  - BigInteger：status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，4:节点的VON不足最低质押门槛，8:节点被举报双签，16:节点出块率低且达到移除条件, 32: 节点主动发起撤销

  - BigInteger：validatorTerm   验证人的任期

  - String：website   节点的第三方主页(有长度限制，表示该节点的主页)
  
  - BigInteger：delegateEpoch  节点最后一次被委托的结算周期
  
  - BigInteger：delegateTotal  节点被委托的生效总数量
  
  - BigInteger：delegateTotalHes  节点被委托的未生效总数量
  
  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励
  
  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例，1=0.01%   10000=100%
  
  - BigInteger：rewardPer 当前结算周期奖励分成比例，1=0.01%   10000=100%

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
	- BigInteger：data   打包区块的平均时间（单位为毫秒）
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
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
DelegateContract delegateContract = DelegateContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **delegate**

> 发起委托，委托已质押节点，委托给某个节点增加节点权重来获取收入 

* **入参**

  - String：nodeId   节点id，16进制格式，即节点公钥，可以通过管理台查询（platon attach http://127.0.0.1:6789 --exec "admin.nodeInfo.id"）。
  - StakingAmountType：stakingAmountType   表示使用账户自由金额还是账户的锁仓金额做质押，StakingAmountType.FREE_AMOUNT_TYPE：自由金额，StakingAmountType.RESTRICTING_AMOUNT_TYPE：锁仓金额
  - BigInteger：amount   委托的金额，单位VON，默认委托金额必须大于等于10LAT，该大小限制可以通过治理参数动态调整，可通过治理接口获得当前值（proposalContract.getGovernParamValue("staking", "operatingThreshold")）。

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
BigDecimal amount = Convert.toVon("500000", Convert.Unit.ATP);

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
  - String：nodeId   验证人的节点Id
  - BigInteger：stakingBlockNum   发起质押时的区块高度

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
	- Delegation：data   Delegation对象数据
	- String：errMsg   错误信息，失败时存在

* **Delegation**：保存当前委托账户委托信息的对象
  - String：delegateAddress	委托人的账户地址
  - String：nodeId   验证人的节点Id
  - BigInteger：stakingBlockNum    发起质押时的区块高度
  - BigInteger：delegateEpoch   最近一次对该候选人发起的委托时的结算周期
  - BigInteger：delegateReleased   发起委托账户的自由金额的锁定期委托的VON
  - BigInteger：delegateReleasedHes   发起委托账户的自由金额的犹豫期委托的VON
  - BigInteger：delegateLocked   发起委托账户的锁仓金额的锁定期委托的VON
  - BigInteger：delegateLockedHes   发起委托账户的锁仓金额的犹豫期质押的VON
  - BigInteger：cumulativeIncome  待领取的委托收益

* **Java SDK合约使用**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
String address = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
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
BigDecimal stakingAmount = Convert.toVon("500000", Convert.Unit.ATP);
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
long chainId = 201018;
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
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
NodeContract nodeContract = NodeContract.load(web3j, credentials, chainId);
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

  - String：nodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：stakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - String：benifitAddress	用于接受出块奖励和质押奖励的收益账户

  - BigInteger：rewardPer 当前结算周期奖励分成比例

  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例

  - BigInteger：stakingTxIndex   发起质押时的交易索引 

  - BigInteger：programVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：stakingBlockNum    发起质押时的区块高度 

  - BigInteger：shares   当前候选人总共质押加被委托的VON数目  

  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥，节点图标是通过该公钥获取。

  - String：nodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - String：website   节点的第三方主页(有长度限制，表示该节点的主页)

  - String：details   节点的描述(有长度限制，表示该节点的描述) 

  - BigInteger：validatorTerm   验证人的任期
  
  - BigInteger：delegateTotal  节点被委托的生效总数量
  
  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励

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

  - String：nodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：stakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - String：benifitAddress	用于接受出块奖励和质押奖励的收益账户

  - BigInteger：rewardPer 当前结算周期奖励分成比例

  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例

  - BigInteger：stakingTxIndex   发起质押时的交易索引 

  - BigInteger：programVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：stakingBlockNum    发起质押时的区块高度 

  - BigInteger：shares   当前候选人总共质押加被委托的VON数目  

  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥，节点图标是通过该公钥获取。

  - String：nodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - String：website   节点的第三方主页(有长度限制，表示该节点的主页)

  - String：details   节点的描述(有长度限制，表示该节点的描述) 

  - BigInteger：validatorTerm   验证人的任期
  
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

  - String：nodeId   被质押的节点Id(也叫候选人的节点Id)

  - String：stakingAddress   发起质押时使用的账户(撤销质押时，VON会被退回该账户或者该账户的锁仓信息中)

  - String：benifitAddress	用于接受出块奖励和质押奖励的收益账户

  - BigInteger：rewardPer 当前结算周期奖励分成比例

  - BigInteger：nextRewardPer 下一个结算周期奖励分成比例

  - BigInteger：stakingTxIndex   发起质押时的交易索引 

  - BigInteger：programVersion  被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)

  - BigInteger：status   候选人的状态，0: 节点可用，1: 节点不可用 ，2:节点出块率低但没有达到移除条件的，4:节点的VON不足最低质押门槛，8:节点被举报双签，16:节点出块率低且达到移除条件, 32: 节点主动发起撤销

  - BigInteger：stakingEpoch   当前变更质押金额时的结算周期

  - BigInteger：stakingBlockNum    发起质押时的区块高度 

  - BigInteger：shares   当前候选人总共质押加被委托的VON数目  

  - BigInteger：released   发起质押账户的自由金额的锁定期质押的VON

  - BigInteger：releasedHes   发起质押账户的自由金额的犹豫期质押的VON

  - BigInteger：restrictingPlan   发起质押账户的锁仓金额的锁定期质押的VON

  - BigInteger：restrictingPlanHes   发起质押账户的锁仓金额的犹豫期质押的VON  

  - String：externalId   外部Id(有长度限制，给第三方拉取节点描述的Id)，目前为keybase账户公钥，节点图标是通过该公钥获取。

  - String：nodeName   被质押节点的名称(有长度限制，表示该节点的名称)

  - String：website   节点的第三方主页(有长度限制，表示该节点的主页)

  - String：details   节点的描述(有长度限制，表示该节点的描述) 

  - BigInteger：delegateEpoch  节点最后一次被委托的结算周期
  
  - BigInteger：delegateTotal  节点被委托的生效总数量
  
  - BigInteger：delegateTotalHes  节点被委托的未生效总数量

  - BigInteger：delegateRewardTotal  候选人当前发放的总委托奖励

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
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
ProposalContract proposalContract = ProposalContract.load(web3j, credentials, chainId);
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
  - BigInteger:   activeBlock   提案生效块高，系统根据EndVotingBlock算出
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
  - 1  投票中
  - 2  投票通过
  - 3  投票失败
  - 4 （升级提案）预生效
  - 5 （升级提案）生效
  - 6  被取消

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
  - BigInteger:   activeBlock   提案生效块高，系统根据EndVotingBlock算出
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

```java
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

```java
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
long chainId = 201018;
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
CallResponse<String> baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat", BigInteger.valueOf(500L)).send();
```

###  锁仓相关接口

> PlatON举报惩罚相关的合约接口

#### 加载锁仓合约

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RestrictingPlanContract contract = RestrictingPlanContract.load(web3j, credentials, chainId);
```

#### 接口说明

##### **createRestrictingPlan**

> 创建锁仓计划

* **入参**

  - String：address   锁仓释放到账账户
  - List<RestrictingPlan>：plan   锁仓计划列表（数组）
    - epoch：锁仓的周期，表示结算周期的倍数
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

## Solidity合约调用

将Solidity智能合约部署到区块链上时，必须先将其编译成字节码的格式，然后将其作为交易的一部分发送。Java SDK 将帮你生成Solidity智能合约对应的Java包装类，可以方便的部署Solidity智能合约以及调用Solidity智能合约中的交易方法、事件和常量方法。

### 编译solidity源代码

* 通过`solc`编译器编译solidity源代码，请根据合约声明的编译器版本下载对应solc编译器版本([solc下载](https://github.com/PlatONnetwork/solidity/releases))：

```shell
$ solc <contract>.sol --bin --abi --optimize -o <output-dir>/
```

`bin`，输出包含十六进制编码的solidity二进制文件以提供交易请求。
`abi`，输出一个solidity的应用程序二进制接口（`ABI`）文件，它详细描述了所有可公开访问的合约方法及其相关参数。`abi`文件也用于生成solidity智能合约对应的Java包装类。

* 使用`platon-truffle`编译solidity源代码([platon-truffle开发工具安装参考](https://platon-truffle.readthedocs.io/en/v0.13.2/getting-started/installation.html#)|[platon-truffle开发工具使用手册](https://platon-truffle.readthedocs.io/en/v0.13.2/))：

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

> **step5.** 提取 `abi` 和 `bin`文件

```
将 ./build/contracts/HelloWorld.json中 abi属性放到HelloWorld.abi文件中
将 ./build/contracts/HelloWorld.json中 bytecode属性放到HelloWorld.bin文件中（需要去掉0x开头）

```

### Solidity智能合约Java包装类

Java SDK支持从`abi`文件中自动生成Solidity智能合约对应的Java包装类。

* 通过命令行工具生成Java包装类（[alaya-web3j下载](http://download.alaya.network/alaya/sdk/0.13.2/alaya-web3j-0.13.2.1.zip)）：

```shell
$ alaya-web3j solidity generate [--javaTypes|--solidityTypes] /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name
```

* 直接调用Java SDK中的工具类生成Java包装类：

```java
// 通过maven或gradle导入console模块
compile "com.platon.client:console:{version}"

String args[] = {"generate", "/path/to/<smart-contract>.bin", "/path/to/<smart-contract>.abi", "-o", "/path/to/src/main/java", "-p" , "com.your.organisation.name"};
SolidityFunctionWrapperGenerator.run(args);
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
        <web3j>, <transactionManager>, contractGasProvider, chainId
        [<initialValue>,] <param1>, ..., <paramN>).send();

or

YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <Credentials>, contractGasProvider, chainId
        [<initialValue>,] <param1>, ..., <paramN>).send();
```

这个方法将在区块链上部署智能合约。部署成功以后，它将会返回一个智能合约的包装类实例，包含智能合约的地址。

如果你的智能合约在构造上接受LAT转账，则需要初始化参数值<initialValue>。

通过智能合约的地址也可以创建智能合约对应的Java包装类的实例：

```java
YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, transactionManager, contractGasProvider, chainId);

or

YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, credentials, contractGasProvider, chainId);
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

除了`RawTransactionManager`之外，Java SDK还提供了一个客户端交易管理器`ClientTransactionManager`，它将你的交易签名工作交给你正在连接的PlatON客户端。
此外，还有一个`ReadonlyTransactionManager`，用于只从智能合约中查询数据，而不与它进行交易。

#### gas提供者
合约的手续费是通过 `GasProvider` 设置，因为合约gas消耗为动态的，和合约的逻辑相关。建议首次部署调用时使用比较大的值，如 999999。后面根据实际情况调整。

```java
BigInteger GAS_LIMIT = BigInteger.valueOf(999999);
BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

GasProvider gasProvider  = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
```

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

## Wasm合约调用

将Wasm智能合约部署到区块链上时，必须先将其编译成字节码的格式，然后将其作为交易的一部分发送。Java SDK 将帮你生成Wasm智能合约对应的Java包装类，可以方便的部署Wasm智能合约以及调用Wasm智能合约中的交易方法、事件和常量方法。

### 编译Wasm合约源代码

* 通过`CDT`编译器编译Wasm合约源代码([CDT下载](https://github.com/PlatONnetwork/PlatON-CDT/releases))

CDT安装成功以后，可通过如下命令编译Wasm合约源代码：

```shell
$ platon-cpp <contract>.cpp 
```

编译成功以后，会生成`<contract>.wasm`和`<contract>.abi.json`文件

`wasm`，输出Wasm合约的二进制文件以提供交易请求。
`abi.json`，详细描述了所有可公开访问的合约方法及其相关参数。`abi`文件也用于生成Wasm智能合约对应的Java包装类。

* 使用`platon-truffle`编译Wasm合约源代码([platon-truffle开发工具安装参考](https://platon-truffle.readthedocs.io/en/v0.13.2/getting-started/installation.html#)|[platon-truffle开发工具使用手册](https://platon-truffle.readthedocs.io/en/v0.13.2/))

### Wasm智能合约Java包装类

Java SDK支持从`abi.json`文件中自动生成Wasm智能合约对应的Java包装类。

* 通过命令行工具生成Java包装类：

```shell
$ alaya-web3j wasm generate /path/to/<smart-contract>.wasm /path/to/<smart-contract>.abi.json -o /path/to/src/main/java -p com.your.organisation.name
```

* 直接调用Java SDK中的工具类生成Java包装类：

```java
String args[] = {"generate", "/path/to/<smart-contract>.wasm", "/path/to/<smart-contract>.abi.json", "-o", "/path/to/src/main/java", "-p" , "com.your.organisation.name"};
WasmFunctionWrapperGenerator.run(args);
```

其中`wasm`和`abi.json`文件是编译wasm源代码以后生成的。

Wasm智能合约对应的Java包装类支持的主要功能：
- 构建与部署
- 确定合约有效性
- 调用交易和事件
- 调用常量方法

#### 构建与部署智能合约

智能合约的构建和部署使用包装类中的deploy方法：

```java
YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <transactionManager>, contractGasProvider, chainId,
        [<initialValue>,] <param1>, ..., <paramN>).send();

or

YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <Credentials>, contractGasProvider, chainId,
        [<initialValue>,] <param1>, ..., <paramN>).send();
```

这个方法将在区块链上部署智能合约。部署成功以后，它将会返回一个智能合约的包装类实例，包含智能合约的地址。

如果你的智能合约在构造上接受LAT转账，则需要初始化参数值<initialValue>。

通过智能合约的地址也可以创建智能合约对应的Java包装类的实例：

```java
YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, transactionManager, contractGasProvider,chainId);

or

YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, credentials, contractGasProvider,chainId);
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

除了`RawTransactionManager`之外，Java SDK还提供了一个客户端交易管理器`ClientTransactionManager`，它将你的交易签名工作交给你正在连接的PlatON客户端。
此外，还有一个`ReadonlyTransactionManager`，用于只从智能合约中查询数据，而不与它进行交易。

#### gas提供者
合约的手续费是通过 `GasProvider` 设置，因为合约gas消耗为动态的，和合约的逻辑相关。建议首次部署调用时使用比较大的值，如 999999。后面根据实际情况调整。

```java
BigInteger GAS_LIMIT = BigInteger.valueOf(999999);
BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

GasProvider gasProvider  = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
```

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